package edu.brown.cs.jkst.graphdata;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * class describing a film node, implements our node interface.
 */
public class Movie implements Comparable<Movie> {
  private static final int DECADE = 10;
  private static final int EARLIEST_YEAR = 1890;
  private static final double NORMALIZE_INTERNAL_RATING = 100.f;

  private String id;
  private String filmName;
  private String director;
  private String img;
  private int year;
  private List<String> genres;
  private Map<String, String> crew;
  private List<String> cast;
  private List<String> regions;
  private double rating;
  private int numVotes;
  private double rawRanking;

  /**
   * Constructor for Movie node.
   *
   * @param id
   *          String id for uniquely identifying a movie.
   * @param filmName
   *          String name for displaying the film.
   * @param year
   *          number specifying the year in which it premiered.
   * @param genres
   *          List of String where each String is a genre.
   * @param regions
   *          List of regions in which the film is available.
   * @param rating
   *          Rating of the movie
   * @param numVotes
   *          Number of people who contributed to the rating
   */
  public Movie(String id, String filmName, int year, List<String> genres,
      List<String> regions, double rating, int numVotes) {
    this.id = id;
    this.filmName = filmName;
    this.director = "";
    this.img = "";
    this.year = year;
    this.genres = genres;
    this.regions = regions;
    this.rating = rating;
    this.numVotes = numVotes;
    this.cast = new LinkedList<String>();
    this.rawRanking = this.rawRank();
  }

  /**
   * getter for this film's name.
   *
   * @return film name.
   */
  public String getFilmname() {
    return this.filmName;
  }

  /**
   * getter for the decade in which this film premiered.
   *
   * @return year of film premiere.
   */
  public String getDecade() {
    int decStart = this.year - (this.year % DECADE);
    return decStart + "s";
  }

  /**
   * setter for cast.
   *
   * @param castMembers
   *          array of strings with cast member names.
   */
  public void setCast(String[] castMembers) {
    for (String castMember : castMembers) {
      this.cast.add(castMember);
    }
  }

  /**
   * gets the node id.
   *
   * @return the node id.
   */
  public String getNodeId() {
    return this.id;
  }

  /**
   * sets rating to a given value.
   *
   * @param rating
   *          to set the movie rating to.
   */
  public void setRating(double rating) {
    this.rating = rating;
  }

  /**
   * sets rating to a given value.
   *
   * @param numvotes
   *          to set the movie rating to.
   */
  public void setVotes(int numvotes) {
    this.numVotes = numvotes;
  }

  /**
   * setter for the image url.
   *
   * @param imgURL
   *          url to set for this movie class if any.
   */
  public void setImgURL(String imgURL) {
    this.img = imgURL;
  }

  /**
   * getter for director.
   *
   * @return String of director's id.
   */
  public String getDirector() {
    return this.director;
  }

  /**
   * setter for director.
   *
   * @param director
   *          String of director's name.
   */
  public void setDirector(String director) {
    this.director = director;
  }

  /**
   * getter for genres.
   *
   * @return list of strings indicating genre.
   */
  public List<String> getGenres() {
    return this.genres;
  }

  /**
   * setter for crew.
   *
   * @param crew
   *          list of strings representing the crew.
   */
  public void setCrew(Map<String, String> crew) {
    if (crew != null) {
      this.director = crew.getOrDefault("director", "");
      this.crew = crew;
    }
  }

  /**
   * getter for crew members.
   *
   * @return list of strings indicating names of crew members.
   */
  public Map<String, String> getCrew() {
    return this.crew;
  }

  /**
   * getter for regions in which the film is available.
   *
   * @return list of strings indicating regions in which the film is available.
   */
  public List<String> getRegions() {
    return this.regions;
  }

  /**
   * getter for film's rating.
   *
   * @return film's current rating.
   */
  public double getRating() {
    return this.rating;
  }

