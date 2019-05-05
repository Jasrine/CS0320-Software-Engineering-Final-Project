package edu.brown.cs.jkst.graph;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import edu.brown.cs.jkst.graphdata.Movie;

public class MovieTest {

  @Test
  public void testSettersAndGetters() {
    List<String> genres = new ArrayList<String>();
    genres.add("Documentary");
    genres.add("Short");
    List<String> regions = new ArrayList<String>();
    regions.add("Hungary");
    regions.add("France");
    List<String> crew = new ArrayList<String>();
    crew.add("Tom");
    crew.add("Harry");
    Movie m = new Movie("tt0000001", "Carmencita", "William K.L. Dickson",
        "www.iamtired.com", 1894, genres, regions, 6.4, 179);
    m.setCrew(crew);
    assertTrue(m.getNodeId().equals("tt0000001"));
    assertTrue(m.getDirector().equals("William K.L. Dickson"));
    assertTrue(m.getGenres().get(0).equals("Documentary"));
    assertTrue(m.getGenres().size() == 2);
    assertTrue(m.getCrew().get(0).equals("Tom"));
    assertTrue(m.getCrew().size() == 2);
    assertTrue(m.getRegions().get(1).equals("France"));
    assertTrue(m.getRegions().size() == 2);
    assertTrue(m.getRating() == 6.4);
    m.setRating(8);
    assertTrue(m.getRating() == 8);
    assertTrue(m.getNumVotes() == 179);
    m.setVotes(234);
    assertTrue(m.getNumVotes() == 234);
    assertTrue(m.getImageURL().equals("www.iamtired.com"));
  }

  @Test
  public void testRank() {
    List<String> genres = new ArrayList<String>();
    genres.add("Documentary");
    genres.add("Short");
    genres.add("Romance");
    List<String> regions = new ArrayList<String>();
    regions.add("Hungary");
    regions.add("France");
    List<String> crew = new ArrayList<String>();
    crew.add("Tom");
    crew.add("Harry");
    Movie m = new Movie("tt0000001", "Carmencita", "William K.L. Dickson",
        "www.iamtired.com", 1894, genres, regions, 6.4, 179);
    m.setCrew(crew);
    assertTrue(m.rawRank() == 11.455999755859375);
    m.setVotes(0);
    assertTrue(m.rawRank() == 0);
    List<String> genres2 = new ArrayList<String>();
    genres2.add("Documentary");
    genres2.add("Comedy");
    genres2.add("");
    List<String> regions2 = new ArrayList<String>();
    regions.add("Hungary");
    regions.add("Germany");
    List<String> crew2 = new ArrayList<String>();
    crew2.add("Liz");
    crew2.add("Harry");
    Movie m2 = new Movie("tt0000002", "FakeMovie", "Mike",
        "www.iamhappy.com", 2013, genres2, regions2, 5, 34);
    m2.setCrew(crew2);
    assertTrue(m2.rawRank() == 1.7000000178813934);
    assertTrue(m.compareTo(m2) == 1);
    List<String> genres3 = new ArrayList<String>();
    genres3.add("Documentary");
    genres3.add("Short");
    genres3.add("Romance");
    List<String> regions3 = new ArrayList<String>();
    regions.add("Hungary");
    regions.add("France");
    List<String> crew3 = new ArrayList<String>();
    crew3.add("Liz");
    crew3.add("Harry");
    Movie m3 = new Movie("tt0000002", "FakeMovie2", "William K.L. Dickson",
        "www.iamsad.com", 2013, genres3, regions3, 6.4, 34);
    m2.setCrew(crew2);
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

  @Test
  public void testEquals() {
    List<String> genres = new ArrayList<String>();
    genres.add("Documentary");
    genres.add("Short");
    genres.add("Romance");
    List<String> regions = new ArrayList<String>();
    regions.add("Hungary");
    regions.add("France");
    List<String> crew = new ArrayList<String>();
    crew.add("Tom");
    crew.add("Harry");
    Movie m = new Movie("tt0000001", "Carmencita", "William K.L. Dickson",
        "www.iamtired.com", 1894, genres, regions, 6.4, 179);
    m.setCrew(crew);
    List<String> genres2 = new ArrayList<String>();
    genres2.add("Documentary");
    genres2.add("Comedy");
    genres2.add("");
    List<String> regions2 = new ArrayList<String>();
    regions.add("Hungary");
    regions.add("Germany");
    List<String> crew2 = new ArrayList<String>();
    crew2.add("Liz");
    crew2.add("Harry");
    Movie m2 = new Movie("tt0000002", "FakeMovie", "Mike",
        "www.iamhappy.com", 2013, genres2, regions2, 5, 34);
    m2.setCrew(crew2);
    assertTrue(m.equals(m2) == false);
    Movie m3 = new Movie("tt0000001", "Carmencita", "William K.L. Dickson",
        "www.iamtired.com", 1894, genres, regions, 6.4, 179);
    assertTrue(m.equals(m3) == true);
  }

  @Test
  public void testToString() {
    List<String> genres = new ArrayList<String>();
    genres.add("Documentary");
    genres.add("Short");
    genres.add("Romance");
    List<String> regions = new ArrayList<String>();
    regions.add("Hungary");
    regions.add("France");
    List<String> crew = new ArrayList<String>();
    crew.add("Tom");
    crew.add("Harry");
    Movie m = new Movie("tt0000001", "Carmencita", "William K.L. Dickson",
        "www.iamtired.com", 1894, genres, regions, 6.4, 179);
    m.setCrew(crew);
    assertTrue(m.toString().trim().equals("Film name: Carmencita\n" +
        "Director: William K.L. Dickson\n" +
        "Year: 1894\n" +
        "Genres: [Documentary, Short, Romance]\n" +
        "Regions: Hungary, France"));
  }

}
