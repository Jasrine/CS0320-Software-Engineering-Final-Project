package edu.brown.cs.kpal1sa38.graphdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The PhonyNode class implements the Node interface and is
 * used for unit testing purposes (testing shortest path).
 */
public class PhonyNode implements Node<PhonyNode, PhonyEdge> {
  private String id;
  private List<PhonyEdge> neighbors = new ArrayList<>();
  private double[] coordinates;

  /**
   * Constructor for PhonyNode.
   */
  public PhonyNode(String name, String id, double[] cord) {
    this.id = id;
    this.coordinates = cord;
  }
  public double getCoordinates(int axis) {
    return this.coordinates[axis];
  }
  @Override
  public String getNodeID() {
    return this.id;
  }

  @Override
  public PhonyEdge getEdge(PhonyNode node) {
    for (PhonyEdge e : neighbors) {
      if (e.getEnd().getNodeID().equals(node.getNodeID())) {
        return e;
      }
    }
    return null;
  }

  @Override
  public List<PhonyEdge> getNeighbors() {
    return this.neighbors;
  }

  @Override
  public Double getDist(PhonyNode destination) {
    double diffLat = Math.toRadians(
            destination.getCoordinates(0) - this.coordinates[0]);
    double diffLong = Math.toRadians(
            destination.getCoordinates(1) - this.coordinates[1]);
    double lat1inRad = Math.toRadians(this.coordinates[0]);
    double lat2inRad = Math.toRadians(destination.getCoordinates(0));
    double part1 = Math.pow(Math.sin(diffLat / 2), 2)
            + Math.pow(Math.sin(diffLong / 2), 2)
            * Math.cos(lat1inRad)
            * Math.cos(lat2inRad);
    double part2 = 2 * Math.asin(Math.sqrt(part1));
    return part2;
  }

  /**
   * Add edge to the current list of edges in neighbors.
   * @param edge - a PhonyEdge
   */
  public void addNeighbor(PhonyEdge edge) {
    this.neighbors.add(edge);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PhonyNode phonyNode = (PhonyNode) o;
    return Objects.equals(id, phonyNode.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