  /**
   * getter for number of votes on film.
   *
   * @return film's current number of votes.
   */
  public int getNumVotes() {
    return this.numVotes;
  }

  /**
   * raw ranking for searches that are NOT by similarity.
   *
   * @return double that indicates the raw rank.
   */
  public double rawRank() {
    double dataCompleteness = 0.f; // Number of nonempty fields
    double yearScore = 0.f;
    double ratingScore = 0.f;
    if (this.filmName != null) {
      dataCompleteness++;
    }
    if ((this.director != null) && !this.director.equals("")) {
      dataCompleteness++;
    }
    if (this.year != 0) {
      dataCompleteness++;
      yearScore = this.year - EARLIEST_YEAR;
    }
    if (this.genres != null && !this.genres.isEmpty()) {
      dataCompleteness++;
    }
    if (this.crew != null && !this.crew.isEmpty()) {
      dataCompleteness++;
    }
    if (this.regions != null && !this.regions.isEmpty()) {
      dataCompleteness++;
    }
    if (this.numVotes != 0) {
      dataCompleteness++;
    }
    if (this.rating != 0.0) {
      ratingScore = this.rating * (this.numVotes / NORMALIZE_INTERNAL_RATING);
      dataCompleteness++;
    }
    // The oldest movie on IMDB is from 1874, so subtracting 1870 from the date
    // of release gives a normalized score that's higher the more current the
    // movie is.
    // double awardsWon = 0.f;
    // won
    double compWeight = 1.f;
    double yearWeight = 1.f;
    double rateWeight = 1.5f;
    dataCompleteness *= compWeight;
    yearScore *= yearWeight;
    ratingScore *= rateWeight;
    return dataCompleteness * Math.sqrt(yearScore * yearScore
        + ratingScore * ratingScore);
  }

  /**
   * getter for the image url.
   *
   * @return String that contains a link to the image if it exists.
   */
  public String getImageURL() {
    return this.img;
  }

  // public double searchRelevancy(String title, String decade, String region,
  // String genres) {
  // if (this.)
  // //1. measure title similarity
  // //2. measure release date similarity
  // //3. measure region similarity
  // //4. measure genre similarity
  // todo comparator extending class!!!
  // }

  /**
   * @param movies
   *          a Set of Movies to compare this Movie against.
   * @return a TreeSet of the same Movies reordered based on similarity to this
   *         movie.
   */
  public Set<Movie> suggest(Set<Movie> movies) {
    Set<Movie> suggestions = new TreeSet<>(
        Comparator.comparingDouble(this::scoreSimilarity));
    suggestions.addAll(movies);
    return suggestions;
  }

