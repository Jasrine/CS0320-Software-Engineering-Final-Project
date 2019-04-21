package edu.brown.cs.jkst.handler;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.jkst.query.FilmQuery;
import edu.brown.cs.jkst.suggest.SuggestCommand;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateViewRoute;

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
      String suggestions = SuggestCommand.INSTANCE.getSuggestions(search);
      List<String> regions = FilmQuery.getRegions();
      Map<String, Object> variables = ImmutableMap.of("title",
          "Film suggestions", "suggestions", suggestions, "regions", regions);
      return GSON.toJson(variables);
    }
  }

  /**
   * Handles submit requests in the search bar on the front page of our
   * application.
   */
  public static class SearchSubmitHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title",
          "Film suggestions", "results", "results");
      return new ModelAndView(variables, "results.ftl");
    }
  }
}
