package edu.brown.cs.jkst.movies;

import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.jkst.graphdata.Edge;
import edu.brown.cs.jkst.graphdata.Node;

/**
 * class describing a film node, implements our node interface.
 */
public class Movie implements Node {

  private String id;
  private String filmName;
  private HashSet<Edge> edges;

  @Override
  public String getNodeId() {
    return this.id;
  }

  @Override
  public Edge getEdge(Node n) {
    if (this.edges.contains(n)) {
      return this.edges.get(n);
    }
  }

  @Override
  public Set getEdges() {
    // TODO Auto-generated method stub
    return null;
  }

  // TODO
}