  /**
   * Given a Movie, returns a number scoring how relatively similar that Movie
   * is to this Movie.
   *
   * @param m
   *          a Movie that is potentially similar to this Movie.
   * @return a score indicating how similar the two movies are predicted to be.
   *         Higher score indicates more similarities. Factors considered
   *         include director, genre(s) according to IMDB, rating according to
   *         IMDB, ...
   */
  public double scoreSimilarity(Movie m) {
    double directorScore = 0.0;
    if (this.director.equals(m.director)) {
      directorScore = 1.0;
    }

    // consider averaging the genre similarity in both directions
    // consider accounting for the varying tones in the actual genres
    double genreScore = 0.0;
    String AA = this.genres.get(0);
    String aa = m.genres.get(0);
    String BB = this.genres.get(1);
    String bb = m.genres.get(1);
    String CC = this.genres.get(2);
    String cc = m.genres.get(2);
    boolean isOverlap1 = true;
    if (aa.equals(AA)) {
      genreScore += 39.0;
    } else if (aa.equals(BB)) {
      genreScore += 36.0;
    } else if (aa.equals(CC)) {
      genreScore += 33.0;
    } else {
      isOverlap1 = false;
    }
    boolean isOverlap2 = true;
    if (bb.equals(AA)) {
      genreScore += 26.0;
    } else if (bb.equals(BB)) {
      genreScore += 24.0;
    } else if (bb.equals(CC)) {
      genreScore += 22.0;
    } else {
      isOverlap2 = false;
    }
    boolean isOverlap3 = true;
    if (cc.equals(AA)) {
      genreScore += 13.0;
    } else if (cc.equals(BB)) {
      genreScore += 12.0;
    } else if (cc.equals(CC)) {
      genreScore += 11.0;
    } else {
      isOverlap3 = false;
    }
    if (isOverlap1 || isOverlap2 || isOverlap3) {
      // adjust it so it's a scale of 0-100 (but first nonzero is 26)
      genreScore += 26.0;
    }
    genreScore /= NORMALIZE_INTERNAL_RATING;

    // The smaller the difference in rating, the more similar the movies are
    // said
    // to be. This could be modified so that if the OTHER movie is rated higher
    // the "similarity" is inflated, making the "better" movie a more appealing
    // suggestion.
    double ratingScore = 1.0 - (Math.abs(this.rating - m.rating) * 0.1);
    // TODO: normalize ratings by genre?

    // TODO: crewScore (similar to genre score but with no weight on order?)
    // TODO: regionScore (positive or negative depending on preference?)
    // TODO: titleScore (? [still not sure that this is an important metric])
    // TODO: awardScore (?? [dependent on ability to get more data])

    // TODO: all parts of the score should be toggleable based on search
    // settings

    return directorScore + genreScore + ratingScore;
  }

  @Override
  public String toString() {
    StringBuilder regionSb = new StringBuilder();
    for (String region : this.regions) {
      regionSb.append(region + ", ");
    }
    String regionStr = regionSb.substring(0, regionSb.length() - 2);

    StringBuilder sb = new StringBuilder();
    sb.append("Film name: " + this.filmName + "\n");
    sb.append("Director: " + this.director + "\n");
    sb.append("Year: " + this.year + "\n");
    sb.append("Genres: " + this.genres.toString() + "\n");
    sb.append("Regions: " + regionStr + "\n");
    return sb.toString();
  }

  @Override
  public int hashCode() {
    return this.id.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    Movie con = (Movie) o;
    return this.id.equals(con.getNodeId());
  }

  /**
   * Compares this object with the specified object for order. Returns a
   * negative integer, zero, or a positive integer as this object is less than,
   * equal to, or greater than the specified object.
   * <p>
   * <p>
   * The implementor must ensure <tt>sgn(x.compareTo(y)) ==
   * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>. (This implies
   * that <tt>x.compareTo(y)</tt> must throw an exception iff
   * <tt>y.compareTo(x)</tt> throws an exception.)
   * <p>
   * <p>
   * The implementor must also ensure that the relation is transitive:
   * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
   * <tt>x.compareTo(z)&gt;0</tt>.
   * <p>
   * <p>
   * Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
   * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for all
   * <tt>z</tt>.
   * <p>
   * <p>
   * It is strongly recommended, but <i>not</i> strictly required that
   * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>. Generally speaking, any
   * class that implements the <tt>Comparable</tt> interface and violates this
   * condition should clearly indicate this fact. The recommended language is
   * "Note: this class has a natural ordering that is inconsistent with equals."
   * <p>
   * <p>
   * In the foregoing description, the notation
   * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
   * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
   * <tt>0</tt>, or <tt>1</tt> according to whether the value of
   * <i>expression</i> is negative, zero or positive.
   *
   * @param o
   *          the object to be compared.
   * @return a negative integer, zero, or a positive integer as this object is
   *         less than, equal to, or greater than the specified object.
   * @throws NullPointerException
   *           if the specified object is null
   * @throws ClassCastException
   *           if the specified object's type prevents it from being compared to
   *           this object.
   */
  @Override
  public int compareTo(Movie o) {
    return Double.compare(this.rawRanking, o.rawRanking);
  }
}
