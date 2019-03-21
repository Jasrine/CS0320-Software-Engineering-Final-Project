/**
 * 
 */
package edu.brown.cs.jkst.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author katelummis
 *
 */
public class Repl {

  BufferedReader br;
  String input;
  PrintWriter pw;

  CommandManager cm = new CommandManager();

  public void read() {
    try {
      PrintWriter pw = new PrintWriter(System.out, true);
      br = new BufferedReader(new InputStreamReader(System.in));
      input = br.readLine();
      while (input != null) {
        cm.processCommand(input, pw);
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
