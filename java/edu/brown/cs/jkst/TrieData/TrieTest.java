package edu.brown.cs.kpal1sa38.TrieData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * The TrieTest class tests all the public (and indirectly private) methods in
 * the Trie class.
 */
public class TrieTest {

  /**
   * test that checks whether Trie insert method works as expected.
   */
  @Test
  public void testInsert() {
    Trie<Character> tester1 = new Trie<Character>();
    tester1.insert("cat");
    tester1.insert("cab");
    tester1.insert("good");
    assertTrue(tester1.findWord("cat"));
    assertTrue(tester1.findWord("cab"));
    assertTrue(tester1.findWord("good"));
    assertFalse(tester1.findWord("ca"));
  }

  /**
   * method that tests findPrefixedWords in Trie.
   */
  @Test
  public void testPrefix() {
    Trie<Character> tester1 = new Trie<Character>();
    tester1.insert("cat");
    tester1.insert("cab");
    tester1.insert("good");
    tester1.insert("caterer");
    List<Character> ca = new ArrayList<>();
    ca.add('c');
    ca.add('a');
    Set<String> actualResult = new HashSet<String>();
    actualResult = tester1.findPrefixedWords("ca", actualResult);
    assertTrue(actualResult.contains("cat"));
    assertTrue(actualResult.contains("caterer"));
    assertTrue(actualResult.contains("cab"));
    assertEquals(3, actualResult.size());
  }

  /**
   * tests the findLedWords method in Trie.
   */
  @Test
  public void testLed() {
    Trie<Character> tester1 = new Trie<Character>();
    tester1.insert("bad");
    tester1.insert("cat");
    tester1.insert("cab");
    tester1.insert("good");
    tester1.insert("caterer");
    List<Character> ca = new ArrayList<>();
    ca.add('c');
    ca.add('a');
    Set<String> actualResult = new HashSet<String>();
    actualResult = tester1.findLedWords(actualResult, "ca", 2);
    assertTrue(actualResult.contains("bad"));
    assertTrue(actualResult.contains("cab"));
    assertTrue(actualResult.contains("cat"));
    assertEquals(actualResult.size(), 3);
  }

  /**
   * tests the findLedWords method in Trie in case of led = 0.
   */
  @Test
  public void testLedZero() {
    Trie<Character> tester1 = new Trie<Character>();
    tester1.insert("bad");
    tester1.insert("cat");
    tester1.insert("cab");
    tester1.insert("good");
    tester1.insert("caterer");
    List<Character> ca = new ArrayList<>();
    ca.add('c');
    ca.add('a');
    Set<String> anotherActualResult = new HashSet<String>();
    anotherActualResult = tester1.findLedWords(anotherActualResult, "ca", 0);
    assertEquals(anotherActualResult.size(), 0);
  }

}
