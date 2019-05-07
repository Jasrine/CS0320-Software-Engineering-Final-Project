package edu.brown.cs.jkst.movies;

/**
 * class that contains genre information.
 */
public class Genres {
  private String primary;
  private String secondary;
  private String tertiary;

  /**
   * Constructor for genres.
   *
   * @param primary
   *          first genre in list.
   * @param secondary
   *          second genre in list.
   * @param tertiary
   *          third genre in list.
   */
  public Genres(String primary, String secondary, String tertiary) {
    this.primary = primary;
    this.secondary = secondary;
    this.tertiary = tertiary;
  }

  /**
   * static method instead of instance method? Compares this object with the
   * specified object for similarity.
   *
   * @param that
   *          the object to be compared.
   * @return an integer assigning a score to the similarity of the two movies
   */
  public int SimilarityScore(Genres that) {
    int genreScore = 0;
    if (that.primary.equals(this.primary)) {
      genreScore += 39;
    } else if (that.primary.equals(this.secondary)) {
      genreScore += 36;
    } else if (that.primary.equals(this.tertiary)) {
      genreScore += 33;
    }
    if (that.secondary.equals(this.primary)) {
      genreScore += 26;
    } else if (that.secondary.equals(this.secondary)) {
      genreScore += 24;
    } else if (that.secondary.equals(this.tertiary)) {
      genreScore += 22;
    }
    if (that.tertiary.equals(this.primary)) {
      genreScore += 13;
    } else if (that.tertiary.equals(this.secondary)) {
      genreScore += 12;
    } else if (that.tertiary.equals(this.tertiary)) {
      genreScore += 11;
    }
    return genreScore;
  }
}
