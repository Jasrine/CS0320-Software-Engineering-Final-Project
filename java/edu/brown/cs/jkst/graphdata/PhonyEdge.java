package edu.brown.cs.kpal1sa38.graphdata;

import java.util.Objects;

/**
 * The PhonyEdge class implements the Edge interface and is
 * used for unit testing purposes (testing shortest path).
 */
public class PhonyEdge implements Edge<PhonyNode, PhonyEdge> {
  private PhonyNode start;
  private PhonyNode end;
  private String name;
  private Double weight;

  /**
   * Constructor for PhonyEdge
   * @param start - Starting Node.
   * @param end - Ending Node.
   * @param edgeName - Name of the Edge.
   * @param weight - Weight of the Edge.
   */
  public PhonyEdge(PhonyNode start, PhonyNode end,
                   String edgeName, Double weight) {
    this.start = start;
    this.end = end;
    this.name = edgeName;
    this.weight = weight;
  }
  @Override
  public PhonyNode getStart() {
    return this.start;
  }

  @Override
  public PhonyNode getEnd() {
    return this.end;
  }

  @Override
  public String getEdgeName() {
    return this.name;
  }

  @Override
  public Double getWeight() {
    return this.weight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PhonyEdge phonyEdge = (PhonyEdge) o;
    return Objects.equals(name, phonyEdge.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "PhonyEdge{" +
            "start=" + start +
            ", end=" + end +
            ", name='" + name + '\'' +
            ", weight=" + weight +
            '}';
  }
}
