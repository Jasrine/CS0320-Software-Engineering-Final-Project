package edu.brown.cs.jkst.graphdata;

import java.util.Set;

/**
 * interface that describes how our Nodes generally look.
 *
 * @param <N>
 *          type of Node in the graph.
 * @param <E>
 *          type of Edge connecting Nodes.
 */
public interface Node<N extends Node<N, E>, E extends Edge<N, E>> {

  /**
   * getter for the node id.
   *
   * @return String corresponding to node's id.
   */
  String getNodeId();

  /**
   * gets an edge from this node to a specified node if it exists.
   *
   * @param n
   *          Node which might be a destination node with this node as source.
   *
   * @return edge if it exists.
   */
  E getEdge(Node<N, E> n);

  /**
   * gets the set of edges with this node as source.
   *
   * @return set of edges with this node as the starting point.
   */
  Set<E> getEdges();
}
