package edu.brown.cs.jkst.suggest;

import java.io.PrintWriter;
import java.util.Set;

import edu.brown.cs.jkst.main.CommandManager.Command;
import edu.brown.cs.jkst.query.FilmQuery;

/**
 * handles the suggest command.
 */
public final class SuggestCommand implements Command {
  public static final SuggestCommand INSTANCE = new SuggestCommand();

  @Override
  public void execute(String line, PrintWriter pw, Boolean repl) {
    Set<String> suggestions = FilmQuery.INSTANCE.findSuggestion(line);
    if (repl) {
      pw.println(displaySuggestions(suggestions));
    } else {
      pw.print("sent stuff to handler");
    }
  }

  /**
   * method that fetches a set of string suggestions for a search query.
   *
   * @param search
   *          String which we want to pass into the trie
   * @return Set of strings which are reasonable suggestions for the user.
   */
  public Set<String> getSuggestions(String search) {
    return FilmQuery.INSTANCE.findSuggestion(search);
  }

  /**
   * turns suggestions into String for display.
   *
   * @param suggestions
   *          Set of strings that have to be displayed as suggestions.
   * @return string for display in commandline. need to modify this for display
   *         in handler.
   */
  public String displaySuggestions(Set<String> suggestions) {
    StringBuilder sb = new StringBuilder();

    if (sb.length() > 0) {
      return sb.substring(0, sb.length() - 1);
    }

    return "";
  }
}
