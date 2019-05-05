package edu.brown.cs.jkst.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import edu.brown.cs.jkst.graphdata.Movie;
import edu.brown.cs.jkst.trie.AutocorrectComparator;
import edu.brown.cs.jkst.trie.Trie;

/**
 * class that handles many SQL queries for the application.
 */
public final class FilmQuery {
  public static int ONE = 1;
  public static int TWO = 2;
  public static int THREE = 3;
  public static int FOUR = 4;
  public static int FIVE = 5;
  public static int SIX = 6;
  public static int SEVEN = 7;
  public static int EIGHT = 8;
  public static int NINE = 9;
  public static int TEN = 10;
  public static int ELEVEN = 11;
  public static int TWELVE = 12;
  public static final FilmQuery INSTANCE = new FilmQuery();
  private static Trie<Character> trie = new Trie<Character>();
  private static List<String> regions = new LinkedList<String>();
  private static int numResults = 5;
  private static Connection conn = null;
  private static String[] genres = {
      "Short", "Comedy", "Documentary", "Sport", "Romance", "Family", "Drama",
      "Music", "Musical", "Action", "Adventure", "Noir", "Mystery",
      "Thriller", "Horror", "Fantasy", "Western", "War", "Animation",
      "Biography", "Crime", "History"
  };
  private static String[] decades = {
      "1890s", "1900s", "1910s", "1920s", "1930s", "1940s", "1950s",
      "1960s", "1970s", "1980s", "1990s", "2000s", "2010s"
  };
  private static String[] services = {
      "Netflix", "Hulu", "Amazon Prime Video", "Showtime", "Sundance TV",
      "Epix", "Starz", "Hallmark", "Free Online Streaming Services", "HBO",
      "MUBI"
  };
  private static String[] serviceDB = {
      "Netflix", "Hulu", "PrimeVideo", "Showtime", "Sundance",
      "Epix", "Starz", "Hallmark", "Free", "HBO", "MUBI"
  };
  private static List<String> genreList = Arrays.asList(genres);
  private static List<String> decadeList = Arrays.asList(decades);
  private static List<String> serviceList = Arrays.asList(services);
  private static Map<String, String> serviceMap = new HashMap<String, String>();

  /**
   * Constructor for FilmQuery.
   */
  private FilmQuery() {
  }

  /**
   * getter for Connection.
   *
   * @return Connection to database.
   */
  public static Connection getConn() {
    return conn;
  }

  /**
   * Closes the connection.
   */
  public void closeConnection() {
    try {
      conn.close();
    } catch (SQLException e) {
      System.out.println("ERROR: SQL connection problem in closing!");
    }
    regions = new LinkedList<String>();
  }

  /**
   * getter for the alphabetically sorted list of regions available to the user.
   *
   * @return List of String with regions.
   */
  public static List<String> getRegions() {
    return regions;
  }

  /**
   * getter for the chronologically sorted list of decades from which the user
   * can choose a film in advanced search.
   *
   * @return List of String with decades.
   */
  public static List<String> getDecades() {
    return decadeList;
  }

  /**
   * getter for the list of services the user can choose from in advanced
   * search.
   *
   * @return List of String with streaming services we have information for.
   */
  public static List<String> getServices() {
    return serviceList;
  }

  /**
   * getter for the list of services the user can choose from in advanced
   * search.
   *
   * @return List of String with streaming services we have information for.
   */
  public static Map<String, String> getServiceMap() {
    if (serviceMap.keySet().isEmpty()) {
      int i = 0;
      for (String serviceDisplay : serviceList) {
        serviceMap.put(serviceDisplay, serviceDB[i]);
        i++;
      }
    }

    return serviceMap;
  }

  /**
   * getter for the alphabetically sorted list of regions available to the user.
   *
   * @return List of String with regions.
   */
  public static List<String> getGenres() {
    Collections.sort(genreList);
    return genreList;
  }

  /**
   * Method to create a connection to the database.
   *
   * @return 1 - if there is an error, or 0 if there is no error.
   */
  public int createConnection() {
    try {
      Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection("jdbc:sqlite:data/imdb.db");
      Statement stat = conn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys = ON;");

      // clean up cache at this point if a cache exists
      return 0;
    } catch (ClassNotFoundException e) {
      System.out.println(
          "ERROR: ClassNotFound Exception" + "(in creating connection)");
      return 1;
    } catch (SQLException e) {
      System.out.println("ERROR: SQL Exception (in creating connection)");
      return 1;
    } finally {
      if (conn != null) {
        // loading trie
        trie = new Trie<Character>();
        addAllNames();
        System.out.println("done loading in trie");

        // loading in regions
        addAllRegions();
        System.out.println("done loading in regions");
        return 0;
      }

    }
  }

