package edu.brown.cs.jkst.query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * class with methods for testing Film Query functionality.
 */
public class FilmQueryTest {
  private static final int REGION_SIZE = 234;
  private static final int DECADE_NUM = 13;
  private static final int SERVICE_NUM = 11;
  private static final int GENRE_NUM = 22;

  /**
   * class that tests whether connections are created and closed properly.
   */
  @Test
  public void testConnection() {
    FilmQuery.INSTANCE.createConnection();
    assertTrue(FilmQuery.INSTANCE.getConn() != null);
    assertTrue(FilmQuery.INSTANCE.getConn() != null);
    assertEquals(FilmQuery.INSTANCE.getRegions().size(), REGION_SIZE);
    assertEquals(FilmQuery.getDecades().size(), DECADE_NUM);
    assertEquals(FilmQuery.getServices().size(), SERVICE_NUM);
    assertEquals(FilmQuery.getGenres().size(), GENRE_NUM);
    FilmQuery.INSTANCE.closeConnection();
  }

  /**
   * class that tests find suggestion.
   */
  @Test
  public void testFindSuggestion() {
    FilmQuery.INSTANCE.createConnection();
    assertEquals(FilmQuery.INSTANCE.findSuggestion("Bat").trim(), "Bat\n"
        + "Zaw\nZap\nYap\nYao");
    assertEquals(FilmQuery.INSTANCE.findSuggestion("Avenge").trim(),
        "Avenge\nRevenge\nAvenues\n"
            + "Avengers: Infinity War\nAvengers: Age of Ultron");
    assertEquals(FilmQuery.INSTANCE.findSuggestion("bvhgdved hkwbiw").trim(),
        "");
    FilmQuery.INSTANCE.closeConnection();
  }
}
