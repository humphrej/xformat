package xformat;

import com.google.common.flogger.FluentLogger;
import com.google.googlejavaformat.java.Formatter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class JavaFormatter {

  private static final Formatter JAVA_FORMATTER = new Formatter();
  private static FluentLogger LOGGER = FluentLogger.forEnclosingClass();

  public static void format(Path path) {
    try {
      // why temp file
      File tempFile = File.createTempFile("prefix", "suffix");
      Path tempFilePath = Paths.get(tempFile.getAbsolutePath());
      // write formatted text to temporary file first
      Files.write(
          tempFilePath, Collections.singleton(JAVA_FORMATTER.formatSource(Files.readString(path))));
      // move temporary file to original
      Files.move(tempFilePath, path, StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      LOGGER.atWarning().withCause(e).log("Error formatting %s", path, e);
    }
  }

  public static Supplier<Consumer<Path>> create() {
    return () -> JavaFormatter::format;
  }
}

