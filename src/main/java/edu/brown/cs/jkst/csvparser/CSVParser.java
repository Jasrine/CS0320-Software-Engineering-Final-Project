package edu.brown.cs.jkst.csvparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import edu.brown.cs.jkst.main.CommonErrors;

/**
 * class that has methods for reading in a CSV file.
 */
public final class CSVParser {
  private CSVParser() {
  }

  /**
   * file for reading in CSV file.
   *
   * @param pw
   *          PrintWriter for printing to REPL if needed.
   * @param filename
   *          to be read in.
   * @return String containing the file.
   */
  public static String readFile(PrintWriter pw, String filename) {
    StringBuilder sb = new StringBuilder();
    if (!filename.endsWith(".csv")) {
      pw.println("ERROR: not a csv file.");
      return "ERROR: not a csv file.";
    }

    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      String line = br.readLine();
      while (line != null) {
        sb.append(line + "\n");
        line = br.readLine();
      }
      br.close();

    } catch (FileNotFoundException e) {
      pw.println(CommonErrors.fileNotFound(filename));
      return CommonErrors.fileNotFound(filename);
    } catch (IOException e) {
      pw.println(CommonErrors.fileReadingException());
      return CommonErrors.fileReadingException();
    }

    return sb.toString();
  }
}
