package xformat;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;
import org.junit.Test;

public class FileWalkerTest {

  @Test
  public void should_walk_known_tree() throws IOException {
    Path tempDir = Files.createTempDirectory("FileWalkerTest");
    makeTestData(tempDir);

    List<Path> visited = Lists.newArrayList();
    FileWalker.create(List.of(tempDir.toString()), Set.of("ignored"), visited::add).walk();

    assertThat(visited)
        .containsExactly(tempDir.resolve("root1/a1.txt"), tempDir.resolve("root1/level2/a2.txt"));
    // anything below ignored .. is ignored
  }

  private void makeTestData(Path rootDirectory) throws IOException {
    // mkdir /root1
    Path root1 = Files.createDirectory(rootDirectory.resolve("root1"));
    // create /root1/a1.txt
    Path a1 = root1.resolve("a1.txt");
    Files.write(a1, new byte[0], StandardOpenOption.CREATE);
    // mkdir /root1/level2
    Path root1_level2 = Files.createDirectory(root1.resolve("level2"));
    // create /root1/level2/a2.txt
    Path a2 = root1_level2.resolve("a2.txt");
    Files.write(a2, new byte[0], StandardOpenOption.CREATE);
    // mkdir /root1/ignored
    Path root1_ignored = Files.createDirectory(root1.resolve("ignored"));
    // create /root1/ignored/b.txt
    Path b = root1_ignored.resolve("b.txt");
    Files.write(b, new byte[0], StandardOpenOption.CREATE);
  }
}

