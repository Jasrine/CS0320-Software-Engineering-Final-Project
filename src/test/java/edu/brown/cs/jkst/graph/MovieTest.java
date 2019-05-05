package edu.brown.cs.jkst.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import edu.brown.cs.jkst.graphdata.Movie;

/**
 * class that contains tests for the movie class.
 */
public class MovieTest {
  private static final int CARMENCITA_YEAR = 1894;
  private static final int CARMENCITA_VOTES = 179;
  private static final int CARMENCITA_FAKE_VOTES = 234;
  private static final double CARMENCITA_FAKE_RATING = 8.0;
  private static final double CARMENCITA_RATING = 6.4;
  private static final int FAKEMOVIE_YEAR = 2013;
  private static final int FAKEMOVIE_VOTES = 34;
  private static final double RAWRANK1 = 11.455999755859375;
  private static final double RAWRANK2 = 1.7000000178813934;

  /**
   * method that tests getters and setters for Movie.
   */
  @Test
  public void testSettersAndGetters() {
    List<String> genres = new ArrayList<String>();
    genres.add("Documentary");
    genres.add("Short");
    List<String> regions = new ArrayList<String>();
    regions.add("Hungary");
    regions.add("France");
    Map<String, String> crew = new HashMap<String, String>();
    crew.put("actor", "Tom");
    crew.put("self", "Harry");
    Movie m = new Movie("tt0000001", "Carmencita", CARMENCITA_YEAR, genres,
        regions, CARMENCITA_RATING, CARMENCITA_VOTES);
    m.setCrew(crew);
    m.setImgURL("www.iamtired.com");
    assertEquals(m.getNodeId(), "tt0000001");
    assertTrue(m.getGenres().get(0).equals("Documentary"));
    assertTrue(m.getGenres().size() == 2);
    assertTrue(m.getCrew().get("actor").equals("Tom"));
    assertTrue(m.getCrew().size() == 2);
    assertTrue(m.getRegions().get(1).equals("France"));
    assertTrue(m.getRegions().size() == 2);
    assertTrue(m.getRating() == CARMENCITA_RATING);
    m.setRating(CARMENCITA_FAKE_RATING);
    assertTrue(m.getRating() == CARMENCITA_FAKE_RATING);
    assertTrue(m.getNumVotes() == CARMENCITA_VOTES);
    m.setVotes(CARMENCITA_FAKE_VOTES);
    assertEquals(m.getNumVotes(), CARMENCITA_FAKE_VOTES);
    assertTrue(m.getImageURL().equals("www.iamtired.com"));
  }

  /**
   * method that tests rank.
   */
  @Test
  public void testRank() {
    List<String> genres = new ArrayList<String>();
    genres.add("Documentary");
    genres.add("Short");
    genres.add("Romance");
    List<String> regions = new ArrayList<String>();
    regions.add("Hungary");
    regions.add("France");
    Map<String, String> crew = new HashMap<String, String>();
    crew.put("actor", "Tom");
    crew.put("self", "Harry");
    Movie m = new Movie("tt0000001", "Carmencita", CARMENCITA_YEAR, genres,
        regions, CARMENCITA_RATING, CARMENCITA_VOTES);
    m.setCrew(crew);
    m.setImgURL("www.iamtired.com");
    assertTrue(m.rawRank() == RAWRANK1);
    m.setVotes(0);
    assertTrue(m.rawRank() == 0);
    List<String> genres2 = new ArrayList<String>();
    genres2.add("Documentary");
    genres2.add("Comedy");
    genres2.add("");
    List<String> regions2 = new ArrayList<String>();
    regions.add("Hungary");
    regions.add("Germany");
    Map<String, String> crew2 = new HashMap<String, String>();
    crew2.put("actress", "Liz");
    crew2.put("actor", "Harry");
    Movie m2 = new Movie("tt0000002", "FakeMovie",
        FAKEMOVIE_YEAR, genres2, regions2, 5, FAKEMOVIE_VOTES);
    m2.setCrew(crew2);
    m2.setImgURL("www.iamhappy.com");
    assertTrue(m2.rawRank() == RAWRANK2);
    assertTrue(m.compareTo(m2) == 1);
    List<String> genres3 = new ArrayList<String>();
    genres3.add("Documentary");
    genres3.add("Short");
    genres3.add("Romance");
    List<String> regions3 = new ArrayList<String>();
    regions.add("Hungary");
    regions.add("France");
    Map<String, String> crew3 = new HashMap<String, String>();
    crew3.put("actress", "Liz");
    crew3.put("actor", "Harry");
    Movie m3 = new Movie("tt0000002", "FakeMovie2",
        FAKEMOVIE_YEAR, genres3, regions3, CARMENCITA_RATING, FAKEMOVIE_VOTES);
    m3.setCrew(crew2);
    m3.setImgURL("www.iamsad.com");
    assertTrue(m.scoreSimilarity(m3) == 3);
    Set<Movie> movies = new TreeSet<Movie>();
    movies.add(m2);
    movies.add(m3);
    Set<Movie> suggestions = m.suggest(movies);
    List<Movie> listSugg = new ArrayList<Movie>();
    for (Movie movie : movies) {
      listSugg.add(movie);
    }
    assertTrue(listSugg.get(0).equals(m2));
    assertTrue(listSugg.get(0).equals(m3));
  }

