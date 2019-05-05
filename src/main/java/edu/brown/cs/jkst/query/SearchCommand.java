package edu.brown.cs.jkst.query;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import edu.brown.cs.jkst.graphdata.Movie;
import edu.brown.cs.jkst.main.CommandManager.Command;

/**
 * lets a user enter a search query.
 */
public final class SearchCommand implements Command {
  public static final SearchCommand INSTANCE = new SearchCommand();
  private static final int SEVEN = 7;
  private static final int EIGHT = 8;
  private static final int NINE = 9;
  private static final int DEC = 10;
  private static final int ELEVEN = 11;
  private static final int TWELVE = 12;
  private static final int NUM_RESULTS = 100;
  private static final double MILI = 0.001;
  private static Map<String, String> serviceMap = FilmQuery.getServiceMap();

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
    String queryString = "SELECT DISTINCT * FROM titles";
    StringBuilder sbFull = new StringBuilder(queryString);
    StringBuilder sb = new StringBuilder();
    boolean connect = false;

    if (title != null && title.length() > 0) {
      String titlePart = "primary_title LIKE ? ";
      connect = true;
      sb.append(titlePart);
    }

    if (decade != null && decade.length() > 0) {
      try {
        String decPart = "premiered BETWEEN ? AND ? ";
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
    if (connect) {
      sbFull.append(" WHERE ").append(sb);
    }
    return sbFull.toString();
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

        if (service != null && service.length() > 0) {
          counter++;
          prep.setString(counter, "%" + this.serviceMap.get(service) + "%");
        }

        ResultSet rs = prep.executeQuery();

        PriorityQueue<Movie> bestMovies = new PriorityQueue<>(NUM_RESULTS);

        int numResults = 0;
        while (rs.next()/* && numResults < NUM_RESULTS */) {
          String id = rs.getString(1);
          String regionsRaw = rs.getString(2);
          if (rs.wasNull()) {
            regionsRaw = "";
          }
          List<String> regions = Arrays.asList(regionsRaw.split(","));
          String filmName = rs.getString(3);
          if (rs.wasNull()) {
            filmName = "";
          }
          int year = rs.getInt(4);
          if (rs.wasNull()) {
            year = 0;
          }

          List<String> genreLst = Arrays.asList(rs.getString(6).split(","));
          if (rs.wasNull()) {
            genreLst = Collections.emptyList();
          }

          double rating;
          String rate = rs.getString(SEVEN);
          if (rs.wasNull()) {
            rating = 0.0;
          } else {
            try {
              rating = Double.parseDouble(rate);
            } catch (NumberFormatException e) {
              rating = 0.0;
            }
          }

          int numVotes = rs.getInt(EIGHT);
          if (rs.wasNull()) {
            numVotes = 0;
          }

          String url = rs.getString(NINE);
          if (rs.wasNull()) {
            url = "/css/images/Question-Mark.png";
          }
          Movie m = new Movie(id, filmName, year, genreLst,
              regions, rating, numVotes);

          m.setImgURL(url);

          String director = rs.wasNull() ? "" : rs.getString(ELEVEN);
          m.setDirector(director);

          String cast = rs.wasNull() ? "" : rs.getString(TWELVE);
          if (cast != null) {
            m.setCast(cast.split(","));
          }

          if (numResults < NUM_RESULTS) {
            bestMovies.add(m);
            numResults++;
          } else if (m.compareTo(bestMovies.peek()) > 0) {
            bestMovies.poll();
            bestMovies.add(m);
            assert (bestMovies.size() == NUM_RESULTS);
          }

          // output.add(m);
          // numResults++;
        }
        rs.close();
        prep.close();
        assert bestMovies.size() <= NUM_RESULTS;
        output.addAll(bestMovies);
        // getCrew(output);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    // Collections.sort(output);
    Collections.reverse(output);
    return output;
  }

  /**
   * helper method that gets and sets crew for a movie.
   *
   * @param moviesWithoutCrew
   *          list of movies for which the crew has not been set.
   * @return the same list of movies, with the crew set.
   */
  public List<Movie> getCrew(List<Movie> moviesWithoutCrew) {
    String queryString = "SELECT * FROM crew WHERE title_id = ?";
    Connection conn = FilmQuery.getConn();
    if (conn != null) {
      try {
        for (Movie m : moviesWithoutCrew) {
          PreparedStatement prep = conn.prepareStatement(queryString);
          prep.setString(1, m.getNodeId());
          ResultSet rs = prep.executeQuery();
          Map<String, String> crew = new HashMap<String, String>();

          while (rs.next()) {
            crew.put(rs.getString(3), rs.getString(4));
          }

          m.setCrew(crew);
          rs.close();
          prep.close();
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return moviesWithoutCrew;
  }
}
