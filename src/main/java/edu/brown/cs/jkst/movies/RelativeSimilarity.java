package edu.brown.cs.jkst.movies;

import edu.brown.cs.jkst.graphdata.Movie;

import java.util.Comparator;
import java.util.List;

public class RelativeSimilarity implements Comparator<Movie> {
  private Movie movie;
  public RelativeSimilarity(Movie movie) {
    this.movie = movie;
  }

  /**
   * Compares its two arguments for order.  Returns a negative integer,
   * zero, or a positive integer as the first argument is less than, equal
   * to, or greater than the second.<p>
   * <p>
   * In the foregoing description, the notation
   * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
   * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
   * <tt>0</tt>, or <tt>1</tt> according to whether the value of
   * <i>expression</i> is negative, zero or positive.<p>
   * <p>
   * The implementor must ensure that <tt>sgn(compare(x, y)) ==
   * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
   * implies that <tt>compare(x, y)</tt> must throw an exception if and only
   * if <tt>compare(y, x)</tt> throws an exception.)<p>
   * <p>
   * The implementor must also ensure that the relation is transitive:
   * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
   * <tt>compare(x, z)&gt;0</tt>.<p>
   * <p>
   * Finally, the implementor must ensure that <tt>compare(x, y)==0</tt>
   * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
   * <tt>z</tt>.<p>
   * <p>
   * It is generally the case, but <i>not</i> strictly required that
   * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
   * any comparator that violates this condition should clearly indicate
   * this fact.  The recommended language is "Note: this comparator
   * imposes orderings that are inconsistent with equals."
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return a negative integer, zero, or a positive integer as the
   * first argument is less than, equal to, or greater than the
   * second.
   * @throws NullPointerException if an argument is null and this
   *                              comparator does not permit null arguments
   * @throws ClassCastException   if the arguments' types prevent them from
   *                              being compared by this comparator.
   */
  @Override
  public int compare(Movie o1, Movie o2) {
//    String filmName;
////    String director;
////    int year;
//    List<String> genres;
//    List<String> crew;
//    List<String> regions;
////    double rating;
//    int numVotes;
//
//
//    int yearScore1 = Math.abs(o1.getYear() - movie.getYear());
//    int yearScore2 = Math.abs(o2.getYear() - movie.getYear());

//    return 0;

    return Double.compare(movie.compareTo(o1), movie.compareTo(o2));
  }

  public int compareDirectors(Movie o1, Movie o2) {
    String d1 = o1.getDirector();
    String d2 = o2.getDirector();
    String dm = movie.getDirector();
    if (!d1.equals(d2)) {
      if (d1.equals(dm)) {
        return 1;
      }
      if (d2.equals(dm)) {
        return -1;
      }
    }
    return 0;
  }

  boolean recentYearBias = true;
  boolean higherRateBias = true;

  public void toggleYearBias() {
    recentYearBias = !recentYearBias;
  }

  public void toggleRateBias() {
    higherRateBias = !higherRateBias;
  }

  //biased towards more recent years
  public int compareRelativeYear(Movie o1, Movie o2) {
    int y1 = o1.getYear();
    int y2 = o2.getYear();
    int ym = movie.getYear();
    int diff1 = -1 * Math.abs(y1 - ym);
    int diff2 = -1 * Math.abs(y2 - ym);
    if (recentYearBias && diff1 == diff2) {
      return Integer.compare(y1, y2);
    }
    return Integer.compare(diff1, diff2);
  }

  public int compareRelativeGenres(Movie o1, Movie o2) {
    return Integer.compare(movie.compareTo(o1), movie.compareTo(o2));
  }

  //biased towards higher ratings
  public int compareRelativeRating(Movie o1, Movie o2) {
    double r1 = o1.getRating();
    double r2 = o2.getRating();
    double rm = movie.getRating();
    double diff1 = -1.0 * Math.abs(r1 - rm);
    double diff2 = -1.0 * Math.abs(r2 - rm);
    if (higherRateBias && diff1 == diff2) {
      return Double.compare(r1, r2);
    }
    return Double.compare(diff1, diff2);
  }

}
