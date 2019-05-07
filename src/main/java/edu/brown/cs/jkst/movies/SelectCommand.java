package edu.brown.cs.jkst.movies;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import edu.brown.cs.jkst.graphdata.Movie;
import edu.brown.cs.jkst.main.CommandManager.Command;
import edu.brown.cs.jkst.query.FilmQuery;
import edu.brown.cs.jkst.query.SearchCommand;

/**
 * class that selects one of a list of search results.
 */
public final class SelectCommand implements Command {
  public static final SelectCommand INSTANCE = new SelectCommand();
  private static final int NUM_RESULTS = 100;

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
    Connection conn = FilmQuery.getConn();
    List<Movie> output = new LinkedList<>();
    if (conn != null) {
      try {
        PreparedStatement prep
                = conn.prepareStatement("SELECT DISTINCT * FROM titles");
        System.out.println("Getting similar movies...");
        output = FilmQuery.topMovies(prep, new RelativeSimilarity(m),
                NUM_RESULTS);
        System.out.println("Similar movies retrieved.");
//        getCrew(output);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
//    System.out.println("-----------------------------------------------------");
//    for(Movie mov : output) {
//      int castOverlap1 = 0;
//      for (String castMember : m.getCast()) {
//        if (!castMember.equals("")) {
//          if (mov.getCast().contains(castMember)) {
//            System.out.print(" <" + castMember + "> ");
//            castOverlap1++;
//          }
//        }
//      }
//      System.out.print("cast: " + castOverlap1);
//      boolean directorMatch1 = mov.getDirector().equals(m.getDirector());
//      System.out.print(" | dir: " + directorMatch1);
//      int genreScore1 = m.genreSimilarity2(mov);
//      System.out.print(" | genre: " + genreScore1);
//      int simScore = castOverlap1 + 1;
//      simScore *= (directorMatch1 ? 10 : 1);
//      simScore = (genreScore1 + simScore) / 2;
//      System.out.println(" | score: " + simScore + ": " + mov.getFilmname());
//    }
    output.sort(new RelativeSimilarity(m));
    Collections.reverse(output);
    return output;
  }
}
