package edu.brown.cs.jkst.graphdata;

/**
 * a public interface that defines general edges accompanying our nodes.
 *
 * @param <N>
 *          type of node.
 * @param <E>
 *          type of edge.
 */
public interface Edge<N extends Node<N, E>, E extends Edge<N, E>> {

  /**
   * getter for the source of the edge.
   *
   * @return source node of edge.
   */
  N getSrc();

  /**
   * getter for the destination node to which the egde points.
   *
   * @return destination node of edge.
   */
  N getDest();

  /**
   * getter for the weight of the edge.
   *
   * @return weight of the edge.
   */
  Double getWeight();
}
