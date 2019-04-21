package edu.brown.cs.jkst.query;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import edu.brown.cs.jkst.graphdata.Movie;
import edu.brown.cs.jkst.main.CommandManager.Command;

/**
 * lets a user enter a search query.
 */
public final class SearchCommand implements Command {
  public static final SearchCommand INSTANCE = new SearchCommand();

  @Override
  public void execute(String line, PrintWriter pw, Boolean repl) {
    // returns films similar to the selected film
    // also has to route to advanced search, and then ranker
    pw.println("No results found.");
  }

  /**
   * method called by handler when a user enters a query for film suggestions.
   * cleans information and then passes it along to advanced search, followed by
   * ranker.
   *
   * @param title
   *          of film.
   * @param decades
   *          to which the desired film may belong.
   * @param region
   *          of film.
   * @param genres
   *          which we wish to include in our search.
   *
   * @return List of similar movies.
   */
  public List<Movie> search(String title, String decades, String region,
      String genres) {
    return new LinkedList<Movie>();
  }
}
