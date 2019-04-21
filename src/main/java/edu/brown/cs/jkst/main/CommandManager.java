
package edu.brown.cs.jkst.main;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.brown.cs.jkst.movies.SelectCommand;
import edu.brown.cs.jkst.query.FilmQuery;
import edu.brown.cs.jkst.query.SearchCommand;
import edu.brown.cs.jkst.suggest.SuggestCommand;

/**
 * Class that runs commands.
 */
public class CommandManager {

  private Map<String, Command> commands;

  /**
   * Constructor for Command Manager.
   */
  public CommandManager() {
    this.commands = new HashMap<String, Command>();
    FilmQuery.INSTANCE.createConnection();
    this.commands.put("suggest", SuggestCommand.INSTANCE);
    this.commands.put("select", SelectCommand.INSTANCE);
    this.commands.put("search", SearchCommand.INSTANCE);

    // need to initialize this list
  }

  /**
   * processes commands received from repl or main.
   *
   * @param line
   *          String received from terminal.
   * @param pw
   *          Prints error messages to terminal.
   * @param repl
   *          Boolean which indicates whether the command must print outputs in
   *          the commandline, or prepare them for a handler.
   */
  public void processCommand(String line, PrintWriter pw, Boolean repl) {
    Iterator<HashMap.Entry<String, Command>> it = commands.entrySet()
        .iterator();
    boolean hasValidCommand = false;

    while (!hasValidCommand && it.hasNext()) {
      Map.Entry<String, Command> current = it.next();
      if (line.startsWith(current.getKey())) {
        hasValidCommand = true;
        String modifiedLine = line.replaceFirst(current.getKey(), "").trim();
        if (modifiedLine.trim().length() == 0) {
          pw.println("ERROR: no arguments passed into " + current.getKey());
          break;
        }

        current.getValue().execute(modifiedLine, pw, repl);
      }
    }

    if (!hasValidCommand) {
      pw.println("ERROR: Invalid Command");
    }
  }

  /**
   * Tell the manager that when this command is seen, to call c.execute with any
   * args pattern should be regexp.
   *
   * @param pattern
   *          String pattern for the command.
   * @param command
   *          Command associated with the String.
   * @param repl
   *          boolean indicating whether this command must display results for
   *          repl or for the handler.
   */
  public void registerCommand(String pattern, Command command, Boolean repl) {
    commands.put(pattern, command);
  }

  /**
   * interface for commands.
   */
  public interface Command {
    /**
     * executes commands.
     *
     * @param line
     *          String with call to appropriate command.
     * @param pw
     *          PrintWriter for printing to commandline.
     * @param repl
     *          Boolean which indicates whether execution is printing in the
     *          commandline, or sending output to a handler.
     */
    void execute(String line, PrintWriter pw, Boolean repl);
  }
}
