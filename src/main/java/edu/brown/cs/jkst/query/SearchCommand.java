package edu.brown.cs.jkst.query;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.brown.cs.jkst.graphdata.Movie;
import edu.brown.cs.jkst.main.CommandManager.Command;

/**
 * lets a user enter a search query.
 */
public final class SearchCommand implements Command {
  public static final SearchCommand INSTANCE = new SearchCommand();
  private static final int DEC = 10;
  private static final int NUM_RESULTS = 100;

  @Override
  public void execute(String line, PrintWriter pw, Boolean repl) {
    // returns films similar to the selected film
    // also has to route to advanced search, and then ranker
    pw.println("No results found.");
  }

  /**
   * helper method called by search, which returns a useful query to turn into a
   * prepared statement to pass to FilmQuery.
   *
   * @param title
   *          of film.
   * @param decade
   *          to which the desired film may belong.
   * @param region
   *          of film.
   * @param genres
   *          which we wish to include in our search.
   *
   * @return query for getting the right movies.
   */
  public String getQuery(String title, String decade, String region,
      String genres) {
    String queryString = "SELECT DISTINCT * FROM titles WHERE ";
    StringBuilder sb = new StringBuilder();
    sb.append(queryString);
    boolean connect = false;

    if (title != null && title.length() > 0) {
      String titlePart = "primary_title LIKE ? ";
      connect = true;
      sb.append(titlePart);
    }

    if (decade != null && decade.length() > 0) {
      try {
        int decStart = Integer.parseInt(decade.substring(0, decade.length()
            - 1));
        int decEnd = decStart + DEC;
        String decPart = "premiered > ? AND premiered < ? ";
        if (connect) {
          sb.append("OR " + decPart);
        } else {
          sb.append(decPart);
        }

        connect = true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if (region != null && region.length() > 0) {
      String regionPart = "region LIKE ? ";
      if (connect) {
        sb.append("OR " + regionPart);
      } else {
        sb.append(regionPart);
      }
    }

    if (genres != null && genres.length() > 0) {
      String genrePart = "genres LIKE ? ";
      if (connect) {
        sb.append("OR " + genrePart);
      } else {
        sb.append(genrePart);
      }
    }

    return sb.toString();
  }

  /**
   * method called by handler when a user enters a query for film suggestions.
   * cleans information and then passes it along to advanced search, followed by
   * ranker.
   *
   * @param title
   *          of film.
   * @param decade
   *          to which the desired film may belong.
   * @param region
   *          of film.
   * @param genres
   *          which we wish to include in our search.
   *
   * @return List of similar movies.
   */
  public List<Movie> search(String title, String decade, String region,
      String genres) {
    String queryString = getQuery(title, decade, region, genres);
    Connection conn = FilmQuery.getConn();
    if (conn != null) {
      try {
        PreparedStatement prep = conn.prepareStatement(queryString);

        // setting variables
        int counter = 0;

        if (title != null && title.length() > 0) {
          String titlePart = "title LIKE ? ";
          counter++;
          prep.setString(counter, title);
        }

        if (decade != null && decade.length() > 0) {
          try {
            int decStart = Integer.parseInt(decade.substring(0, decade.length()
                - 1));
            int decEnd = decStart + DEC;

            counter++;
            prep.setInt(counter, decStart);
            counter++;
            prep.setInt(counter, decEnd);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

        if (region != null && region.length() > 0) {
          counter++;
          prep.setString(counter, region);
        }

        if (genres != null && genres.length() > 0) {
          counter++;
          prep.setString(counter, "%" + genres + "%");
        }

        ResultSet rs = prep.executeQuery();
        List<Movie> output = new LinkedList<Movie>();
        // Set<Movie> poss = new HashSet<Movie>();

        int numResults = 0;
        while (rs.next() && numResults < NUM_RESULTS) {
          String id = rs.getString(1);
          List<String> regions = Arrays.asList(rs.getString(2).split(","));
          String filmName = rs.getString(3);
          String director = "";
          int year = rs.getInt(4);
          List<String> genreLst = Arrays.asList(rs.getString(6).split(","));
          Movie m = new Movie(id, filmName, director, year, genreLst, regions);

          // poss.add(m);
          output.add(m);
          System.out.println(m.toString());
          numResults++;
        }
        rs.close();
        prep.close();

        // Collections.sort(output);

        return output;
      } catch (SQLException e) {
        e.printStackTrace();
      }

    }

    return new LinkedList<Movie>();
  }
}
