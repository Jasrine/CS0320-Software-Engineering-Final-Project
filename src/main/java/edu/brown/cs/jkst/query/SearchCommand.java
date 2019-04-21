package edu.brown.cs.jkst.query;

import java.io.PrintWriter;

import edu.brown.cs.jkst.main.CommandManager.Command;

/**
 * lets a user enter a search query.
 */
public final class SearchCommand implements Command {
  public static final SearchCommand INSTANCE = new SearchCommand();

  @Override
  public void execute(String line, PrintWriter pw, Boolean repl) {
    // returns films similar to the selected film
    pw.println("No results found.");
  }
}
