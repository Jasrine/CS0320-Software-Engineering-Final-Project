package edu.brown.cs.jkst.movies;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import edu.brown.cs.jkst.graphdata.Movie;
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

  /**
   * method that returns similar movies to a given movie.
   *
   * @param m
   *          Movie to which we are trying to find similar movies.
   * @return list of similar movies.
   */
  public List<Movie> getSimilarMovies(Movie m) {
    List<Movie> lst = new LinkedList<Movie>();
    lst.add(m);
    return lst;
  }

}
