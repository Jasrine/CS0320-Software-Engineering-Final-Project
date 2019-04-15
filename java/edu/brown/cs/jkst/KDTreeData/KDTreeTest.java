package edu.brown.cs.kpal1sa38.KDTreeData;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to test methods in KDTree class.
 */
public class KDTreeTest {

  /**
   * Test k-Nearest neighbors method when the data set is empty.
   */
  @Test
  public void testNearestNeighborsEmptyNode() {
    ArrayList<Star> actualData = new ArrayList<>();
    KDNode<Star> test1node = new KDNode<>(null, null, null, 0);
    KDNode<Star> buildData = test1node.buildKDTree(actualData,0,2);
    KDTree<Star> test1tree = new KDTree<>();
    List<Star> nodes = new ArrayList<>();
    double[] cord = {0.0,0.0,0.0};
    Star sol = new Star(0, "Sol", cord);
    List<Star> nodesActual = new ArrayList<>();
    test1tree.nearestNeighbors(buildData, sol, nodes,
            0 , 2, 5, true);
    List<Integer> nodesActualID= new ArrayList<>();
    for (Star n : nodesActual) {
      nodesActualID.add(n.getStarID());
    }
    List<Integer> nodesExpectedID = new ArrayList<>();
    assertEquals(nodesExpectedID, nodesActualID);
  }
  /**
   * Test k-Nearest neighbors method when the data set is not empty.
   */
  @Test
  public void testNearestNeighborsNonEmptyNode() {
    StarParser test1 = new StarParser();
    ArrayList<Star> actualData = test1.addData("data/stars/ten-star.csv");
    KDNode<Star> test1node = new KDNode<>(null, null, null, 0);
    KDNode<Star> buildData = test1node.buildKDTree(actualData,0,2);
    KDTree<Star> test1tree = new KDTree<>();
    List<Star> nodes = new ArrayList<>();
    double[] cord = {0.0,0.0,0.0};
    Star sol = new Star(0, "Sol", cord);
    test1tree.nearestNeighbors(buildData, sol, nodes,
            0 , 2, 5, true);
    List<Integer> nodesActualID= new ArrayList<>();
    for (Star n : nodes) {
      nodesActualID.add(n.getStarID());
    }
    List<Integer> nodesExpectedID = new ArrayList<>();
    nodesExpectedID.add(70667);
    nodesExpectedID.add(71454);
    nodesExpectedID.add(71457);
    nodesExpectedID.add(87666);
    nodesExpectedID.add(118721);
    for (int i = 0; i < nodesExpectedID.size(); i++) {
      int expected = nodesExpectedID.get(i);
      int actual = nodesActualID.get(i);
      assertEquals(expected,actual);
    }
  }
  /**
   * Test k-Nearest neighbors method when the size wanted is 0.
   */
  @Test
  public void testNearestNeighborsSizeZero() {
    StarParser test1 = new StarParser();
    ArrayList<Star> actualData = test1.addData("data/stars/ten-star.csv");
    KDNode<Star> test1node = new KDNode<>(null, null, null, 0);
    KDNode<Star> buildData = test1node.buildKDTree(actualData,0,2);
    KDTree<Star> test1tree = new KDTree<>();
    List<Star> nodes = new ArrayList<>();
    double[] cord = {0.0,0.0,0.0};
    Star sol = new Star(0, "Sol", cord);
    test1tree.nearestNeighbors(buildData, sol, nodes,
            0 , 2, 0, true);
    List<Integer> nodesActualID= new ArrayList<>();
    for (Star n : nodes) {
      nodesActualID.add(n.getStarID());
    }
    List<Integer> nodesExpectedID = new ArrayList<>();
    assertEquals(nodesExpectedID,nodesActualID);

  }
  /**
   * Test k-Nearest neighbors method through name of KDTreeObject
   * implemented node.
   */
  @Test
  public void testNearestNeighborsByName() {
    StarParser test1 = new StarParser();
    ArrayList<Star> actualData = test1.addData("data/stars/one-star.csv");
    KDNode<Star> test1node = new KDNode<>(null, null, null, 0);
    KDNode<Star> buildData = test1node.buildKDTree(actualData,0,2);
    KDTree<Star> test1tree = new KDTree<>();
    List<Star> nodes = new ArrayList<>();
    double[] cord = {0.0,0.0,0.0};
    Star sol = new Star(0, "Sol", cord);
    test1tree.nearestNeighbors(buildData, sol, nodes,
            0 , 2, 5, true);
    List<Integer> nodesActualID= new ArrayList<>();
    for (Star n : nodes) {
      nodesActualID.add(n.getStarID());
    }
    List<Integer> nodesExpectedID = new ArrayList<>();
    nodesExpectedID.add(1);
    assertEquals(nodesExpectedID,nodesActualID);
  }
  /**
   * Test k-Nearest neighbors method is asked through Coordinates.
   * Done by creating a phony star and setting the boolean byName to false.
   */
  @Test
  public void testNearestNeighborsByCoordinates() {
    StarParser test1 = new StarParser();
    ArrayList<Star> actualData = test1.addData("data/stars/ten-star.csv");
    KDNode<Star> test1node = new KDNode<>(null, null, null, 0);
    KDNode<Star> buildData = test1node.buildKDTree(actualData,0,2);
    KDTree<Star> test1tree = new KDTree<>();
    List<Star> nodes = new ArrayList<>();
    double[] cord = {0.0,0.0,0.0};
    Star sol = new Star(0, "Sol", cord);
    test1tree.nearestNeighbors(buildData, sol, nodes,
            0 , 2, 5, false);
    List<Integer> nodesActualID= new ArrayList<>();
    for (Star n : nodes) {
      nodesActualID.add(n.getStarID());
    }
    List<Integer> nodesExpectedID = new ArrayList<>();
    nodesExpectedID.add(0);
    nodesExpectedID.add(70667);
    nodesExpectedID.add(71454);
    nodesExpectedID.add(71457);
    nodesExpectedID.add(87666);
    for (int i = 0; i < nodesExpectedID.size(); i++) {
      int expected = nodesExpectedID.get(i);
      int actual = nodesActualID.get(i);
      assertEquals(expected,actual);
    }
  }

