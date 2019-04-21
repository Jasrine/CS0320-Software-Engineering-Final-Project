package edu.brown.cs.jkst.ranker;

/**
 * methods for ranking similarities between two movies.
 */
public final class Ranker {

  private static final int MAX_GENRE = 50;

  private Ranker() {

  }

  /**
   * method which returns an int score comparing two genres.
   *
   * @param genre1
   *          String first genre we're comparing.
   * @param genre2
   *          String second genre we're comparing.
   * @return int that is higher if there's greater similarity.
   */
  public static int scoreTwoGenres(String genre1, String genre2) {
    if (genre1.equals(genre2)) {
      return MAX_GENRE;
    }

    return 0;
  }

}
