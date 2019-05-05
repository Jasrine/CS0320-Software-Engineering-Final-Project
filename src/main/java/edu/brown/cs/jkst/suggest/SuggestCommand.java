package edu.brown.cs.jkst.suggest;

import java.io.PrintWriter;

import edu.brown.cs.jkst.main.CommandManager.Command;
import edu.brown.cs.jkst.query.FilmQuery;

/**
 * handles the suggest command.
 */
public final class SuggestCommand implements Command {
  public static final SuggestCommand INSTANCE = new SuggestCommand();

  @Override
  public String execute(String line, PrintWriter pw, Boolean repl) {
    String suggestions = FilmQuery.INSTANCE.findSuggestion(line);
    if (repl) {
      pw.println(suggestions);
    } else {
      pw.println("sent suggest stuff to handler");
    }
    return suggestions;
  }

  /**
   * method that fetches a set of string suggestions for a search query.
   *
   * @param search
   *          String which we want to pass into the trie
   * @return Set of strings which are reasonable suggestions for the user.
   */
  public String getTextSuggestions(String search) {
    return FilmQuery.INSTANCE.findSuggestion(search);
  }
}
