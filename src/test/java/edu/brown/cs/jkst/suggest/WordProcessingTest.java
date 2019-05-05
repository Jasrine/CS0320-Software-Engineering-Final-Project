package edu.brown.cs.jkst.suggest;

import org.junit.Test;

/**
 * tests static methods in the autocorrect files.
 */
public final class WordProcessingTest {

  /**
   * tests the levenshteinDistance method in the autocorrect app manager.
   */
  @Test
  public void testLevenshteinDistance() {
    // test for sameness
    assert (WordProcessing.levenshteinDistance("chair", "chair") == 0);
    // test for order
    assert (WordProcessing.levenshteinDistance("char", "chair") == 1);
    assert (WordProcessing.levenshteinDistance("chair", "char") == 1);
    // test for empty strings
    assert (WordProcessing.levenshteinDistance("", "chair") == 5);
    assert (WordProcessing.levenshteinDistance("char", "") == 4);
    // test for lowest possible led
    // assert (WordProcessing.levenshteinDistance("charred chair", "chair") ==
    // 8);
    assert (WordProcessing.levenshteinDistance("charred chair",
        "chair chair") == 3);
    assert (WordProcessing.levenshteinDistance("charred chair",
        "chaired char") == 2);
    // general tests
    assert (WordProcessing.levenshteinDistance("charred", "chair") == 3);
  }

  /**
   * method that calls all tests in this file.
   */
  @Test
  public void testAll() {
    testLevenshteinDistance();
  }
}
