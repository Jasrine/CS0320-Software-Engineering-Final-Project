package edu.brown.cs.jkst.trie;

import java.util.Comparator;

/**
 * Autocorrect Comparator for ranking.
 */
public class AutocorrectComparator implements Comparator<String> {

  private String originalWord;

  /**
   * Constructor for Autocorrect Comparator.
   *
   * @param originalWord
   *          String to compare for similarity to original.
   */
  public AutocorrectComparator(String originalWord) {
    this.originalWord = originalWord;
  }

  @Override
  public int compare(String o1, String o2) {
    if (o1.equals(o2)) {
      return 0;
    }

    if (o1.equals(this.originalWord)) {
      return 1;
    } else if (o2.equals(this.originalWord)) {
      return -1;
    }

    return o1.compareTo(o2);

    // int o1freq = this.frequencyMap.get(o1.trim().split(" ")[0]);
    // int o2freq = this.frequencyMap.get(o2.trim().split(" ")[0]);
    //
    // if (o1freq > o2freq) {
    // return 1;
    // } else if (o1freq < o2freq) {
    // return -1;
    // } else {
    // return -o1.compareTo(o2);
    // }
  }
}
