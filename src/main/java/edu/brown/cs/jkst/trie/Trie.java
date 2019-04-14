package edu.brown.cs.jkst.trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.jkst.suggest.WordProcessing;

/**
 * Class for constructing a Trie of type parameter T.
 *
 * @param <T>
 *          type of generic Trie
 */
public class Trie<T> {

  private Node root;

  /**
   * Constructor for Trie. Initializes a root node.
   */
  public Trie() {
    root = new Node(Character.MIN_VALUE, false);
  }

  /**
   * Class containing methods and definitions for Nodes used by Trie.
   */
  public class Node {
    private char c;
    private boolean end;
    private HashMap<Character, Node> children;

    /**
     * Constructor for a Trie Node.
     *
     * @param c
     *          char stored in the Trie
     * @param end
     *          boolean indicating whether this Node marks the end of a word
     */
    public Node(char c, boolean end) {
      this.c = c;
      this.end = end;
      this.children = new HashMap<Character, Node>();
    }

    /**
     * getter for children of Node.
     *
     * @return HashMap associating characters to children Nodes
     */
    public HashMap<Character, Node> getChildren() {
      return children;
    }

    /**
     * sets the end parameter of a Trie Node to b.
     *
     * @param b
     *          boolean indicating whether the word should end
     */
    public void setEndOfWord(boolean b) {
      end = b;
    }

    /**
     * getter for end attribute of Node.
     *
     * @return boolean indicating whether this Node's end parameter is set to
     *         true or to false
     */
    public boolean isEndOfWord() {
      return end;
    }

    /**
     * getter for Character stored in this Node.
     *
     * @return Character stored in this Node
     */
    public Character getChar() {
      return c;
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      if (this.end) {
        sb.append(this.c + "\n");
      }
      for (Character ch : this.children.keySet()) {
        String[] childsStringPieces = (this.children.get(ch).toString())
            .split("\n");
        for (String piece : childsStringPieces) {
          if (!piece.equals("")) {
            sb.append(this.c + piece + "\n");
          }
        }
      }
      return sb.toString();
    }

    /**
     * returns a Set of String of all the words at the Node.
     *
     * @return Set of String containing all words at this Node
     */
    public Set<String> wordsAtNode() {
      Set<String> result = new HashSet<String>();
      if (this.end) {
        result.add(Character.toString(this.c));
      }
      for (Character ch : this.children.keySet()) {
        Set<String> childStrings = this.children.get(ch).wordsAtNode();
        for (String piece : childStrings) {
          if (!piece.equals("")) {
            result.add(this.c + piece);
          }
        }
      }

      return result;
    }

    /**
     * returns a HashSet of String of all the words at the Node.
     *
     * @param word
     *          String we want to check against
     * @param led
     *          int indicating maximum led
     *
     * @return HashSet of String containing all words at this Node
     */
    public Set<String> ledWordsAtNode(String word, int led) {
      HashSet<String> result = new HashSet<String>();
      if (this.end) {
        result.add(Character.toString(this.c));
      }
      for (Character ch : this.children.keySet()) {
        Set<String> childStrings = this.children.get(ch).wordsAtNode();
        for (String piece : childStrings) {
          if (!piece.equals("")) {
            result.add(this.c + piece);
          }
        }
      }
      return result;
    }
  }

  /**
   * inserts a given word into our trie; starts at root and keeps checking if
   * the next character in the word is there, and if the next character is not
   * in children, it inserts that character in.
   *
   * @param word
   *          String to be inserted into trie
   */
  public void insert(String word) {
    Node current = root;

    for (int i = 0; i < word.length(); i++) {
      Character currChar = word.charAt(i);
      HashMap<Character, Node> currChildren = current.getChildren();
      if (currChildren.containsKey(currChar)) {
        current = currChildren.get(currChar);
      } else {
        current = new Node(currChar, false);
        currChildren.put(currChar, current); // how to make sure this is being
                                             // inserted at right depth?
      }
    }
    current.setEndOfWord(true);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Character c : root.children.keySet()) {
      sb.append(root.children.get(c).toString() + "\n");
    }
    return sb.toString();
  }

  /**
   * method that checks if a given word is in the trie.
   *
   * @param word
   *          String that we are searching for
   * @return boolean true if word is found, false if not
   */
  public boolean findWord(String word) {
    Node currNode = root;
    if (word.length() > 0) {
      Character currChar = word.charAt(0);
      int i = 0;
      while (currNode.children.containsKey(currChar) && i < word.length()) {
        currNode = currNode.children.get(currChar);
        i++;
        if (i < word.length()) {
          currChar = word.charAt(i);
        }
      }
      if (i == word.length()) {
        return currNode.end; // only true if we reach the end of the word
      }
    }

    return false;
  }

  /**
   * puts all the words in the trie that have a distance less than equal to led
   * into a Set and outputs that Set.
   *
   * @param results
   *          HashSet containing results so far
   * @param word
   *          String wrt which we're trying to find similar words
   * @param led
   *          int (non-negative), maximum distance that an output word can have
   *          with input word
   * @return Set of String containing all output words
   */
  public Set<String> findLedWords(Set<String> results, String word, int led) {
    for (Character c : root.children.keySet()) {
      Set<String> currWords = root.children.get(c).wordsAtNode();
      for (String currWord : currWords) {
        if (WordProcessing.levenshteinDistance(word, currWord) <= led) {
          results.add(currWord);
        }
      }
    }

    return results;
  }

  /**
   * handles word search when prefix is on.
   *
   * @param prefix
   *          String against which we are searching
   * @param results
   *          HashSet of String containing output words
   * @return HashSet of String containing all possible words with that prefix
   */
  public Set<String> findPrefixedWords(String prefix, Set<String> results) {
    Node currNode = root;
    Character currChar = prefix.charAt(0);
    int i = 0;
    while (currNode.children.containsKey(currChar) && i < prefix.length()) {
      currNode = currNode.children.get(currChar);
      i++;
      if (i < prefix.length()) {
        currChar = prefix.charAt(i);
      }
    }

    if (i == prefix.length()) {
      Set<String> suffixes = currNode.wordsAtNode();
      String prefixWithoutLastChar = prefix.substring(0, prefix.length() - 1);
      for (String suffix : suffixes) {
        results.add(prefixWithoutLastChar + suffix);
      }
    }

    return results;
  }

  /**
   * gets results from searching smartly.
   *
   * @param prefix
   *          String prefix for word
   * @param results
   *          HashSet of String containing all results so far
   * @return HashSet of String containing all smart results
   */
  public Set<String> findSmartWords(String prefix, Set<String> results) {
    // basically uses Node.toString() on the right node

    Node currNode = root;
    Character currChar = prefix.charAt(0);
    int i = 0;
    while (currNode.children.containsKey(currChar) && i < prefix.length()) {
      currNode = currNode.children.get(currChar);
      i++;
      if (i < prefix.length()) {
        currChar = prefix.charAt(i);
      }
    }
    String[] suffixes = currNode.toString().split("\n");
    StringBuilder sb = new StringBuilder();
    String prefixWithoutLastChar = prefix.substring(0, prefix.length() - 1);
    for (String suffix : suffixes) {
      results.add(prefixWithoutLastChar + suffix);
      sb.append(prefixWithoutLastChar + suffix + "\n");
    }

    return results;
  }
}
