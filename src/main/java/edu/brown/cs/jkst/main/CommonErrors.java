package edu.brown.cs.jkst.main;

/**
 * class containing standard error messages (all static methods) for all
 * application managers that are connected to the main REPL to use.
 */
public final class CommonErrors {

  /**
   * constructs default class.
   */
  private CommonErrors() {

  }

  /**
   * Standard error message when a file is not found at the given location.
   *
   * @param file
   *          String at which the file that is to be loaded in exists
   * @return String Error message saying that the file was not found
   */
  public static String fileNotFound(String file) {
    return "ERROR: File " + file + " not found in system!";
  }

  /**
   * Standard error message when no file is specified after a command that is
   * usually used to read in a file e.g. corpus or stars
   *
   * @return String Error message saying that no file has been specified for
   *         reading in
   */
  public static String noFileSpecified() {
    return "ERROR: File not specified!";
  }

  /**
   * Standard error message when an IOException was run into while attempting to
   * read the file.
   *
   * @return String Error message indicating the file could not be read
   *         successfully
   */
  public static String fileReadingException() {
    return "ERROR: Problem reading in file!";
  }

  /**
   * Standard error message when a negative integer is incorrectly specified to
   * a given command.
   *
   * @param command
   *          String which must take nonnegative integer argument
   * @return String message indicating first argument to command must be
   *         nonnegative
   */
  public static String firstArgumentNegative(String command) {
    return "ERROR: First argument to " + command + " must be nonnegative.";
  }

  /**
   * Standard error message when non-number arguments are supplied to command.
   *
   * @param command
   *          String command to which non-number arguments are supplied
   * @return String message indicating the command received unexpected
   *         non-numeric arguments
   */
  public static String genericNumberFormatException(String command) {
    return "ERROR: Arguments to  " + command + " must be integers or floats.";
  }
}
