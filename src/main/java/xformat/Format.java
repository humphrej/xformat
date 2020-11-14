package xformat;

import static xformat.Matchers.matchesBase;
import static xformat.Matchers.matchesExt;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum Format {
  JAVA(List.of(matchesExt(".java")), JavaFormatter.create()),
  BAZEL(
      List.of(
          matchesExt(".bazel"), matchesExt(".bzl"), matchesBase("WORKSPACE"), matchesBase("BUILD")),
      ExternalFormatter.create("buildifier", "-mode=fix", "%s")),
  DHALL(
      List.of(matchesExt(".dhall")),
      ExternalFormatter.create("dhall", "format", "--inplace", "%s")),
  GO(List.of(matchesExt(".go")), ExternalFormatter.create("go", "fmt", "%s")),
  SH(List.of(matchesExt(".sh")), ExternalFormatter.create("shfmt", "-i", "2", "-ci", "-w", "%s")),
  CPP(
      List.of(matchesExt(".cc"), matchesExt(".cpp")),
      ExternalFormatter.create("clang-format", "-i", "%s")),
  TYPESCRIPT(
      List.of(matchesExt(".ts")), ExternalFormatter.create("tsfmt", "--no-tslint", "-r", "%s")),
  PYTHON(List.of(matchesExt(".py")), ExternalFormatter.create("yapf", "-i", "%s")),
  PROTO(List.of(matchesExt(".proto")), ExternalFormatter.create("clang-format", "-i", "%s"));

  private final List<Predicate<Path>> matchPredicates;
  private final Supplier<Consumer<Path>> formatFnSupplier;

  Format(List<Predicate<Path>> matchPredicates, Supplier<Consumer<Path>> formatFnSupplier) {
    this.matchPredicates = matchPredicates;
    this.formatFnSupplier = formatFnSupplier;
  }

  public List<Predicate<Path>> matchPredicates() {
    return matchPredicates;
  }

  public Supplier<Consumer<Path>> formatFnSupplier() {
    return formatFnSupplier;
  }
}

