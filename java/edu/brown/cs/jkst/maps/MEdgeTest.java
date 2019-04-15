package edu.brown.cs.kpal1sa38.maps;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MEdgeTest {
  @Test 
  public void testGetStart() {
    double[] cordStart = {0.0, 0.0};
    MNode start = new MNode("11", cordStart, true);
    double[] cordEnd = {1.0, 1.0};
    MNode end = new MNode("11", cordEnd, true);
    MEdge edge1 = new MEdge("1", "1", "random", start, end);
    assertEquals(edge1.getStart(), start);
  }

  @Test
  public void testGetEnd() {
    double[] cordStart = {0.0, 0.0};
    MNode start = new MNode("11", cordStart, true);
    double[] cordEnd = {1.0, 1.0};
    MNode end = new MNode("11", cordEnd, true);
    MEdge edge1 = new MEdge("1", "1", "random", start, end);
    assertEquals(edge1.getEnd(), end);
  }

  @Test
  public void testGetEdgeName() {
    double[] cordStart = {0.0, 0.0};
    MNode start = new MNode("11", cordStart, true);
    double[] cordEnd = {1.0, 1.0};
    MNode end = new MNode("11", cordEnd, true);
    MEdge edge1 = new MEdge("1", "2", "random", start, end);
    assertEquals(edge1.getEdgeName(), "2");
  }

  @Test
  public void testGetWeight() {
    double[] cordStart = {0.0, 0.0};
    MNode start = new MNode("11", cordStart, true);
    double[] cordEnd = {1.0, 1.0};
    MNode end = new MNode("11", cordEnd, true);
    MEdge edge1 = new MEdge("1", "1", "random", start, end);
    assertEquals(157.2, edge1.getWeight(), 0.1);
  }
  
  @Test
  public void testSetWeight() {
    double[] cordStart = {0.0, 0.0};
    MNode start = new MNode("11", cordStart, true);
    double[] cordEnd = {1.0, 1.0};
    MNode end = new MNode("11", cordEnd, true);
    MEdge edge1 = new MEdge("1", "2", "random", start, end);
    edge1.setWeight(4.0);
    assertEquals(4.0, edge1.getWeight());
  }

  @Test
  public void testSetTraffic() {
    double[] cordStart = {0.0, 0.0};
    MNode start = new MNode("11", cordStart, true);
    double[] cordEnd = {1.0, 1.0};
    MNode end = new MNode("11", cordEnd, true);
    MEdge edge1 = new MEdge("1", "2", "random", start, end);
    edge1.setTraffic(3.0);
    assertEquals(3.0, edge1.getTraffic());
  }
  
  @Test
  public void testGetTraffic() {
    double[] cordStart = {0.0, 0.0};
    MNode start = new MNode("11", cordStart, true);
    double[] cordEnd = {1.0, 1.0};
    MNode end = new MNode("11", cordEnd, true);
    MEdge edge1 = new MEdge("1", "2", "random", start, end);
    assertEquals(0.0, edge1.getTraffic());
  }
  
  @Test
  public void  testGetType() {
    double[] cordStart = {0.0, 0.0};
    MNode start = new MNode("11", cordStart, true);
    double[] cordEnd = {1.0, 1.0};
    MNode end = new MNode("11", cordEnd, true);
    MEdge edge1 = new MEdge("1", "2", "random", start, end);
    edge1.setWeight(4.0);
    assertEquals("random", edge1.getType());
  }
  
  @Test
  public void testGetId() {
    double[] cordStart = {0.0, 0.0};
    MNode start = new MNode("11", cordStart, true);
    double[] cordEnd = {1.0, 1.0};
    MNode end = new MNode("12", cordEnd, true);
    MEdge edge1 = new MEdge("1", "2", "random", start, end);
    edge1.setWeight(4.0);
    assertEquals("1", edge1.getId());
  }
  
  @Test
  public void testEquals() {
    double[] cordStart = {0.0, 0.0};
    MNode start = new MNode("11", cordStart, true);
    double[] cordEnd = {1.0, 1.0};
    MNode end = new MNode("11", cordEnd, true);
    MEdge edge1 = new MEdge("1", "2", "random", start, end);
    MEdge edge2 = new MEdge("1", "2", "random", start, end);
    MEdge edge3 = new MEdge("4", "3", "random", start, end);
    assertEquals(edge1, edge2);
    assertNotEquals(edge1, edge3);
    assertNotEquals(edge2, edge3);
  }
}
