package edu.brown.cs.jkst.suggest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.Test;

import edu.brown.cs.jkst.query.FilmQuery;

/**
 * class which contains methods for the SuggestCommand class.
 */
public class SuggestCommandTest {

  /**
   * tests the execute method implemented from the Command interface.
   */
  @Test
  public void testExecute() {
    FilmQuery.INSTANCE.createConnection();
    File file = new File("testing.txt");
    PrintWriter pw;
    try {
      pw = new PrintWriter(file);
      assertEquals(SuggestCommand.INSTANCE.execute("Bat", pw, true).trim(),
          "Bat\nZaw\nZap\nYap\nYao");
      assertEquals(SuggestCommand.INSTANCE.execute("Avenge", pw, true).trim(),
          "Avenge\nRevenge\nAvenues\nAvengers: Infinity War\n"
              + "Avengers: Age of Ultron");

      pw.close();
    } catch (FileNotFoundException e) {
      System.out.println("File issue in test");
    } finally {
      file.delete();
    }
    FilmQuery.INSTANCE.closeConnection();
  }

  /**
   * method that tests getTextSuggestions from the search command class.
   */
  @Test
  public void testGetTextSuggestions() {
    FilmQuery.INSTANCE.createConnection();
    assertEquals(SuggestCommand.INSTANCE.getTextSuggestions("Bat").trim(),
        "Bat\nZaw\nZap\nYap\nYao");
    assertEquals(SuggestCommand.INSTANCE.getTextSuggestions("Avenge").trim(),
        "Avenge\nRevenge\nAvenues\nAvengers: Infinity War\n"
            + "Avengers: Age of Ultron");

    assertTrue(SuggestCommand.INSTANCE.getTextSuggestions("xfgdxhchgv").trim()
        .equals(""));
    FilmQuery.INSTANCE.closeConnection();
  }
}
