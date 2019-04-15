package edu.brown.cs.kpal1sa38.maps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * unit tests on MapsDatabase.
 */
public class MapsDatabaseTest {

  /**
   * tests findSuggestion which takes in a query and returns a set of strings
   * containing suggestions.
   */
  @Test
  public void testFindSuggestion() {
    // small maps tests
    MapsDatabase.INSTANCE.createConnection("data/maps/smallMaps.sqlite3");
    MapsDatabase.INSTANCE.addAllNames();
    Set<String> test1 = MapsDatabase.INSTANCE.findSuggestion("Chih");
    assertEquals(test1.size(), 1);
    assertTrue(test1.contains("Chihiro Ave"));
    Set<String> test2 = MapsDatabase.INSTANCE.findSuggestion("Chabiro Ave");
    assertEquals(test2.size(), 1);
    assertTrue(test2.contains("Chihiro Ave"));
    Set<String> test3 = MapsDatabase.INSTANCE.findSuggestion("Sunset");
    assertEquals(test3.size(), 0);

    // big maps tests
    MapsDatabase.INSTANCE.createConnection("data/maps/maps.sqlite3");
    MapsDatabase.INSTANCE.addAllNames();
    Set<String> test4 = MapsDatabase.INSTANCE.findSuggestion("Thayer St");
    assertEquals(test4.size(), 3);
    assertTrue(test4.contains("Thayer St"));
    assertTrue(test4.contains("Thayer Street"));
    assertTrue(test4.contains("Thames St"));
    Set<String> test5 = MapsDatabase.INSTANCE.findSuggestion("Angell");
    assertTrue(test5.size() >= 5);
    assertTrue(test5.contains("Angell Road"));
    assertTrue(test5.contains("Angell Court"));
    assertTrue(test5.contains("Angell Street"));
    assertTrue(test5.contains("Angell Avenue"));
  }

  /**
   * tests the createConnection method, which returns 0 for success, else 1.
   * also checked through system tests.
   */
  @Test
  public void testCreateConnection() {
    assertEquals(MapsDatabase.INSTANCE.createConnection(
        "data/maps/smallMaps.sqlite3"), 0);
    assertEquals(MapsDatabase.INSTANCE.createConnection(
        "data/maps/smallMaps1.sqlite3"), 1);
    assertEquals(MapsDatabase.INSTANCE.createConnection(
        "data/maps/maps.sqlite"), 1);
  }

  /**
   * method that collectively tests addNodes and getNode, since addNodes
   * modifies a map that is private, but getNode works by accessing this map if
   * it is filled in correctly.
   */
  @Test
  public void testAddNodesAndGetNode() {
    // small maps tests
    MapsCommandLine.INSTANCE.run("map data/maps/smallMaps.sqlite3");
    MNode small1 = MapsDatabase.INSTANCE.getNode("/n/0");
    assertEquals(small1.getCoordinates(0), 41.82, 0.0001);
    assertEquals(small1.getCoordinates(1), -71.4, 0.0001);
    MNode small2 = MapsDatabase.INSTANCE.getNode("/n/4");
    assertEquals(small2.getCoordinates(0), 41.8203, 0.0001);
    assertEquals(small2.getCoordinates(1), -71.4003, 0.0001);
  }

  /**
   * method that tests getAllTraversableNodes.
   */
  @Test
  public void testGetAllTraversableNodes() {
    // small maps tests
    MapsDatabase.INSTANCE.createConnection("data/maps/smallMaps.sqlite3");
    MapsDatabase.INSTANCE.addNodes();
    List<MNode> smallTraverse = MapsDatabase.INSTANCE.getAllTraversableNodes();
    assertEquals(6, smallTraverse.size());
    // prove uniqueness
    Set<MNode> smallTraverseSet = new HashSet<MNode>();
    for (MNode node : smallTraverse) {
      smallTraverseSet.add(node);
    }
    assertEquals(smallTraverse.size(), smallTraverseSet.size());

  }

