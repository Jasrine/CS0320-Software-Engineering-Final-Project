package edu.brown.cs.jkst.handler;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.jkst.graphdata.Movie;
import edu.brown.cs.jkst.movies.SelectCommand;
import edu.brown.cs.jkst.query.FilmQuery;
import edu.brown.cs.jkst.query.SearchCommand;
import edu.brown.cs.jkst.suggest.SuggestCommand;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * contains handlers for displaying pages and handling requests.
 */
public final class Handler {
  private static final Gson GSON = new Gson();

  private Handler() {
  }

  /**
   * Handles requests in the search bar on the front page of our application so
   * that this can be autocorrected.
   */
  public static class SearchSuggestHandler implements Route {
    @Override
    public String handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String search = qm.value("search");
      String region = qm.value("region");
      String genre = qm.value("genre");
      String decade = qm.value("decade");
      String service = qm.value("service");
      String suggestions = SuggestCommand.INSTANCE.getTextSuggestions(search);
      List<String> regions = FilmQuery.getRegions();
      List<String> genres = FilmQuery.getGenres();
      List<String> decades = FilmQuery.getDecades();
      List<String> services = FilmQuery.getServices();
      Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
          .put("title", "Film suggestions")
          .put("suggestions", suggestions)
          .put("regions", regions)
          .put("genres", genres)
          .put("decades", decades)
          .put("services", services).build();

      // ImmutableMap.of("title",
      // "Film suggestions", "suggestions", suggestions, "regions", regions,
      // "genres", genres, "decades", decades, "services", services);
      return GSON.toJson(variables);
    }
  }

  /**
   * Handles submit requests in the search bar on the front page of our
   * application.
   */
  public static class SearchSubmitHandler implements Route {
    @Override
    public String handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String search = qm.value("search");
      String region = qm.value("region");
      String genre = qm.value("genre");
      String decade = qm.value("decade");
      String service = qm.value("service");
      // System.out.println(service + " " + decade + " " + genre + " " +
      // search);
      List<Movie> results = SearchCommand.INSTANCE.search(search, decade,
          region, genre, service);
      // System.out.println(results.toString());
      Map<String, Object> variables = ImmutableMap.of("title",
          "Film suggestions", "results", results);
      return GSON.toJson(variables);
    }
  }

  /**
   * handler that returns list of similar movies to a given movie to front end.
   */
  public static class SimilarityHandler implements Route {
    @Override
    public String handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();

      String filmId = qm.value("id");
      String filmName = qm.value("filmName");
      int filmYear = (qm.value("year") != null) ? Integer.parseInt(qm.value(
          "year")) : 0;
      String filmDirector = qm.value("director");
      String filmRegion = qm.value("region") != null ? qm.value("region") : "";
      List<String> filmRegions = new LinkedList<String>();
      for (String regions : filmRegion.split(", ")) {
        filmRegions.add(regions);
      }

      String filmGenre = qm.value("genres") != null ? qm.value("genres") : "";
      List<String> filmGenres = new LinkedList<String>();
      for (String genres : filmGenre.split(", ")) {
        filmGenres.add(genres);
      }
      double rating = qm.value("rating") != null ? Double.parseDouble(qm.value(
          "rating")) : 0.0;
      int votes = qm.value("votes") != null ? Integer.parseInt(qm.value(
          "votes")) : 0;
      Movie m = new Movie(filmId, filmName, filmYear, filmGenres, filmRegions,
          rating, votes);
      m.setDirector(filmDirector);

      String cast = qm.value("cast") != null ? qm.value("cast") : "";
      m.setCast(cast.split(","));

      List<Movie> results = SelectCommand.INSTANCE.getSimilarMovies(m);
      List<String> regions = FilmQuery.getRegions();
      List<String> genres = FilmQuery.getGenres();
      List<String> decades = FilmQuery.getDecades();
      List<String> services = FilmQuery.getServices();

      System.out.println("Printing similar movies");
      for (Movie r : results) {
        System.out.println(r);
      }
      Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
          .put("title", "Film suggestions")
          .put("results", results)
          .put("regions", regions)
          .put("genres", genres)
          .put("decades", decades)
          .put("services", services).build();

      return GSON.toJson(variables);
    }
  }
}
