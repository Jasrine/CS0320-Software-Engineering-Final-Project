package edu.brown.cs.kpal1sa38.graphdata;

import static junit.framework.TestCase.assertEquals;

import java.util.List;

import org.junit.Test;

/**
 * tests for graphs.
 */
public class GraphTest {

  /**
   * method which tests the findShortestPath method.
   */
  @Test
  public void testFindShortestPath() {
    double[] cord1 = {
        1, 1
    };
    double[] cord2 = {
        2, 2
    };
    double[] cord3 = {
        3, 3
    };
    double[] cord4 = {
        4, 4
    };
    PhonyNode node1 = new PhonyNode("1", "1", cord1);
    PhonyNode node2 = new PhonyNode("2", "2", cord2);
    PhonyNode node3 = new PhonyNode("3", "3", cord3);
    PhonyNode node4 = new PhonyNode("4", "4", cord4);
    PhonyEdge node1Neighbor1 = new PhonyEdge(node1, node2, "5", 5.0);
    node1.addNeighbor(node1Neighbor1);
    PhonyEdge node2Neighbor1 = new PhonyEdge(node2, node1, "5", 5.0);
    node2.addNeighbor(node2Neighbor1);
    PhonyEdge node1Neighbor2 = new PhonyEdge(node1, node3, "15", 15.0);
    node1.addNeighbor(node1Neighbor2);
    PhonyEdge node2Neighbor2 = new PhonyEdge(node2, node3, "6", 6.0);
    node2.addNeighbor(node2Neighbor2);
    PhonyEdge node3Neighbor1 = new PhonyEdge(node3, node2, "6", 6.0);
    node3.addNeighbor(node3Neighbor1);
    PhonyEdge node3Neighbor2 = new PhonyEdge(node3, node1, "15", 15.0);
    node3.addNeighbor(node3Neighbor2);
    PhonyEdge node3Neighbor3 = new PhonyEdge(node3, node4, "2", 2.0);
    node3.addNeighbor(node3Neighbor3);
    Graph<PhonyNode, PhonyEdge> graphTester = new Graph<>();
    List<PhonyEdge> edgesTest1 = graphTester.findShortestPath(node1, node2);
    assertEquals(1, edgesTest1.size());
    assertEquals(node1Neighbor1, edgesTest1.get(0));
  }
}
