package edu.brown.cs.kpal1sa38.repl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.brown.cs.kpal1sa38.maps.MNode;

/**
 * class that tests methods in StringOps.
 */
public class StringOpsTest {

  /**
   * method that tests enclosedInQuotes.
   */
  @Test
  public void testEnclosedInQuotes() {
    assertTrue(StringOps.enclosedInQuotes("\"cat\""));
    assertTrue(StringOps.enclosedInQuotes("\"\""));
    assertTrue(StringOps.enclosedInQuotes("\"\"\""));
    assertFalse(StringOps.enclosedInQuotes("\""));
    assertFalse(StringOps.enclosedInQuotes(""));
    assertFalse(StringOps.enclosedInQuotes("\\"));
  }

  /**
   * method that tests noPath.
   */
  @Test
  public void testNoPath() {
    double[] coordinates = {
        0.0, 0.0
    };
    MNode n1 = new MNode("\n\0123", coordinates, true);
    MNode n2 = new MNode("\n\0124", coordinates, false);

    assertEquals(StringOps.noPath(n1, n2), "\n\0123 -/- \n\0124");
    assertEquals(StringOps.noPath(n1, n1), "\n\0123 -/- \n\0123");
    assertEquals(StringOps.noPath(n2, n1), "\n\0124 -/- \n\0123");
  }

  /**
   * method that tests trimStreet in StringOps.
   */
  @Test
  public void testTrim() {
    assertEquals(StringOps.trimStreet("ab st"), "ab st");
    assertEquals(StringOps.trimStreet("ab st "), "ab st");
    assertEquals(StringOps.trimStreet("  ab st"), "ab st");
    assertEquals(StringOps.trimStreet("ab^st"), "ab^st");
    assertEquals(StringOps.trimStreet("ab^\"st"), "ab^\"st");
  }
}
