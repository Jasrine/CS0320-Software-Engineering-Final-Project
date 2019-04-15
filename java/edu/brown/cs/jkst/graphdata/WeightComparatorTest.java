package edu.brown.cs.kpal1sa38.graphdata;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static junit.framework.TestCase.assertEquals;

/**
 * The WeightComparatorTest class tests methods
 * in WeightComparator class.
 */
public class WeightComparatorTest {
  @Test
  public void testCompare() {
    HashMap<PhonyNode, Double> map = new HashMap<>();
    double[] cord1 = {1, 1};
    double[] cord2 = {2, 2};
    double[] cord3 = {3, 3};
    PhonyNode node1 = new PhonyNode("1", "1", cord1);
    PhonyNode node2 = new PhonyNode("2", "2", cord2);
    PhonyNode node3 = new PhonyNode("3", "3", cord3);
    map.put(node1, 5.0);
    map.put(node2, 3.0);
    map.put(node3, 2.0);
    List<PhonyNode> nodes = new ArrayList<>();
    nodes.add(node3);
    nodes.add(node1);
    nodes.add(node2);
    nodes.sort(new WeightComparator<PhonyNode>(map));
    assertEquals(node3, nodes.get(0));
    assertEquals(node2, nodes.get(1));
    assertEquals(node1, nodes.get(2));
  }
}
