package edu.brown.cs.jkst.csvparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.Test;

/**
 * class that contains unit tests for CSV parser.
 */
public class CSVParserTest {
  private static final int ELEVEN = 11;

  /**
   * method that tests the readFile method in CSVParser.
   */
  @Test
  public void testReadFile() {
    File file = new File("testing.txt");
    PrintWriter pw;
    try {
      pw = new PrintWriter(file);
      // test empty input
      String output = CSVParser.readFile(pw, "");
      assertEquals("ERROR: not a csv file.", output);

      // test file that doesn't exist.
      output = CSVParser.readFile(pw, "burp.csv");
      assertEquals("ERROR: File burp.csv not found in system!", output);

      // test file that isn't csv.
      output = CSVParser.readFile(pw, "data/autocorrect/sherlock.txt");
      assertEquals("ERROR: not a csv file.", output);

      // test file with 1 star.
      output = CSVParser.readFile(pw, "data/stars/one-star.csv");
      assertFalse(output.startsWith("ERROR:"));
      assertEquals(2, output.trim().split("\n").length);

      // test file with many stars.
      output = CSVParser.readFile(pw, "data/stars/ten-star.csv");
      assertFalse(output.startsWith("ERROR:"));
      assertEquals(ELEVEN, output.trim().split("\n").length);
      pw.close();
      file.delete();
    } catch (FileNotFoundException e) {
      System.out.println("Error in CSVParserTest testReadFile.");
    }
  }
}
