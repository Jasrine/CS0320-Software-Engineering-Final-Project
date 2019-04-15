package edu.brown.cs.kpal1sa38.KDTreeData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a public class wherein the methods in this class parses
 * a document with desired splitting string.
 */
public abstract class Parser {
  /**
   * This method parses the document that is stored in the filepath given.
   * @param filePath - filepath of the document needed to parse.
   * @param splitBy - string with which the method should split the words by.
   * @return List of Array of Strings
   */
  public List<String[]> parse(String filePath, String splitBy) {
    BufferedReader br = null;
    String currentLine = "";
    List<String[]> data = new ArrayList<String[]>();
    try {
      br = new BufferedReader(new FileReader(filePath));
      while ((currentLine = br.readLine()) != null) {
        String[] params = currentLine.split(splitBy);
        data.add(params);
      }
    } catch (FileNotFoundException e) {
      System.out.println("ERROR: File cannot be found");
      return null;
    } catch (IOException e) {
      System.out.println("ERROR: IOException");
      return null;
    } finally {
      try {
        if (br != null) {
          br.close();
        }
      } catch (IOException e) {
        System.out.println("ERROR: IOException");
        return null;
      }
    }
    return data;
  }
}
