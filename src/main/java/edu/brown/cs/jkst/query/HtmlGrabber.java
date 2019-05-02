package edu.brown.cs.jkst.query;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * web scraping HTML.
 */
public final class HtmlGrabber {

  private static final int MAX_ID_LENGTH = 7;
  private static final int MILLION = 1500000;
  private static final int MAX_NETFLIX_PAGES = 104;
  private static final int MAX_HULU_PAGES = 31;
  private static final int RESULTS_PER_HULU_PAGE = 50;

  private HtmlGrabber() {

  }

  /**
   * for grabbing netflix titles.
   */
  public static void grabNetflixTitles() {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter("netflixtitles.txt", "UTF-8");
      String urlBase = "https://flixable.com/genre/movies/?min-rating=0&min"
          + "-year=1920&max-year=2019&order=date&page=";
      for (int i = 1; i <= MAX_NETFLIX_PAGES; i++) {
        writer.print(grabNetflixHTML(urlBase + i));
      }
      writer.println();
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      e.printStackTrace();
    } finally {
      writer.close();
    }
  }

  /**
   * for grabbing hulu titles.
   */
  public static void grabHuluTitles() {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter("hulutitles.txt", "UTF-8");
      String urlBase = "https://reelgood.com/movies/source/hulu?offset=";
      for (int i = 0; i <= MAX_HULU_PAGES; i++) {
        writer.print(grabHuluHTML(urlBase + RESULTS_PER_HULU_PAGE * i));
      }
      writer.println();
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      e.printStackTrace();
    } finally {
      writer.close();
    }
  }

  /**
   * class that grabs the first million image files.
   */
  public static void grabFirstMillionFiles() {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter("titles-to-imageurl2.txt", "UTF-8");
      for (int i = 808423; i <= MILLION; i++) {
        grabSomeImgFiles(i, writer);
      }
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      e.printStackTrace();
    } finally {
      writer.close();
    }
  }

  /**
   * takes in an int, so it can get the corresponding title id. uses this title
   * id to produce the right url to send to grabHTML. then gets the grabbed
   * image file url and writes it to a file along with title.
   *
   * @param num
   *          an int specifying the place this title would take.
   * @param writer
   *          print writer for writing the url to a file.
   */
  public static void grabSomeImgFiles(int num, PrintWriter writer) {
    String numToString = String.valueOf(num);
    int numZeros = MAX_ID_LENGTH - numToString.length();
    String titleId = "tt";
    for (int i = 0; i < numZeros; i++) {
      titleId += "0";
    }
    titleId += numToString;
    String url = "https://www.imdb.com/title/" + titleId + "/mediaviewer";
    String imgURL = grabHTML(url);

    if (imgURL.length() > 0) {
      // write the image url and the titleId to a txt file.
      // System.out.println(titleId);
      writer.println(titleId + "\t" + imgURL);
    }

  }

  /**
   * grabs HTML from a page.
   *
   * @param url
   *          from which we'll grab a page.
   * @return String corresponding to image url.
   */
  public static String grabNetflixHTML(String url) {
    String content = null;
    URLConnection connection = null;
    try {
      connection = new URL(url).openConnection();
      // System.out.println(connection);
      Scanner scanner = new Scanner(connection.getInputStream());
      scanner.useDelimiter("\\Z");
      content = scanner.next();
      scanner.close();

      String[] pieces = content.split("<p><strong><a href=");
      StringBuilder sb = new StringBuilder();
      for (int i = 1; i < pieces.length; i++) {
        int startIndex = pieces[i].indexOf(">");
        int endIndex = pieces[i].indexOf("</p");
        String relevantPortion = pieces[i].substring(startIndex, endIndex);
        String[] portions = relevantPortion.split("</a></strong><br>");
        sb.append(portions[0] + "\t" + portions[1] + "\n");
      }

      return sb.toString();
    } catch (Exception ex) {
      // ex.printStackTrace();
    }

    return "";
  }

  /**
   * grabs HTML for getting hulu movies.
   *
   * @param url
   *          from which we'll grab a page.
   * @return String corresponding to image url.
   */
  public static String grabHuluHTML(String url) {
    String content = null;
    URLConnection connection = null;
    try {
      connection = new URL(url).openConnection();
      Scanner scanner = new Scanner(connection.getInputStream());
      scanner.useDelimiter("\\Z");
      content = scanner.next();
      scanner.close();

      String[] pieces = content.split("class=\"css-78jh1y\"><a href=\"/movie/");
      StringBuilder sb = new StringBuilder();
      for (int i = 1; i < pieces.length; i++) {
        int startIndex = pieces[i].indexOf(">");
        int endIndex = pieces[i].indexOf("</a");
        String movie = pieces[i].substring(startIndex + 1, endIndex);

        String year = pieces[i].split(
            "</td><td></td><td class=\"css-1u11l3y\">")[0];
        int yearEndIndex = year.indexOf("</td");
        year = year.substring(0, yearEndIndex);
        System.out.println(movie);
        // String[] portions = relevantPortion.split("</a>");
        sb.append(movie + "\t" + year + "\n");
      }

      return sb.toString();
    } catch (Exception ex) {
      // ex.printStackTrace();
    }

    return "";
  }

  /**
   * grabs HTML from a page.
   *
   * @param url
   *          from which we'll grab a page.
   * @return String corresponding to image url.
   */
  public static String grabHTML(String url) {
    String content = null;
    URLConnection connection = null;
    try {
      connection = new URL(url).openConnection();
      // System.out.println(connection);
      Scanner scanner = new Scanner(connection.getInputStream());
      scanner.useDelimiter("\\Z");
      content = scanner.next();
      scanner.close();

      String tag = "<meta itemprop=\"image\" content=\"";
      int index = content.indexOf(tag);

      if (index > 0) {
        content = content.substring(index + tag.length(), content.length() - 1);
        int endIndex = content.indexOf("\"/>");
        return content.substring(0, endIndex);
      }
    } catch (Exception ex) {
      // ex.printStackTrace();
    }

    return "";
  }
}
