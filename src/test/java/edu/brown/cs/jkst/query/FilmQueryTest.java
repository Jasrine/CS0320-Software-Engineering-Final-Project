package edu.brown.cs.jkst.query;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FilmQueryTest {

  @Test
  public void testConnection() {
    assertTrue(FilmQuery.INSTANCE.getConn() == null);
    FilmQuery.INSTANCE.createConnection();
    assertTrue(FilmQuery.getConn() != null);
    assertTrue(FilmQuery.getConn() != null);
    assertTrue(FilmQuery.INSTANCE.getRegions().size() == 235);
    assertTrue(FilmQuery.INSTANCE.getDecades().size() == 13);
    assertTrue(FilmQuery.INSTANCE.getServices().size() == 10);
    assertTrue(FilmQuery.INSTANCE.getGenres().size() == 22);
    FilmQuery.INSTANCE.closeConnection();
  }

  @Test
  public void testFindSuggestion() {
    FilmQuery.INSTANCE.createConnection();
    assertTrue(FilmQuery.INSTANCE.findSuggestion("Bat").trim().equals("Bat\n" +
        "Zaw\n" +
        "Zap\n" +
        "Yap\n" +
        "Yao"));
    assertTrue(FilmQuery.INSTANCE.findSuggestion("Avenge").trim().equals(
        "Avenge\n" +
            "Revenge\n" +
            "Avenues\n" +
            "Avengers: Infinity War\n" +
            "Avengers: Age of Ultron"));
    assertTrue(FilmQuery.INSTANCE.findSuggestion("bvhgdved hkwbiw").trim()
        .equals(""));
    FilmQuery.INSTANCE.closeConnection();
  }
}
