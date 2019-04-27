package edu.brown.cs.jkst.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.brown.cs.jkst.trie.AutocorrectComparator;
import edu.brown.cs.jkst.trie.Trie;

/**
 * class that handles many SQL queries for the application.
 */
public final class FilmQuery {
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
  private static List<String> genreList = Arrays.asList(genres);
  private static List<String> decadeList = Arrays.asList(decades);

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
      conn = DriverManager.getConnection("jdbc:sqlite:data/imdb5.db");
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
        // return 0;
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
    System.out.println(search);
    results = trie.findLedWords(results, search, 2);
    results = trie.findPrefixedWords(search, results);

    // put in unsorted list
    LinkedList<String> unsortedResults = new LinkedList<String>();
    for (String result : results) {
      unsortedResults.add(result);
    }

    // sort these
    Collections.sort(unsortedResults, new AutocorrectComparator(search));

    int start = 0;
    StringBuilder sb = new StringBuilder();

    while (start < numResults && !unsortedResults.isEmpty()) {
      sb.append(unsortedResults.removeLast() + "\n");
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
          + "WHERE runtime_minutes > 5";
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
          for (String piece : txt.split(",")) {
            regionSet.add(piece);
          }
          // regions.add(rs.getString(1));
          System.out.println(rs.getString(1));
        }
      }
      rs.close();
      prep.close();

      for (String piece : regionSet) {
        regions.add(piece);
      }
      Collections.sort(regions);
    } catch (SQLException e) {
      System.out.println("ERROR: SQL Exception caught in addAllNames");
    }
  }
}
