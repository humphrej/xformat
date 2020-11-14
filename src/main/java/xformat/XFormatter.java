package xformat;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.flogger.FluentLogger;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

public class XFormatter implements Runnable {

  @Option(
      name = "--ignore_directories",
      metaVar = "d1,d2",
      usage = "Ignored directories, split by commas")
  private static String ignoreDirectories = "";

  @Option(name = "-v", metaVar = "0,1", usage = "Log Level")
  private static int logLevel = 0;

  @Option(name = "--format", usage = "Formatters to include")
  private static Format[] formats = Format.values();

  @Argument private List<String> roots = Lists.newArrayList();

  private static final long TIMEOUT_SECONDS = 30;
  private static final FluentLogger LOGGER = FluentLogger.forEnclosingClass();

  private final ExecutorService executor;

  public XFormatter() {
    int nThreads = Runtime.getRuntime().availableProcessors();

    executor =
        Executors.newFixedThreadPool(
            nThreads, new ThreadFactoryBuilder().setNameFormat("xformat-%d").build());
  }

  @VisibleForTesting
  static Optional<Format> match(Path path) {
    for (Format format : formats) {
      for (var predicate : format.matchPredicates()) {
        if (predicate.test(path)) {
          return Optional.of(format);
        }
      }
    }
    return Optional.empty();
  }

  private static Runnable safeRunnable(Runnable r) {
    return () -> {
      try {
        r.run();
      } catch (Throwable t) {
        LOGGER.atSevere().withCause(t).log("Exception thrown in executor");
      }
    };
  }

  private static void formatPath(Format f, Path p) {
    LOGGER.atFine().log("Formatting as %s %s", f, p);
    f.formatFnSupplier().get().accept(p);
  }

  private void scheduleMatchAndFormat(Path p) {
    executor.submit(safeRunnable(() -> match(p).ifPresent(f -> formatPath(f, p))));
  }

  @Override
  public void run() {
    try {
      work();
    } catch (IOException e) {
      LOGGER.atSevere().withCause(e).log("Problem running formatter");
    } finally {
      shutDownAndWait();
    }
  }

  private void work() throws IOException {
    LOGGER.atFine().log("formats: %s", Arrays.toString(formats));

    Set<String> ignoredDirectories =
        Stream.of(ignoreDirectories.split(",")).collect(Collectors.toSet());

    if (logLevel == 0) {
      setLevel(Level.SEVERE);
    } else if (logLevel == 1) {
      setLevel(Level.FINE);
    } else {
      setLevel(Level.FINEST);
    }

    FileWalker.create(roots, ignoredDirectories, this::scheduleMatchAndFormat).walk();
  }

  private void shutDownAndWait() {
    executor.shutdown();
    while (!executor.isTerminated()) {
      try {
        executor.awaitTermination(TIMEOUT_SECONDS, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private static void setLevel(Level targetLevel) {
    Logger root = Logger.getLogger("");
    root.setLevel(targetLevel);
    for (Handler handler : root.getHandlers()) {
      handler.setLevel(targetLevel);
    }
  }

  static {
    System.setProperty(
        "java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT %1$tL] [%4$-7s] %5$s %6$s %n");
  }

  public static void main(String[] args) throws Exception {
    XFormatter application = new XFormatter();
    ArgumentParser.builder(args, application).withTerminationOnError(true).build().parse();

    application.run();
  }
}

