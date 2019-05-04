package edu.brown.cs.jkst.graphdata;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * class describing a film node, implements our node interface.
 */
public class Movie implements Node<Movie, MEdge> {

  private String id;
  private String filmName;
  private String director;
  private int year;
  private List<String> genres;
  private List<String> crew;
  private List<String> regions;
  private Set<MEdge> edges;
  private double rating;
  private int numVotes;

  /**
   * Constructor for Movie node.
   *
   * @param id
   *          String id for uniquely identifying a movie.
   * @param filmName
   *          String name for displaying the film.
   * @param director
   *          String id for director.
   * @param year
   *          number specifying the year in which it premiered.
   * @param genres
   *          List of String where each String is a genre.
   * @param regions
   *          List of regions in which the film is available.
   * @param rating
   *          double between 0 and 10 giving the film's IMDB rating.
   * @param numVotes
   *          int number of votes contributing to the rating.
   */
  public Movie(String id, String filmName, String director,
      int year, List<String> genres, List<String> regions,
      double rating, int numVotes) {
    this.id = id;
    this.filmName = filmName;
    this.director = director;
    this.year = year;
    this.genres = genres;
    this.regions = regions;
    this.rating = rating;
    this.numVotes = numVotes;
  }

  @Override
  public String getNodeId() {
    return this.id;
  }

  @Override
  public MEdge getEdge(Node<Movie, MEdge> n) {
    for (MEdge e : this.edges) {
      if (e.getDest().equals(n)) {
        return e;
      }
    }

    return null;
  }

  @Override
  public Set<MEdge> getEdges() {
    return this.edges;
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
   * getter for genres.
   *
   * @return list of strings indicating genre.
   */
  public List<String> getGenres() {
    return this.genres;
  }

  /**
   * getter for crew members.
   *
   * @return list of strings indicating names of crew members.
   */
  public List<String> getCrew() {
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

  public Set<Movie> suggest(Set<Movie> movies) {
    Set<Movie> suggestions = new TreeSet<>(Comparator.comparingDouble(
        this::scoreSimilarity));
    suggestions.addAll(movies);
    return suggestions;
  }

  /**
   * Very simple way to decide the following: how similar are these movies?
   * 
   * @return a score indicating how similar the two movies are predicted to be.
   *         Higher score indicates more similarities. Factors considered
   *         include director, genre(s) according to IMDB, rating according to
   *         IMDB, ...
   */
  public double scoreSimilarity(Movie that) {
    double directorScore = 0.0;
    if (this.director.equals(that.director)) {
      directorScore = 1.0;
    }

    // TODO: consider averaging the genre similarity in both directions
    // TODO: consider accounting for the varying tones in the actual genres
    double genreScore = 0.0;
    String AA = this.genres.get(0);
    String aa = that.genres.get(0);
    String BB = this.genres.get(1);
    String bb = that.genres.get(1);
    String CC = this.genres.get(2);
    String cc = that.genres.get(2);
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
    genreScore *= 0.01;

    // The smaller the difference in rating, the more similar the movies are
    // said
    // to be. This could be modified so that if the OTHER movie is rated higher
    // the "similarity" is inflated, making the "better" movie a more appealing
    // suggestion.
    double ratingScore = 1.0 - (Math.abs(this.rating - that.rating) * 0.1);
    // TODO: is the rating more or less meaningful depending on numVotes?

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
    StringBuilder sb = new StringBuilder();
    sb.append("Film name: " + this.filmName + "\n");
    sb.append("Director: " + this.director + "\n");
    sb.append("Year: " + this.year + "\n");
    sb.append("Genres: " + this.genres.toString() + "\n");
    sb.append("Regions: " + this.regions.toString() + "\n");
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
}
