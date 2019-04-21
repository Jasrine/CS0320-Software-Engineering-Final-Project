package edu.brown.cs.jkst.graphdata;

/**
 * class that describes edges connecting different movie nodes.
 */
public class MEdge implements Edge<Movie, MEdge> {

  private String id;
  private Movie src;
  private Movie dest;
  private double weight;

  @Override
  public Movie getSrc() {
    return this.src;
  }

  @Override
  public Movie getDest() {
    return this.dest;
  }

  @Override
  public Double getWeight() {
    return this.weight;
  }

  /**
   * getter for the id of the edge.
   *
   * @return String id that uniquely identifies the edge.
   */
  public String getId() {
    return this.id;
  }

  @Override
  public String toString() {
    return this.src.toString() + " -> " + this.dest.toString() + ": "
        + this.weight;
  }
}