  /**
   * helper method called by CommandLine.
   *
   * @param search
   *          String searched for.
   * @return Set of suggestions from trie.
   */
  public String findSuggestion(String search) {
    Set<String> results = new HashSet<>();
    results = trie.findLedWords(results, search, 2);
    results = trie.findPrefixedWords(search, results);

    // put in unsorted list
    LinkedList<String> unsortedResults = new LinkedList<String>();
    unsortedResults.addAll(results);

    // sort these
    unsortedResults.sort(new AutocorrectComparator(search));

    int start = 0;
    StringBuilder sb = new StringBuilder();

    while (start < numResults && !unsortedResults.isEmpty()) {
      sb.append(unsortedResults.removeLast()).append("\n");
      start++;
    }

    return sb.length() == 0 ? sb.toString() : sb.substring(0, sb.length() - 1);
  }

  /**
   * add all names to the trie.
   */
  public void addAllNames() {
    try {
      String q = "SELECT DISTINCT primary_title FROM titles "
          + "WHERE runtime_minutes > 4 AND region IS NOT NULL "
          + "AND premiered IS NOT NULL AND ratings > 5 AND votes IS NOT NULL";
      PreparedStatement prep = conn.prepareStatement(q);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        trie.insert(rs.getString(1));
      }
      rs.close();
      prep.close();
    } catch (SQLException e) {
      System.out.println("ERROR: SQL Exception caught in addAllNames");
    }
  }

  /**
   * add all regions to the list of strings for regions.
   */
  public void addAllRegions() {
    try {
      String q = "SELECT DISTINCT region FROM titles;";
      PreparedStatement prep = conn.prepareStatement(q);
      ResultSet rs = prep.executeQuery();

      Set<String> regionSet = new HashSet<String>();
      while (rs.next()) {
        if (rs.getString(1) != null) {
          String txt = rs.getString(1);
          regionSet.addAll(Arrays.asList(txt.split(",")));
        }
      }
      rs.close();
      prep.close();

      regions.addAll(regionSet);
      Collections.sort(regions);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static List<Movie> topMovies(PreparedStatement prep,
                                      Comparator<Movie> comp,
                                      int max_num_results) throws SQLException {
    List<Movie> output = new LinkedList<>();
    ResultSet rs = prep.executeQuery();
    PriorityQueue<Movie> bestMovies
            = new PriorityQueue<>(max_num_results, comp);
    int numResults = 0;
    while (rs.next()) {
      String id = rs.getString(ONE);
      //TODO: this is where we'd get the movie from the cache, if we had one
      String regionsRaw = rs.getString(TWO);
      if (rs.wasNull()) {
        regionsRaw = "";
      }
      List<String> regions = Arrays.asList(regionsRaw.split(","));
      String filmName = rs.getString(THREE);
      if (rs.wasNull()) {
        filmName = "";
      }
      int year = rs.getInt(FOUR);
      if (rs.wasNull()) {
        year = 0;
      }
      List<String> genreLst = Arrays.asList(rs.getString(SIX).split(","));
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
      Movie m = new Movie(id, filmName, year, genreLst,
              regions, rating, numVotes);
      String url = rs.getString(NINE);
      if (!rs.wasNull()) {
        m.setImgURL(url);
      }
      String director = rs.wasNull() ? "" : rs.getString(ELEVEN);
      m.setDirector(director);

      String cast = rs.wasNull() ? "" : rs.getString(TWELVE);
      if (cast != null) {
        m.setCast(cast.split(","));
      }
      if (numResults < max_num_results) {
        bestMovies.add(m);
        numResults++;
      } else if (m.compareTo(bestMovies.peek()) > 0) {
        bestMovies.poll();
        bestMovies.add(m);
      }
    }
    rs.close();
    prep.close();
    assert bestMovies.size() <= max_num_results;
    output.addAll(bestMovies);
    Collections.reverse(output);
    return output;
  }

}
