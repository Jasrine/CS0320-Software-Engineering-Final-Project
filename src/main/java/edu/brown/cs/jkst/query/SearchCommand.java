package edu.brown.cs.jkst.query;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
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
  public String execute(String line, PrintWriter pw, Boolean repl) {
    // returns films similar to the selected film
    // also has to route to advanced search, and then ranker

    pw.println("No results found.");
    return "";
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
   * @param service
   *          which the movie should be available on.
   *
   * @return query for getting the right movies.
   */
  public String getQuery(String title, String decade, String region,
      String genres, String service) {
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
        String decPart = "premiered >= ? AND premiered <= ? ";
        if (connect) {
          sb.append("AND ").append(decPart);
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
        sb.append("AND ").append(regionPart);
      } else {
        sb.append(regionPart);
      }
    }

    if (genres != null && genres.length() > 0) {
      String genrePart = "genres LIKE ? ";
      if (connect) {
        sb.append("AND ").append(genrePart);
      } else {
        sb.append(genrePart);
      }
    }

    if (service != null && service.length() > 0) {
      String servicePart = "streaming_services LIKE ?";
      if (connect) {
        sb.append("AND ").append(servicePart);
      } else {
        sb.append(servicePart);
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
   * @param service
   *          which people want to find a movie on.
   *
   * @return List of similar movies.
   */
  public List<Movie> search(String title, String decade, String region,
      String genres, String service) {
    String queryString = getQuery(title, decade, region, genres, service);
    Connection conn = FilmQuery.getConn();
    List<Movie> output = new LinkedList<>();
    if (conn != null) {
      try {
        PreparedStatement prep = conn.prepareStatement(queryString);
        // setting variables
        int counter = 0;

        if (title != null && title.length() > 0) {
          counter++;
          prep.setString(counter, "%" + title + "%");
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

        int numResults = 0;
        while (rs.next() && numResults < NUM_RESULTS) {
          String id = rs.getString(1);
          List<String> regions = Arrays.asList(rs.getString(2).split(","));
          String filmName = rs.getString(3);
          String director = ""; // TODO: get actual director
          int year = rs.getInt(4);
          List<String> genreLst = Arrays.asList(rs.getString(6).split(","));
          double rating;
          String rate = rs.getString(7);
          if (rs.wasNull()) {
            rating = 0.0;
          } else {
            try {
              rating = Double.parseDouble(rate);
            } catch (NumberFormatException e) {
              rating = 0.0;
            }
          }
          int numVotes = rs.getInt(8);
          if (rs.wasNull()) {
            numVotes = 0;
          }
          String url = rs.getString(9);
          Movie m = new Movie(id, filmName, director, url, year, genreLst,
              regions, rating, numVotes);

          output.add(m);
          numResults++;
        }
        rs.close();
        prep.close();

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    Collections.sort(output);
    Collections.reverse(output);
    return output;
  }
}
