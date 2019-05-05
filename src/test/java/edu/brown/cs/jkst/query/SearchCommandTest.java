package edu.brown.cs.jkst.query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SearchCommandTest {

  @Test
  public void testGetQuery() {
    String query1 = "SELECT DISTINCT * FROM titles WHERE primary_title LIKE ? AND premiered >= ? AND premiered <= ? AND region LIKE ? AND genres LIKE ? AND streaming_services LIKE ?";
    String query2 = "SELECT DISTINCT * FROM titles WHERE primary_title LIKE ?";
    String query3 = "SELECT DISTINCT * FROM titles WHERE primary_title LIKE ? AND genres LIKE ?";
    String query4 = "SELECT DISTINCT * FROM titles WHERE primary_title LIKE ? AND region LIKE ?";
    String query5 = "SELECT DISTINCT * FROM titles WHERE streaming_services LIKE ?";
    assertEquals(query2, SearchCommand.INSTANCE.getQuery("Carmencita", null,
        null,
        null, null).trim());
    assertEquals(query1, SearchCommand.INSTANCE.getQuery("Carmencita", "1900",
        "Hungary",
        "Short,Documentary", "Netflix").trim());
    assertEquals(query3, SearchCommand.INSTANCE.getQuery("Avengers",
        null, null, "Action", null).trim());
    assertEquals(query4, SearchCommand.INSTANCE.getQuery("Carmencita", null,
        "Hungary,Greece,Russian Federation,United States",
        null, null).trim());
    assertEquals(query5, SearchCommand.INSTANCE.getQuery(null, null,
        null,
        null, "Hulu").trim());
  }

  @Test
  public void testSuggestions() {
    FilmQuery.INSTANCE.createConnection();
    assertTrue(SearchCommand.INSTANCE.search("Scavengers", null,
        null, null, null).size() == 22);
    assertTrue(SearchCommand.INSTANCE.search("Avengers", null,
        null, null, null).size() == 58);
    assertTrue(SearchCommand.INSTANCE.search("Avengers", "2010s",
        null, null, null).size() == 33);
    String region = "Russian Federation,United States,World Wide,Spain,Poland,Ukraine,Romania,Vietnam,Serbia,Greece,Lithuania,Estonia,Italy,France,Argentina,Hungary,Turkey,Thailand,Bulgaria,Slovenia,Brazil,Germany,Portugal";
    assertTrue(SearchCommand.INSTANCE.search("Avengers", "2010s",
        region, null, null).size() == 1);
    FilmQuery.INSTANCE.closeConnection();
  }

}
