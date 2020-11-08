package xformat;

import java.nio.file.Path;
import java.util.function.Predicate;

public class Matchers {

  public static Predicate<Path> matchesExt(String expectedExt) {
    return p -> p.getFileName().toString().endsWith(expectedExt);
  }

  public static Predicate<Path> matchesBase(String expectedBase) {
    return p -> p.getFileName().toString().equals(expectedBase);
  }

  private Matchers() {}
}

