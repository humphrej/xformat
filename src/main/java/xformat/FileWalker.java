package xformat;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class FileWalker {

  private final List<String> roots;
  private final Set<String> ignoredDirectories;
  private final Consumer<Path> fileVisitor;

  public static FileWalker create(
      List<String> roots, Set<String> ignoredDirectories, Consumer<Path> fileVisitor) {
    return new FileWalker(roots, ignoredDirectories, fileVisitor);
  }

  private FileWalker(
      List<String> roots, Set<String> ignoredDirectories, Consumer<Path> fileVisitor) {
    this.roots = roots;
    this.ignoredDirectories = Sets.newHashSet(ignoredDirectories);
    this.fileVisitor = fileVisitor;
  }

  private class MyFileVisitor extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      fileVisitor.accept(file);

      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
        throws IOException {
      if (ignoredDirectories.contains(finalPart(dir))) {
        return FileVisitResult.SKIP_SUBTREE;
      }
      return FileVisitResult.CONTINUE;
    }
  }

  private static String finalPart(Path p) {
    return p.getName(p.getNameCount() - 1).toString();
  }

  /** Performs the walk */
  public void walk() throws IOException {

    for (String root : roots) {
      Path path = FileSystems.getDefault().getPath(root);
      Files.walkFileTree(path, new MyFileVisitor());
    }
  }
}

