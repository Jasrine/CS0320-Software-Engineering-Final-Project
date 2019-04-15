package edu.brown.cs.kpal1sa38.autocorrect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

/**
 * contains tests for methods in WordProcessing in the autocorrect package.
 */
public class WordProcessingTest {

  /**
   * unit tests for the levenshteinDistance method in WordProcessing.
   */
  @Test
  public void testLevenshteinDistance() {
    // test for sameness
    assertEquals(WordProcessing.levenshteinDistance("chair", "chair"), 0);
    // test for order
    assertEquals(WordProcessing.levenshteinDistance("char", "chair"), 1);
    assertEquals(WordProcessing.levenshteinDistance("chair", "char"), 1);
    // test for empty strings
    assertEquals(WordProcessing.levenshteinDistance("", "chair"), 5);
    assertEquals(WordProcessing.levenshteinDistance("char", ""), 4);
    // test for lowest possible led
    assertEquals(WordProcessing.levenshteinDistance("charred chair", "chair"),
        8);
    assertEquals(WordProcessing.levenshteinDistance("charred chair",
        "chair chair"), 3);
    assertEquals(WordProcessing.levenshteinDistance("charred chair",
        "chaired char"), 2);
    // general tests
    assertEquals(WordProcessing.levenshteinDistance("charred", "chair"), 3);
  }

  /**
   * unit tests for the numInsertions method in WordProcessing. this method
   * requires that string1 be a substring of string2.
   */
  @Test
  public void testNumInsertions() {
    // empty cases
    assertEquals(WordProcessing.levenshteinDistance("", ""), 0);
    assertEquals(WordProcessing.levenshteinDistance("", "dog"), 3);
    assertEquals(WordProcessing.levenshteinDistance("dog", ""), 3);

    // equality
    assertEquals(WordProcessing.levenshteinDistance("dog", "dog"), 0);
    assertEquals(WordProcessing.levenshteinDistance("dagger", "dagger"), 0);

    // substrings
    assertEquals(WordProcessing.levenshteinDistance("she", "shes"), 1);
    assertEquals(WordProcessing.levenshteinDistance("che", "cheese"), 3);
    assertEquals(WordProcessing.levenshteinDistance("tbl", "table"), 2);
  }

  /**
   * unit tests for the cleanedWords method in WordProcessing.
   */
  @Test
  public void testCleanedWords() {
    // empty cases
    assertTrue(WordProcessing.cleanedWords("").isEmpty());
    assertTrue(WordProcessing.cleanedWords("&").isEmpty());
    assertTrue(WordProcessing.cleanedWords("    ").isEmpty());
    assertTrue(WordProcessing.cleanedWords(" ^   !").isEmpty());

    // non-empty cases
    assertEquals(WordProcessing.cleanedWords(" here ").getFirst(), "here");
    assertEquals(WordProcessing.cleanedWords(" here is").getFirst(), "here");
    assertEquals(WordProcessing.cleanedWords(" here is").getLast(), "is");
    assertEquals(WordProcessing.cleanedWords(" here is").size(), 2);
    assertEquals(WordProcessing.cleanedWords("can't").getFirst(), "can");
    assertEquals(WordProcessing.cleanedWords("can't").getLast(), "t");
    assertEquals(WordProcessing.cleanedWords("can't").size(), 2);
    assertEquals(WordProcessing.cleanedWords("can ' t").getFirst(), "can");
    assertEquals(WordProcessing.cleanedWords("can ' t").getLast(), "t");
    assertEquals(WordProcessing.cleanedWords("can ' t").size(), 2);
    assertEquals(WordProcessing.cleanedWords("can*t ").getFirst(), "can");
    assertEquals(WordProcessing.cleanedWords("can*t ").getLast(), "t");
    assertEquals(WordProcessing.cleanedWords("can*t ").size(), 2);
  }

  /**
   * unit tests for the cleanedAC method in WordProcessing.
   */
  @Test
  public void testCleanedAC() {
    // empty cases
    String[] test1 = new String[1];
    test1[0] = "";
    LinkedList<String> test = WordProcessing.cleanedAC(test1);
    assertTrue(test.isEmpty());

    String[] test2 = new String[1];
    test2[0] = "$$$$";
    test = WordProcessing.cleanedAC(test2);
    assertTrue(test.isEmpty());

    String[] test3 = new String[1];
    test3[0] = "$$$$ $$$$";
    test = WordProcessing.cleanedAC(test3);
    assertTrue(test.isEmpty());

    String[] test4 = new String[3];
    test4[0] = "$$$$";
    test4[1] = "";
    test4[2] = " ^";
    test = WordProcessing.cleanedAC(test4);
    assertTrue(test.isEmpty());

    // apostrophes and word separations
    String[] test5 = new String[1];
    test5[0] = "can't";
    test = WordProcessing.cleanedAC(test5);
    assertEquals(test.getFirst(), "can");
    assertEquals(test.getLast(), "t");
    assertEquals(test.size(), 2);
  }

  /**
   * unit tests for the cleanedWords method in WordProcessing.
   */
  @Test
  public void testPasteBeginning() {
    String pieceToPaste = "hello";

    String[] test1 = new String[0];
    String[] test2 = new String[1];
    test2[0] = "world";
    String[] test3 = new String[2];
    test3[0] = "world";
    test3[1] = "kitty";
    String[] test4 = new String[3];
    test4[0] = "world";
    test4[1] = "kitty";
    test4[2] = "fresh";
    String[] test5 = new String[2];
    test5[0] = "world";
    test5[1] = "fresh";

    LinkedList<String> result1 = new LinkedList<String>();
    assertEquals(WordProcessing.pasteBeginning(pieceToPaste, test1), result1);
    result1.add("helloworld");
    assertEquals(WordProcessing.pasteBeginning(pieceToPaste, test2), result1);
    result1.add("hellokitty");
    assertEquals(WordProcessing.pasteBeginning(pieceToPaste, test3), result1);
    result1.add("hellofresh");
    assertEquals(WordProcessing.pasteBeginning(pieceToPaste, test4), result1);
    result1.remove("hellokitty");
    assertEquals(WordProcessing.pasteBeginning(pieceToPaste, test5), result1);
  }
}
