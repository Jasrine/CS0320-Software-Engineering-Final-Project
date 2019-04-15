package edu.brown.cs.kpal1sa38.KDTreeData;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Class to test methods in KDNode class.
 */
public class KDNodeTest {

  /**
   * Test whether the KDNode tree is being created correctly. (Size 1)
   */
  @Test
  public void testBuildKDTreeOne() {
    StarParser test1 = new StarParser();
    ArrayList<Star> data = test1.addData("data/stars/one-star.csv");
    KDNode<Star> test1tree = new KDNode<>(null, null, null, 0);
    KDNode<Star> result = test1tree.buildKDTree(data,0,2);
    double[] expectedCoordinate = {5,-2.24,10.04};
    KDNode<Star> expectedResult = new KDNode<>(new Star(1,
            "Lonely Star", expectedCoordinate),
            null, null, 0);
    assertEquals(expectedResult.getRoot().getStarID(),
            result.getRoot().getStarID());
    assertEquals(expectedResult.getRoot().getProperName(),
            result.getRoot().getProperName());
    assertEquals(expectedResult.getLeft(),result.getLeft());
    assertEquals(expectedResult.getRight(),result.getRight());
    assertEquals(expectedResult.getDimensions(),result.getDimensions());
  }
  /**
   * Test whether the KDNode tree is being created correctly. (Size more than 1)
   */
  @Test
  public void testBuildKDTreeThree() {
    StarParser test1 = new StarParser();
    ArrayList<Star> data = test1.addData("data/stars/three-star.csv");
    KDNode<Star> test1tree = new KDNode<>(null, null, null, 0);
    KDNode<Star> result = test1tree.buildKDTree(data,0,2);
    double[] expectedCoordinate = {0,0,0};
    double[] expectedCoordinateLeft = {-1,-1,-1};
    double[] expectedCoordinateRight = {1,1,1};
    KDNode<Star> expectedResult = new KDNode<>(new Star(0,
            "Sol", expectedCoordinate),
            new KDNode<>(new Star(3,"Left",expectedCoordinateLeft),
                    null, null, 1),
            new KDNode<>(new Star(2, "Right", expectedCoordinateRight),
            null, null, 1),  0);
    //Root Node
    assertEquals(expectedResult.getRoot().getStarID(),
            result.getRoot().getStarID());
    assertEquals(expectedResult.getRoot().getProperName(),
            result.getRoot().getProperName());
    assertEquals(expectedResult.getDimensions(),result.getDimensions());
    // Left SubTree
    assertEquals(expectedResult.getLeft().getRoot().getStarID(),
            result.getLeft().getRoot().getStarID());
    assertEquals(expectedResult.getLeft().getRoot().getProperName(),
            result.getLeft().getRoot().getProperName());
    assertEquals(expectedResult.getLeft().getLeft(),
            result.getLeft().getLeft());
    assertEquals(expectedResult.getLeft().getRight(),
            result.getLeft().getRight());
    assertEquals(expectedResult.getLeft().getDimensions(),
            result.getLeft().getDimensions());
    // Right SubTree
    assertEquals(expectedResult.getRight().getRoot().getStarID(),
            result.getRight().getRoot().getStarID());
    assertEquals(expectedResult.getRight().getRoot().getProperName(),
            result.getRight().getRoot().getProperName());
    assertEquals(expectedResult.getRight().getLeft(),
            result.getRight().getLeft());
    assertEquals(expectedResult.getRight().getRight(),
            result.getRight().getRight());
    assertEquals(expectedResult.getRight().getDimensions(),
            result.getRight().getDimensions());

  }

  /**
   * Testing getDimensions() method.
   */
  @Test
  public void testGetDimensions() {
    StarParser test1 = new StarParser();
    ArrayList<Star> data = test1.addData("data/stars/one-star.csv");
    KDNode<Star> test1tree = new KDNode<>(null, null, null, 0);
    KDNode<Star> result = test1tree.buildKDTree(data,0,2);
    double[] expectedCoordinate = {5,-2.24,10.04};
    KDNode<Star> expectedResult = new KDNode<>(new Star(1,
            "Lonely Star", expectedCoordinate),
            null, null, 0);
    assertEquals(expectedResult.getDimensions(), 0);

  }
  /**
   * Testing getRight() method.
   */
  @Test
  public void testGetRight() {
    StarParser test1 = new StarParser();
    ArrayList<Star> data = test1.addData("data/stars/one-star.csv");
    KDNode<Star> test1tree = new KDNode<>(null, null, null, 0);
    KDNode<Star> result = test1tree.buildKDTree(data,0,2);
    double[] expectedCoordinate = {5,-2.24,10.04};
    KDNode<Star> expectedResult = new KDNode<>(new Star(1,
            "Lonely Star", expectedCoordinate),
            null, null, 0);
    assertEquals(expectedResult.getRight(), null);
  }
  /**
   * Testing getLeft() method.
   */
  @Test
  public void testGetLeft() {
    StarParser test1 = new StarParser();
    ArrayList<Star> data = test1.addData("data/stars/one-star.csv");
    KDNode<Star> test1tree = new KDNode<>(null, null, null, 0);
    KDNode<Star> result = test1tree.buildKDTree(data,0,2);
    double[] expectedCoordinate = {5,-2.24,10.04};
    KDNode<Star> expectedResult = new KDNode<>(new Star(1,
            "Lonely Star", expectedCoordinate),
            null, null, 0);
    assertEquals(expectedResult.getLeft(), null);
  }
  /**
   * Testing getRoot() method.
   */
  @Test
  public void testGetRoot() {
    StarParser test1 = new StarParser();
    ArrayList<Star> data = test1.addData("data/stars/one-star.csv");
    KDNode<Star> test1tree = new KDNode<>(null, null, null, 0);
    KDNode<Star> result = test1tree.buildKDTree(data,0,2);
    double[] expectedCoordinate = {5,-2.24,10.04};
    KDNode<Star> expectedResult = new KDNode<>(new Star(1,
            "Lonely Star", expectedCoordinate),
            null, null, 0);
    assertEquals(expectedResult.getRoot().getStarID(), 1);
  }

}
