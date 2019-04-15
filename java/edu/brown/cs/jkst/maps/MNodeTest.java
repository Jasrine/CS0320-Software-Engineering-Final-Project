package edu.brown.cs.kpal1sa38.maps;

import edu.brown.cs.kpal1sa38.KDTreeData.KDTreeObject;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * This is a public class that test methods in MNode class. Those that
 * depend on database are system tested.
 */
public class MNodeTest {
  @Test
  public void testIsTraversable() {
    double[] coordinates = {0.1, 0.1};
    MNode node1 = new MNode("1",coordinates, true);
    assertTrue(node1.isTraversable());
    MNode node2 = new MNode("2",coordinates, false);
    assertFalse(node2.isTraversable());
  }

  @Test
  public void testGetDistance() {
    double[] coordinates = {0.1, 0.1};
    double[] coordinates2 = {0.5, 0.5};
    MNode node1 = new MNode("1",coordinates, true);
    MNode node2 = new MNode("2",coordinates2, true);
    assertEquals(62.9, node1.getDist(node2), 0.1);
  }

  @Test
  public void testGetAxisDistance() {
    double[] coordinates = {0.1, 0.1};
    double[] coordinates2 = {0.5, 0.5};
    MNode node1 = new MNode("1",coordinates, true);
    MNode node2 = new MNode("2",coordinates2, true);
    assertEquals(0.4, node1.getAxisDistance(node2, 0), 0.1);
  }
  
  @Test
  public void testGetCoordinates() {
    double[] coordinates = {0.1, 0.2};
    MNode node1 = new MNode("1",coordinates, true);
    assertEquals(0.1, node1.getCoordinates(0), 0.0);
    assertEquals(0.2,node1.getCoordinates(1), 0.0);
  }
  
  @Test
  public void testGetNodeID () {
      double[] coordinates = {0.1, 0.2};
      MNode node1 = new MNode("1",coordinates, true);
      assertEquals(0.1, node1.getCoordinates(0), 0.0);
      assertEquals("1",node1.getNodeID());
  }
}
