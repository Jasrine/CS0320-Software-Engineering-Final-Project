package edu.brown.cs.kpal1sa38.KDTreeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * StarParser is a public class that is extended by Parser, wherein the Parser
 * class has the parse method which has a general way of parsing documents.
 */
public class StarParser extends Parser {
  /**
   * Constructor for StarParser.
   */
  public StarParser() {

  }
  /**
   * The following method checks whether the csv file that is going to be parsed
   * has a valid format.
   * @param filename - filepath to the csv file
   * @return - parsed data of stars,i.e., an arraylist of stars.
   */
  public ArrayList<Star> addData(String filename) {
    int counter = 0;
    boolean firstNotVisited = true;
    List<String[]> dataset = super.parse(filename, ",");
    if (dataset == null) {
      return null;
    }
    ArrayList<Star> parsedData = new ArrayList<>();
    for (String[] data : dataset) {
      if (data.length == 5) {
        if (firstNotVisited) {
          firstNotVisited = false;
          if (!data[0].equals("StarID")
                  || !data[1].equals("ProperName")
                  || !data[2].equals("X")
                  || !data[3].equals("Y")
                  || !data[4].equals("Z")) {
            System.out.println("ERROR: Invalid Header Line Format");
            break;
          }
        } else {
          try {
            int id = Integer.parseInt(data[0]);
            double x = Double.parseDouble(data[2]);
            double y = Double.parseDouble(data[3]);
            double z = Double.parseDouble(data[4]);
            double[] coordinates = {x, y, z};
            Star newData = new Star(id, data[1], coordinates);
            parsedData.add(newData);
            counter++;
          } catch (NumberFormatException e) {
            System.out.println("ERROR: Coordinates in row or "
                    + "ID number of the following star (row), "
                    + counter + ", are not numbers. (Malformed Input)");
            break;
          }
        }
      }
    }
    System.out.println("Read " + counter + " stars from " + filename);
    return parsedData;
  }

  /**
   * Method to create a HashMap of String of properNames and Stars
   * associated with the properName.
   * @param data - Arraylist of Star used to create a HashMap.
   * @return - HashMap of keys as Strings and values as Stars.
   */
  public HashMap<String, Star> createHashMap(ArrayList<Star> data) {
    HashMap<String, Star> map = new HashMap<String, Star>();
    for (Star row : data) {
      if (!(row.getProperName().equals(""))) {
        map.put(row.getProperName(), row);
      }
    }
    return map;
  }
}
