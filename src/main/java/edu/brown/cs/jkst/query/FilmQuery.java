package edu.brown.cs.jkst.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.jkst.trie.Trie;

/**
 * class that handles many SQL queries for the application.
 */
public final class FilmQuery {
  public static final FilmQuery INSTANCE = new FilmQuery();
  private static Trie<Character> trie = new Trie<Character>();
  private static Connection conn = null;

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
      trie = new Trie<Character>();
      addAllNames();

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
  public Set<String> findSuggestion(String search) {
    Set<String> results = new HashSet<>();
    results = trie.findLedWords(results, search, 2);
    results = trie.findPrefixedWords(search, results);
    return results;
  }

  /**
   * add all names to the trie.
   */
  public void addAllNames() {
    try {
      String q = "SELECT DISTINCT primary_title FROM titles";
      PreparedStatement prep = conn.prepareStatement(q);
      HashSet<String> names = new HashSet<String>();
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        names.add(rs.getString(1));
      }
      rs.close();
      prep.close();
      for (String name : names) {
        trie.insert(name);
      }
    } catch (SQLException e) {
      System.out.println("ERROR: SQL Exception caught in addAllNames");
    }
  }
}
