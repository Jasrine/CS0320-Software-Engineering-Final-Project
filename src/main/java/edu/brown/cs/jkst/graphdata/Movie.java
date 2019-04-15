package edu.brown.cs.jkst.graphdata;

import java.util.HashSet;
import java.util.Set;

/**
 * class describing a film node, implements our node interface.
 */
public class Movie implements Node<Movie, MEdge> {

  private String id;
  private String filmName;
  private HashSet<MEdge> edges;

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
}
