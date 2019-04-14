package edu.brown.cs.jkst.suggest;

import java.util.LinkedList;

/**
 * class consisting of static methods for processing words for getting
 * suggestions using the trie.
 */
public final class WordProcessing {

  /**
   * constructor for word processing.
   */
  private WordProcessing() {

  }

  /**
   * computes the levenshtein edit distance of two Strings.
   *
   * @param word1
   *          String that we're checking against another String
   * @param word2
   *          String that we're checking against
   * @return int corresponding to the two Strings' levenshtein edit distance
   */
  public static int levenshteinDistance(String word1, String word2) {
    int maxWordLength = Math.max(word1.length(), word2.length());

    // edge cases - empty words
    if (word1.isEmpty() || word2.isEmpty()) {
      return maxWordLength;
    }

    int[][] dp = new int[word1.length() + 1][word2.length() + 1];
    dp[0][0] = (word1.charAt(0) == word2.charAt(0)) ? 0 : 1;

    // initialize table
    for (int i = 0; i <= word1.length(); i++) {
      dp[i][0] = i;
    }
    for (int i = 0; i <= word2.length(); i++) {
      dp[0][i] = i;
    }

    for (int i = 1; i <= word1.length(); i++) {
      for (int j = 1; j <= word2.length(); j++) {
        int iprime = Math.max(i - 1, 0);
        int jprime = Math.max(j - 1, 0);
        int left = dp[iprime][j] + 1;
        int right = dp[i][jprime] + 1;
        int diagonal = dp[iprime][jprime];
        int lastMin = Math.min(Math.min(right, left), diagonal);
        if (word1.charAt(i - 1) != word2.charAt(j - 1)) {
          diagonal += 1;
        }
        lastMin = Math.min(Math.min(right, left), diagonal);
        dp[i][j] = lastMin;
      }
    }

    return dp[word1.length()][word2.length()];
  }

  /**
   * computes the number of insertions made to get from one String to another.
   *
   * @param word1
   *          String that we're checking against another String
   * @param word2
   *          String that we're checking against
   * @return int corresponding to the two Strings' insertion distance
   */
  public static int numInsertions(String word1, String word2) {
    // edge cases - empty words
    if (word1.isEmpty() || word2.isEmpty()) {
      return Math.max(word1.length(), word2.length());
    }

    // insertion and deletion
    if (word1.charAt(0) != word2.charAt(0)) {
      if (word2.indexOf(word1.charAt(0)) > 0) {
        return 1 + numInsertions(word1, word2.substring(1, word2.length()));
      } else {
        return 1 + numInsertions(word1.substring(1, word1.length()), word2);
      }
    } else {
      return numInsertions(word1.substring(1, word1.length()), word2);
    }
  }

  /**
   * handles preprocessing for a line of words from the REPL so they can be used
   * to update frequencies in the bigramMap.
   *
   * @param line
   *          String read in from REPL, to handle preprocessing for
   * @return LinkedList of String with cleaned nonempty words
   */
  public static LinkedList<String> cleanedWords(String line) {
    String[] cleanedPieces = line.replaceAll("[^A-Za-z0-9]", " ").toLowerCase()
        .split(" ");
    LinkedList<String> nonemptyPieces = new LinkedList<String>();
    for (String piece : cleanedPieces) {
      if (piece.length() > 0) {
        nonemptyPieces.add(piece.trim());
      }
    }

    return nonemptyPieces;
  }

  /**
   * handles preprocessing for a line of words from the REPL so they can be used
   * to update frequencies in the bigramMap.
   *
   * @param commands
   *          String[] read in from REPL, to handle preprocessing for
   * @return LinkedList of String with cleaned nonempty words
   */
  public static LinkedList<String> cleanedAC(String[] commands) {
    LinkedList<String> cleanedPieces = new LinkedList<String>();
    for (int i = 0; i < commands.length; i++) {
      String piece = commands[i];
      String cleanedPiece = piece.replaceAll("[^A-Za-z0-9]", " ").toLowerCase();
      for (String newPiece : cleanedPiece.split(" ")) {
        if (newPiece.length() > 0) {
          cleanedPieces.add(newPiece);
        }
      }
    }

    return cleanedPieces;
  }

  /**
   * pastes a string to the beginning of all pieces of a collection.
   *
   * @param pieceToPaste
   *          String to paste onto beginning of piece
   * @param pieces
   *          Collection of String where we want to paste beginning piece
   * @return LinkedList of String containing modified pieces
   */
  public static LinkedList<String> pasteBeginning(String pieceToPaste,
      String[] pieces) {
    LinkedList<String> modifiedPieces = new LinkedList<String>();
    for (String piece : pieces) {
      modifiedPieces.add(pieceToPaste + piece);
    }

    return modifiedPieces;
  }
}