  /**
   * tests getWayIdByName by checking if it gets correct ids associated with a
   * way name.
   */
  @Test
  public void testGetWayIdByName() {
    // small maps tests
    MapsDatabase.INSTANCE.createConnection(
        "data/maps/smallMaps.sqlite3");
    Set<String> test1 = MapsDatabase.INSTANCE.getWayIdByName("Chihiro Ave");
    assertEquals(2, test1.size());
    assertTrue(test1.contains("/w/0"));
    assertTrue(test1.contains("/w/1"));
    Set<String> test2 = MapsDatabase.INSTANCE.getWayIdByName("Sootball Ln");
    assertEquals(1, test2.size());
    assertTrue(test2.contains("/w/3"));
    Set<String> test3 = MapsDatabase.INSTANCE.getWayIdByName("Sunset");
    assertEquals(0, test3.size());

    // big maps tests
    MapsDatabase.INSTANCE.createConnection("data/maps/maps.sqlite3");
    Set<String> test4 = MapsDatabase.INSTANCE.getWayIdByName("Mile Road");
    assertTrue(test4.size() > 2);
    assertTrue(test4.contains("/w/4171.7163.19354674.0.2"));
    assertTrue(test4.contains("/w/4171.7163.19354674.10.1"));
    Set<String> test5 = MapsDatabase.INSTANCE.getWayIdByName("Thayer Drive");
    assertTrue(test5.size() > 1);
    assertTrue(test5.contains("/w/4159.7127.19353636.2.1"));
    assertTrue(test5.contains("/w/4159.7127.19353636.4.1"));
    Set<String> test6 = MapsDatabase.INSTANCE.getWayIdByName("Subset");
    assertEquals(0, test6.size());

  }

  /**
   * tests getIntersection by checking if it gets the intersections of two
   * streets.
   */
  @Test
  public void testGetIntersection() {
    // small maps tests
    MapsDatabase.INSTANCE.createConnection("data/maps/smallMaps.sqlite3");
    String test1 = MapsDatabase.INSTANCE.getIntersection("Chihiro Ave",
        "Chihiro Ave");
    assertEquals(test1, "/n/1");

    // end nodes match for both streets; shouldn't allow it.
    String test2 = MapsDatabase.INSTANCE.getIntersection("Sootball Ln",
        "Yubaba St");
    assertTrue(!test2.isEmpty());
    Set<String> test3 = MapsDatabase.INSTANCE.findSuggestion("Sunset");
    assertEquals(test3.size(), 0);

    // big maps tests
    MapsDatabase.INSTANCE.createConnection("data/maps/maps.sqlite3");
  }

  /**
   * method that tests getCoordinates, which returns a string array with the
   * coordinates of a node, given the id for the node.
   */
  @Test
  public void testGetCoordinates() {
    // small maps tests
    MapsDatabase.INSTANCE.createConnection("data/maps/smallMaps.sqlite3");
    String[] test1 = MapsDatabase.INSTANCE.getCoordinates("/n/1");
    assertEquals(2, test1.length);
    assertEquals(Double.parseDouble("41.8203"), Double.parseDouble(test1[0]),
        0.1);
    assertEquals("-71.4", test1[1]);

    // end nodes match for both streets; shouldn't allow it.
    String test2 = MapsDatabase.INSTANCE.getIntersection("Sootball Ln",
        "Yubaba St");
    assertNotEquals(test2, "");
    Set<String> test3 = MapsDatabase.INSTANCE.findSuggestion("Sunset");
    assertEquals(0, test3.size());

    // big maps tests
    MapsDatabase.INSTANCE.createConnection("data/maps/maps.sqlite3");
  }

  /**
   * methods that tests getWaysCorrectly, which takes in start and end
   * coordinates, returns a hash set of edges.
   */
  @Test
  public void testGetWaysCorrectly() {
    // small maps tests
    MapsDatabase.INSTANCE.createConnection("data/maps/smallMaps.sqlite3");
    double[] start = {
        41.82, -71.5
    };
    double[] end = {
        41.83, -71
    };
    Set<MEdge> test1 = MapsDatabase.INSTANCE.getWaysCorrectly(start, end);
    assertTrue(!test1.isEmpty());
  }

  /**
   * method that tests getWaysNodes, takes in start and end coordinates. returns
   * nodes within a bounding box.
   */
  @Test
  public void testGetWaysNodes() {
    // small maps tests
    MapsDatabase.INSTANCE.createConnection("data/maps/smallMaps.sqlite3");
    MapsDatabase.INSTANCE.addNodes();
    double[] start = {
        41.82, -71.4
    };
    double[] end = {
        41.83, -71.41
    };
    assertTrue(MapsDatabase.INSTANCE.getWaysNodes(start, start).isEmpty());
    assertTrue(MapsDatabase.INSTANCE.getWaysNodes(end, end).isEmpty());
  }
}
