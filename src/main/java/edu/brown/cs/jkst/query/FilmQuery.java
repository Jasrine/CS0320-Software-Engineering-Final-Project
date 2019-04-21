package edu.brown.cs.jkst.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
      "Music", "Musical", "Action", "Adventure", "Film Noir", "Mystery",
      "Thriller", "Horror", "Fantasy", "Western", "War", "Animation",
      "Biography", "Crime"
  };

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

      // loading trie
      trie = new Trie<Character>();
      addAllNames();
      System.out.println("done loading in trie");

      // loading in regions
      addAllRegions();
      System.out.println("done loading in regions");
      return 0;
    } catch (ClassNotFoundException e) {
      System.out.println(
          "ERROR: ClassNotFound Exception" + "(in creating connection)");
      return 1;
    } catch (SQLException e) {
      System.out.println("ERROR: SQL Exception (in creating connection)");
      return 1;
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
          + "WHERE runtime_minutes > 3";
      PreparedStatement prep = conn.prepareStatement(q);
      // HashSet<String> names = new HashSet<String>();
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        trie.insert(rs.getString(1));
        // names.add(rs.getString(1));
      }
      rs.close();
      prep.close();
      // for (String name : names) {
      // trie.insert(name);
      // System.out.println(name);
      // }
    } catch (SQLException e) {
      System.out.println("ERROR: SQL Exception caught in addAllNames");
    }
  }

  /**
   * add all regions to the list of strings for regions.
   */
  public void addAllRegions() {
    try {
      String q = "SELECT DISTINCT region FROM akas;";
      PreparedStatement prep = conn.prepareStatement(q);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        regions.add(rs.getString(1));
      }
      rs.close();
      prep.close();
      Collections.sort(regions);
    } catch (SQLException e) {
      System.out.println("ERROR: SQL Exception caught in addAllNames");
    }
  }

  /**
   * add all decades to the list of strings for regions.
   */
  public void addAllDecades() {
    try {
      String q = "SELECT DISTINCT premiered FROM titles;";
      PreparedStatement prep = conn.prepareStatement(q);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        regions.add(rs.getString(1));
      }
      rs.close();
      prep.close();
      Collections.sort(regions);
    } catch (SQLException e) {
      System.out.println("ERROR: SQL Exception caught in addAllNames");
    }
  }

  /**
   * add all decades to the list of strings for regions.
   */
  public void addAllGenres() {
    try {
      String q = "SELECT DISTINCT premiered FROM titles;";
      PreparedStatement prep = conn.prepareStatement(q);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        regions.add(rs.getString(1));
      }
      rs.close();
      prep.close();
      Collections.sort(regions);
    } catch (SQLException e) {
      System.out.println("ERROR: SQL Exception caught in addAllNames");
    }
  }
}
