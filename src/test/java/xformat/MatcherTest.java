package xformat;

import static com.google.common.truth.Truth.assertThat;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MatcherTest {

  private final Path path;
  private final Optional<Format> expectedFormat;

  @Parameters(name = "{index}: match({0} {1})")
  public static Collection<Object[]> data() {
    return Arrays.asList(
        new Object[][] {
          {Path.of("foo.java"), Optional.of(Format.JAVA)},
          {Path.of("prefixed/foo.java"), Optional.of(Format.JAVA)},
          {Path.of("foo.dhall"), Optional.of(Format.DHALL)},
          {Path.of("prefixed/foo.dhall"), Optional.of(Format.DHALL)},
          {Path.of("BUILD"), Optional.of(Format.BAZEL)},
          {Path.of("prefixed/BUILD"), Optional.of(Format.BAZEL)},
          {Path.of("BUILD.bazel"), Optional.of(Format.BAZEL)},
          {Path.of("prefixed/BUILD.bazel"), Optional.of(Format.BAZEL)},
          {Path.of("macro.bzl"), Optional.of(Format.BAZEL)},
          {Path.of("prefixed/macro.bzl"), Optional.of(Format.BAZEL)},
          {Path.of("foo.cpp"), Optional.of(Format.CPP)},
          {Path.of("prefixed/foo.cpp"), Optional.of(Format.CPP)},
          {Path.of("foo.cc"), Optional.of(Format.CPP)},
          {Path.of("prefixed/foo.cc"), Optional.of(Format.CPP)},
          {Path.of("foo.ts"), Optional.of(Format.TYPESCRIPT)},
          {Path.of("prefixed/foo.ts"), Optional.of(Format.TYPESCRIPT)},
          {Path.of("foo.py"), Optional.of(Format.PYTHON)},
          {Path.of("prefixed/foo.py"), Optional.of(Format.PYTHON)},
          {Path.of("foo.go"), Optional.of(Format.GO)},
          {Path.of("prefixed/foo.go"), Optional.of(Format.GO)},
          {Path.of("foo.txt"), Optional.empty()},
        });
  }

  public MatcherTest(Path path, Optional<Format> expectedFormat) {
    this.path = path;
    this.expectedFormat = expectedFormat;
  }

  @Test
  public void should_match() {
    assertThat(XFormatter.match(path)).isEqualTo(expectedFormat);
  }
}

