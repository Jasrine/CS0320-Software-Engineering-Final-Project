package edu.brown.cs.jkst.handler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * contains handlers for displaying pages and handling requests.
 */
public final class Handler {
  private static final Gson GSON = new Gson();
  
  /**
   * Handle requests to the front page of our application.
   */
  public static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title", "Film suggestions");
      return new ModelAndView(variables, "main.ftl");
    }
  }
}
