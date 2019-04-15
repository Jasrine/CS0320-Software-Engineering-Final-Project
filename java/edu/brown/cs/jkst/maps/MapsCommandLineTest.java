package edu.brown.cs.kpal1sa38.maps;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Contains tests for MapsCommandLine.
 * The rest of the methods are interlinked. Hence, those methods are system-tested.
 */
public class MapsCommandLineTest {

  /**
   *  Test for isValid method.
   */
  @Test
  public void testIsValid() {
    assertFalse(MapsCommandLine.INSTANCE.isValid("maps"));
    assertTrue(MapsCommandLine.INSTANCE.isValid("map"));
    assertFalse(MapsCommandLine.INSTANCE.isValid("wayyyyyys"));
    assertTrue(MapsCommandLine.INSTANCE.isValid("ways"));
    assertTrue(MapsCommandLine.INSTANCE.isValid("nearest"));
    assertTrue(MapsCommandLine.INSTANCE.isValid("route"));
    assertTrue(MapsCommandLine.INSTANCE.isValid("suggest"));
    assertFalse(MapsCommandLine.INSTANCE.isValid("neeeaarrest"));
  }
   @Test
  public void testParseCoordinates() {
    assertEquals(0.1, MapsCommandLine.INSTANCE.parseCoordinates("0.1", "0.3")[0]);
     assertEquals(0.3, MapsCommandLine.INSTANCE.parseCoordinates("0.1", "0.3")[1]);
   }

}
