/**
 * 
 */
package edu.brown.cs.jkst.main;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author katelummis
 *
 */
public class CommandManager {

  HashMap<String, Command> commands;

  public CommandManager() {
    this.commands = new HashMap<String, Command>();
  }

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
    if (!hasValidCommand)
      pw.println("ERROR: Invalid Command");
  }

  public void registerCommand(String pattern, Command command) {
    /*
     * Tell the manager that when this command is seen, to call c.execute with
     * any args pattern should be regexp
     */
    commands.put(pattern, command);
  }

  public interface Command {

    public void execute(String line, PrintWriter pw);

  }

}