  /**
   * Test radius search method when the data set is empty.
   */
  @Test
  public void testRadiusSearchEmptyNode() {
    ArrayList<Star> actualData = new ArrayList<>();
    KDNode<Star> test1node = new KDNode<>(null, null, null, 0);
    KDNode<Star> buildData = test1node.buildKDTree(actualData,0,2);
    KDTree<Star> test1tree = new KDTree<>();
    List<Star> nodes = new ArrayList<>();
    double[] cord = {0.0,0.0,0.0};
    Star sol = new Star(0, "Sol", cord);
    test1tree.radiusSearchsorted(buildData, sol, nodes,
            0 , 2, 5, true);
    List<Integer> nodesActualID= new ArrayList<>();
    for (Star n : nodes) {
      nodesActualID.add(n.getStarID());
    }
    List<Integer> nodesExpectedID = new ArrayList<>();
    assertEquals(nodesExpectedID, nodesActualID);
  }
  /**
   * Test radius search method when the data set is not empty.
   */
  @Test
  public void testRadiusSearchNonEmptyNode() {
    StarParser test1 = new StarParser();
    ArrayList<Star> actualData = test1.addData("data/stars/ten-star.csv");
    KDNode<Star> test1node = new KDNode<>(null, null, null, 0);
    KDNode<Star> buildData = test1node.buildKDTree(actualData,0,2);
    KDTree<Star> test1tree = new KDTree<>();
    List<Star> nodes = new ArrayList<>();
    double[] cord = {0.0,0.0,0.0};
    Star sol = new Star(0, "Sol", cord);
    test1tree.radiusSearchsorted(buildData, sol, nodes,
            0 , 2, 10, true);
    List<Integer> nodesActualID= new ArrayList<>();
    for (Star n : nodes) {
      nodesActualID.add(n.getStarID());
    }
    List<Integer> nodesExpectedID = new ArrayList<>();
    nodesExpectedID.add(0);
    nodesExpectedID.add(70667);
    nodesExpectedID.add(71454);
    nodesExpectedID.add(71457);
    nodesExpectedID.add(87666);
    nodesExpectedID.add(118721);
    nodesExpectedID.add(3759);
    for (int i = 0; i < nodesExpectedID.size(); i++) {
      int expected = nodesExpectedID.get(i);
      int actual = nodesActualID.get(i);
      assertEquals(expected,actual);
    }
  }
  /**
   * Test k-Nearest neighbors method when the radius size is 0.
   */
  @Test
  public void testRadiusSearchRadiusZero() {
    StarParser test1 = new StarParser();
    ArrayList<Star> actualData = test1.addData("data/stars/ten-star.csv");
    KDNode<Star> test1node = new KDNode<>(null, null, null, 0);
    KDNode<Star> buildData = test1node.buildKDTree(actualData,0,2);
    KDTree<Star> test1tree = new KDTree<>();
    List<Star> nodes = new ArrayList<>();
    double[] cord = {0.0,0.0,0.0};
    Star sol = new Star(0, "Sol", cord);
    test1tree.radiusSearchsorted(buildData, sol, nodes,
            0 , 2, 0, true);
    List<Integer> nodesActualID= new ArrayList<>();
    for (Star n : nodes) {
      nodesActualID.add(n.getStarID());
    }
    List<Integer> nodesExpectedID = new ArrayList<>();
    nodesExpectedID.add(0);
    assertEquals(nodesExpectedID,nodesActualID);
  }
  /**
   * Test radius search method is asked through name of
   * KDTreeObject implemented object.
   */
  @Test
  public void testRadiusSearchByName() {
    StarParser test1 = new StarParser();
    ArrayList<Star> actualData = test1.addData("data/stars/one-star.csv");
    KDNode<Star> test1node = new KDNode<>(null, null, null, 0);
    KDNode<Star> buildData = test1node.buildKDTree(actualData,0,2);
    KDTree<Star> test1tree = new KDTree<>();
    List<Star> nodes = new ArrayList<>();
    double[] cord = {0.0,0.0,0.0};
    Star sol = new Star(0, "Sol", cord);
    test1tree.radiusSearchsorted(buildData, sol, nodes,
            0 , 2, 1, true);
    List<Integer> nodesActualID= new ArrayList<>();
    for (Star n : nodes) {
      nodesActualID.add(n.getStarID());
    }
    List<Integer> nodesExpectedID = new ArrayList<>();
    assertEquals(nodesExpectedID,nodesActualID);
  }
  /**
   * Test radius search method is asked through coordinates of
   * KDTreeObject implemented object.
   * Done by creating a phony KDTreeObject and setting
   * byName parameter to False.
   */
  @Test
  public void testRadiusSearchByCoordinates() {
    StarParser test1 = new StarParser();
    ArrayList<Star> actualData = test1.addData("data/stars/ten-star.csv");
    KDNode<Star> test1node = new KDNode<>(null, null, null, 0);
    KDNode<Star> buildData = test1node.buildKDTree(actualData,0,2);
    KDTree<Star> test1tree = new KDTree<>();
    List<Star> nodes = new ArrayList<>();
    double[] cord = {0.0,0.0,0.0};
    Star sol = new Star(0, "Sol", cord);
    test1tree.radiusSearchsorted(buildData, sol, nodes,
            0 , 2, 10, false);
    List<Integer> nodesActualID= new ArrayList<>();
    for (Star n : nodes) {
      nodesActualID.add(n.getStarID());
    }
    List<Integer> nodesExpectedID = new ArrayList<>();
    nodesExpectedID.add(0);
    nodesExpectedID.add(70667);
    nodesExpectedID.add(71454);
    nodesExpectedID.add(71457);
    nodesExpectedID.add(87666);
    nodesExpectedID.add(118721);
    nodesExpectedID.add(3759);
    for (int i = 0; i < nodesExpectedID.size(); i++) {
      int expected = nodesExpectedID.get(i);
      int actual = nodesActualID.get(i);
      assertEquals(expected,actual);
    }

    StarParser test2 = new StarParser();
    ArrayList<Star> actualData2 = test2.addData("data/stars/stardata.csv");
    KDNode<Star> test2node = new KDNode<>(null, null, null, 0);
    KDNode<Star> buildData2 = test2node.buildKDTree(actualData2,0,2);
    KDTree<Star> test1tree2 = new KDTree<>();
    List<Star> nodes2 = new ArrayList<>();
    double[] cord2 = {0.0,0.0,0.0};
    Star sol2 = new Star(0, "Sol", cord2);
    test1tree2.radiusSearchsorted(buildData2, sol2, nodes2,
            0 , 2, 2, false);
    for (Star n : nodes2) {
      System.out.println("HEREE : " + n.getStarID());
    }

  }


}
