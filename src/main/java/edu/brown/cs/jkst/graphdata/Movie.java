package edu.brown.cs.jkst.graphdata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * class describing a film node, implements our node interface.
 */
public class Movie implements Node<Movie, MEdge> {

  private String id;
  private String filmName;
  private String director;
  private List<String> genres;
  private List<String> crew;
  private List<String> regions;
  private HashSet<MEdge> edges;

  /**
   * Constructor for Movie node.
   *
   * @param id
   *          String id for uniquely identifying a movie.
   * @param filmName
   *          String name for displaying the film.
   * @param director
   *          String id for director.
   * @param genres
   *          List of String where each String is a genre.
   * @param regions
   *          List of regions in which the film is available.
   */
  public Movie(String id, String filmName, String director,
      List<String> genres, List<String> regions) {
    this.id = id;
    this.filmName = filmName;
    this.director = director;
    this.genres = genres;
    this.regions = regions;
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

  @Override
  public String toString() {
    return this.filmName;
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
}
