package edu.brown.cs.jkst.query;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * class that handles getting new txt files containing titles.
 */
public final class FileCleaner {
  private static Connection conn = null;

  private FileCleaner() {
  }

  /**
   * method that calls cleanFile with appropriate arguments to clean all
   * generated files.
   */
  public static void cleanFiles() {
    createConnection();
    cleanFile("mubititles.txt", "mubititleids.txt");
  }

  /**
   * Method to create a connection to the database.
   *
   * @return 1 - if there is an error, or 0 if there is no error.
   */
  public static int createConnection() {
    try {
      Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection("jdbc:sqlite:data/imdb9.db");
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
    }
  }

  /**
   * clean a file.
   *
   * @param filetoRead
   *          file that is being read in.
   * @param fileToWrite
   *          file that is being written.
   */
  public static void cleanFile(String filetoRead, String fileToWrite) {
    PrintWriter writer = null;
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(filetoRead));
      writer = new PrintWriter(fileToWrite, "UTF-8");
      String line = reader.readLine();

      String baseQ = "SELECT title FROM titles WHERE primary_title LIKE ?";
      String yearPiece = " AND premiered = ?";
      while (line != null) {
        String movie = line.split("\t")[0];
        String year = line.split("\t")[1];

        String query = (year.length() > 0) ? baseQ + yearPiece : baseQ;

        PreparedStatement prep = conn.prepareStatement(query);
        prep.setString(1, movie);
        if (year.length() > 0) {
          prep.setInt(2, Integer.parseInt(year));
        }

        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
          writer.println(rs.getString(1));
          System.out.println(rs.getString(1));
        }
        rs.close();
        prep.close();

        line = reader.readLine();
      }

      writer.println();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      writer.close();
    }
  }
}
