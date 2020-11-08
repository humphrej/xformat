package xformat;

import static org.kohsuke.args4j.OptionHandlerFilter.ALL;

import java.io.File;
import java.io.PrintStream;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.DelimitedOptionHandler;
import org.kohsuke.args4j.spi.FileOptionHandler;
import org.kohsuke.args4j.spi.Setter;
import org.kohsuke.args4j.spi.StringOptionHandler;

/** A Helper for args4j */
public class ArgumentParser {

  private final Object application;
  private final String[] args;
  private final PrintStream errorPrintWriter;
  private final boolean terminateOnError;

  private ArgumentParser(
      String[] args, Object application, PrintStream errorPrintWriter, boolean terminateOnError) {
    this.args = args;
    this.application = application;
    this.errorPrintWriter = errorPrintWriter;
    this.terminateOnError = terminateOnError;
  }

  /**
   * A default builder that writes errors to stderr and terminates if the incorrect arguments are
   * supplied
   *
   * @param args the command line arguments to parse
   * @param application a class with fields annotated with args4j Option
   * @return the builder
   */
  public static Builder builder(String[] args, Object application) {
    return new Builder(args, application).withErrorWriter(System.err).withTerminationOnError(true);
  }

  public static class Builder {

    private final Object application;
    private final String[] args;
    private PrintStream errorPrintWriter;
    private boolean terminateOnError;

    private Builder(String[] args, Object application) {
      this.application = application;
      this.args = args;
    }

    public Builder withErrorWriter(PrintStream w) {
      this.errorPrintWriter = w;
      return this;
    }

    public Builder withTerminationOnError(boolean terminationOnError) {
      this.terminateOnError = terminationOnError;
      return this;
    }

    public ArgumentParser build() {
      return new ArgumentParser(args, application, errorPrintWriter, terminateOnError);
    }
  }

  public void parse() {
    CmdLineParser parser = new CmdLineParser(application);

    try {
      parser.parseArgument(args);
    } catch (CmdLineException e) {
      PrintStream ps = errorPrintWriter;
      ps.println(e.getMessage());
      printUsage();
      if (terminateOnError) {
        System.exit(1);
      }
    }
  }

  public void printUsage() {
    CmdLineParser parser = new CmdLineParser(application);

    PrintStream ps = errorPrintWriter;
    ps.println("<command> [options...] arguments...");
    parser.printUsage(ps);
    ps.println();

    ps.println("  Example: <command>" + parser.printExample(ALL));
  }
}

