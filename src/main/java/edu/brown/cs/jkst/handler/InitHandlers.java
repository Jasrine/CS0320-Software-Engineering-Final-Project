package edu.brown.cs.jkst.handler;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.jkst.query.FilmQuery;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateViewRoute;

/**
 * class that contains handlers specifically for initial display.
 */
public final class InitHandlers {

  private static final Gson GSON = new Gson();

  private InitHandlers() {
  }

  /**
   * Handle requests to the front page of our application.
   */
  public static class FrontInitHandler implements Route {
    @Override
    public String handle(Request req, Response res) {
      List<String> regions = FilmQuery.getRegions();
      List<String> genres = FilmQuery.getGenres();
      List<String> decades = FilmQuery.getDecades();
      Map<String, Object> variables = ImmutableMap.of("title",
          "Film suggestions", "suggestions", "\n", "regions", regions,
          "decades", decades, "genres", genres);
      return GSON.toJson(variables);
    }
  }

  /**
   * Handle requests to the front page of our application.
   */
  public static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      List<String> regions = FilmQuery.getRegions();
      List<String> genres = FilmQuery.getGenres();
      List<String> decades = FilmQuery.getDecades();
      Map<String, Object> variables = ImmutableMap.of("title",
          "Film suggestions", "suggestions", "\n", "regions", regions,
          "decades", decades, "genres", genres);
      return new ModelAndView(variables, "main.ftl");
    }
  }
}
