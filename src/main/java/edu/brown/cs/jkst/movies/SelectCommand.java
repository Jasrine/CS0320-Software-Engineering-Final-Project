package edu.brown.cs.jkst.movies;

import java.io.PrintWriter;

import edu.brown.cs.jkst.main.CommandManager.Command;

/**
 * class that selects one of a list of search results.
 */
public final class SelectCommand implements Command {
  public static final SelectCommand INSTANCE = new SelectCommand();

  @Override
  public String execute(String line, PrintWriter pw, Boolean repl) {
    // should pull up the appropriate profile, hopefully given an id.
    if (repl) {
      pw.print("select received this! " + line);
    }
    return "";
  }

}
