package edu.brown.cs.jkst.repl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import edu.brown.cs.jkst.main.CommandManager;

/**
 * file that implements our repl as a singleton.
 */
public final class Repl {
  public static final Repl INSTANCE = new Repl();
  private static BufferedReader br;
  private static String input;
  private static PrintWriter pw;

  public static final CommandManager CM = new CommandManager();

  /**
   * method that implements the loop until an exit is triggered.
   */
  public void read() {
    try {
      pw = new PrintWriter(System.out, true);
      br = new BufferedReader(new InputStreamReader(System.in));
      input = br.readLine();
      while (input != null) {
        CM.processCommand(input, pw, true);
        input = br.readLine();
      }
    } catch (IOException ioe) {
      pw.println("ERROR: Problem with reading user input.");
    } finally {
      try {
        br.close();
      } catch (IOException ioe) {
        pw.println("ERROR: Buffer DNE");
      }
    }
  }
}
