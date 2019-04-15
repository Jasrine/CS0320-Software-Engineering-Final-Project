package edu.brown.cs.kpal1sa38.KDTreeData;

import java.util.Arrays;
import java.util.Objects;


/**
 * The Star class implements KDTreeObject, which indicates that
 * it can be used to create KDTrees. The class contains all
 * the required implementations as well as more methods
 * specific to this class.
 */
public class Star implements KDTreeObject {
  private int starID;
  private String properName;
  private double[] coordinates;
  private static final int PRIME = 31;

  /**
   * Constructor for Star.
   * @param starID - ID of the star
   * @param properName - Name of the star
   * @param coordinates - Coordinates of the star.
   */
  public Star(int starID, String properName, double[] coordinates) {
    this.starID = starID;
    this.properName = properName;
    this.coordinates = coordinates;
  }

  /**
   * Get axis distance between two stars (rewrite).
   * @param destination - Star node.
   * @param axis - particular dimensional axis (eg. 0 for x axis).
   * @return the axis Distance.
   */
  public double getAxisDistanceStar(Star destination, int axis) {
    double coordinateSource = this.coordinates[axis];
    double coordinateDest = destination.coordinates[axis];
    double axisDist = Math.abs(coordinateDest - coordinateSource);
    return axisDist;
  }
  /**
   * Euclidean distance between two stars.
   * @param destination - Star destination node.
   * @return - real distance between the two stars.
   */
  public double getDistanceStar(Star destination) {
    double distance =
            Math.sqrt(Math.pow(getAxisDistanceStar(destination, 0), 2)
            + Math.pow(getAxisDistanceStar(destination, 1), 2)
            + Math.pow(getAxisDistanceStar(destination, 2), 2));
    return distance;
  }
  /**
   * Get properName of the Star.
   * @return - String of name.
   */
  public String getProperName() {
    return properName;
  }
  /**
   * Get ID of the Star.
   * @return - int of starID.
   */
  public int getStarID() {
    return starID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Star star = (Star) o;
    return starID == star.starID
            && Objects.equals(properName, star.properName)
            && Arrays.equals(coordinates, star.coordinates);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(starID, properName);
    result = PRIME * result + Arrays.hashCode(coordinates);
    return result;
  }

  @Override
  public double getDistance(KDTreeObject destination) {
    boolean isValid = destination instanceof Star;
    double distance = 0.0;
    if (isValid) {
      distance = getDistanceStar((Star) destination);
    } else {
      System.out.println("ERROR: Cannot find real distance"
              + " between two different objects.");
      return Double.NaN;
    }
    return distance;
  }

  @Override
  public double getAxisDistance(KDTreeObject destination, int axis) {
    boolean isValid = destination instanceof Star;
    double axisDistance = 0.0;
    if (isValid) {
      axisDistance = getAxisDistanceStar((Star) destination, axis);
    } else {
      System.out.println("ERROR: Cannot find axis distance "
              + " between two different objects.");
      return Double.NaN;
    }
    return axisDistance;
  }
  @Override
  public double getCoordinates(int axis) {
    return this.coordinates[axis];
  }

}
