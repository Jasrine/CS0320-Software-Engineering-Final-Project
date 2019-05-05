package edu.brown.cs.jkst.suggest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.Test;

import edu.brown.cs.jkst.query.FilmQuery;

public class SuggestCommandTest {

  @Test
  public void testExecute() {
    FilmQuery.INSTANCE.createConnection();
    File file = new File("testing.txt");
    PrintWriter pw;
    try {
      pw = new PrintWriter(file);
      assertTrue(SuggestCommand.INSTANCE.execute("Bat", pw, true).trim()
          .equals(
              "Bat\n" +
                  "Zaw\n" +
                  "Zap\n" +
                  "Yap\n" +
                  "Yao"));
      assertTrue(SuggestCommand.INSTANCE.execute("Avenge", pw, true).trim()
          .equals(
              "Avenge\n" +
                  "Revenge\n" +
                  "Avenues\n" +
                  "Avengers: Infinity War\n" +
                  "Avengers: Age of Ultron"));

      pw.close();
    } catch (FileNotFoundException e) {
      System.out.println("File issue in test");
    } finally {
      file.delete();
    }
    FilmQuery.INSTANCE.closeConnection();
  }

  @Test
  public void testGetTextSuggestions() {
    FilmQuery.INSTANCE.createConnection();
    assertTrue(SuggestCommand.INSTANCE.getTextSuggestions("Bat").trim()
        .equals(
            "Bat\n" +
                "Zaw\n" +
                "Zap\n" +
                "Yap\n" +
                "Yao"));
    assertTrue(SuggestCommand.INSTANCE.getTextSuggestions("Avenge").trim()
        .equals(
            "Avenge\n" +
                "Revenge\n" +
                "Avenues\n" +
                "Avengers: Infinity War\n" +
                "Avengers: Age of Ultron"));

    assertTrue(SuggestCommand.INSTANCE.getTextSuggestions("xfgdxhchgv").trim()
        .equals(""));
    FilmQuery.INSTANCE.closeConnection();
  }
}
