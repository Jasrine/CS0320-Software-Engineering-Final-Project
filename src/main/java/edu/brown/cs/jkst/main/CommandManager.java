
package edu.brown.cs.jkst.main;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class that runs commands.
 */
public class CommandManager {

  private String[] commandArr = {
      "suggest", "select"
  };
  private HashMap<String, Command> commands = new HashMap<String, Command>();

  /**
   * Constructor for Command Manager.
   */
  public CommandManager() {
    this.commands = new HashMap<String, Command>();
  }

  /**
   * processes commands received from repl or main.
   *
   * @param line
   *          String received from terminal.
   * @param pw
   *          Prints error messages to terminal.
   */
  public void processCommand(String line, PrintWriter pw) {
    Iterator<HashMap.Entry<String, Command>> it = commands.entrySet()
        .iterator();

    boolean hasValidCommand = false;
    while (!hasValidCommand && it.hasNext()) {
      Map.Entry<String, Command> current = it.next();
      if (line.matches(current.getKey())) {
        current.getValue().execute(line, pw);
        hasValidCommand = true;
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
   */
  public void registerCommand(String pattern, Command command) {
    /*
     * Tell the manager that when this command is seen, to call c.execute with
     * any args pattern should be regexp
     */
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
     */
    void execute(String line, PrintWriter pw);

  }

}
