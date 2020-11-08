package xformat;

import com.google.common.flogger.FluentLogger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExternalFormatter {

  private static final FluentLogger LOGGER = FluentLogger.forEnclosingClass();

  public static void gobble(InputStream inputStream, Consumer<String> lineConsumer) {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
      in.lines().forEach(lineConsumer);
    } catch (IOException e) {
      LOGGER.atWarning().withCause(e).log("Exception gobbling input stream");
    }
  }

  private static void executeWithProcess(String... cmd) throws IOException {
    try {
      ProcessBuilder builder = new ProcessBuilder(cmd);
      builder.redirectErrorStream(true);
      Process process = builder.start();

      StringBuilder output = new StringBuilder();

      gobble(
          process.getInputStream(),
          x -> {
            output.append(x).append("\n");
          });

      int exitCode = process.waitFor();
      LOGGER.atFiner().log("Process exited with exit code %d and output:\n%s%n", exitCode, output);

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private static String sprintf(String template, Object... objects) {
    StringBuilder buf = new StringBuilder();
    try (var formatter = new Formatter(buf)) {
      formatter.format(template, objects);
    }
    return buf.toString();
  }

  public static Supplier<Consumer<Path>> create(String... commandTemplate) {
    return () ->
        p -> {
          String[] command = format(commandTemplate, p.toString());

          try {
            executeWithProcess(command);
          } catch (IOException e) {
            LOGGER.atWarning().withCause(e).log("Exception thrown running command %s", command);
          }
        };
  }

  /** Formats a string array using sprintf */
  public static String[] format(String[] template, Object... objects) {
    String[] result = new String[template.length];
    for (int i = 0; i < template.length; i++) {
      result[i] = sprintf(template[i], objects);
    }
    return result;
  }
}