  /**
   * method that tests the equals method for the Movie class.
   */
  @Test
  public void testEquals() {
    List<String> genres = new ArrayList<String>();
    genres.add("Documentary");
    genres.add("Short");
    genres.add("Romance");
    List<String> regions = new ArrayList<String>();
    regions.add("Hungary");
    regions.add("France");
    Map<String, String> crew = new HashMap<String, String>();
    crew.put("actor", "Tom");
    crew.put("director", "Harry");
    Movie m = new Movie("tt0000001", "Carmencita", CARMENCITA_YEAR, genres,
        regions, CARMENCITA_RATING, CARMENCITA_VOTES);
    m.setCrew(crew);
    m.setImgURL("www.iamtired.com");
    List<String> genres2 = new ArrayList<String>();
    genres2.add("Documentary");
    genres2.add("Comedy");
    genres2.add("");
    List<String> regions2 = new ArrayList<String>();
    regions.add("Hungary");
    regions.add("Germany");
    Map<String, String> crew2 = new HashMap<String, String>();
    crew2.put("actress", "Liz");
    crew2.put("actor", "Harry");
    Movie m2 = new Movie("tt0000002", "FakeMovie", FAKEMOVIE_YEAR, genres2,
        regions2, 5, FAKEMOVIE_VOTES);
    m2.setCrew(crew2);
    m2.setImgURL("www.iamhappy.com");
    assertFalse(m.equals(m2));
    Movie m3 = new Movie("tt0000001", "Carmencita", CARMENCITA_YEAR, genres,
        regions, CARMENCITA_RATING, CARMENCITA_VOTES);
    m3.setImgURL("www.iamtired.com");
    assertTrue(m.equals(m3));
  }

  /**
   * tests the toString method for the Movie class.
   */
  @Test
  public void testToString() {
    List<String> genres = new ArrayList<String>();
    genres.add("Documentary");
    genres.add("Short");
    genres.add("Romance");
    List<String> regions = new ArrayList<String>();
    regions.add("Hungary");
    regions.add("France");
    Map<String, String> crew = new HashMap<String, String>();
    crew.put("actor", "Tom");
    crew.put("director", "Harry");
    Movie m = new Movie("tt0000001", "Carmencita", CARMENCITA_YEAR, genres,
        regions, CARMENCITA_RATING, CARMENCITA_VOTES);
    m.setCrew(crew);
    m.setImgURL("www.iamtired.com");
    assertEquals(m.toString().trim(), "Film name: Carmencita\n"
        + "Director: Harry\n"
        + "Year: 1894\n"
        + "Genres: [Documentary, Short, Romance]\n"
        + "Regions: Hungary, France");
  }

}
