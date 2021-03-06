package keittokirja.test;
// Generated by ComTest BEGIN
import keittokirja.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.03.16 12:00:07 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class AineksetTest {


  // Generated by ComTest BEGIN
  /** testIterator26 */
  @Test
  public void testIterator26() {    // Ainekset: 26
    Ainekset ainekset = new Ainekset(); 
    Aines a1 = new Aines(); 
    Aines a2 = new Aines(); 
    Aines a3 = new Aines(); 
    a1.taytaTiedolla(2); 
    a2.taytaTiedolla(2); 
    a3.taytaTiedolla(1); 
    ainekset.lisaa(a1); 
    ainekset.lisaa(a2); 
    ainekset.lisaa(a3); 
    Iterator<Aines> i2=ainekset.iterator(); 
    assertEquals("From: Ainekset line: 42", a1, i2.next()); 
    assertEquals("From: Ainekset line: 43", a2, i2.next()); 
    assertEquals("From: Ainekset line: 44", a3, i2.next()); 
    try {
    assertEquals("From: Ainekset line: 45", a1, i2.next()); 
    fail("Ainekset: 45 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
    int n = 0; 
    int anrot[] = { 2, 2, 1} ; 
    for (Aines a : ainekset) {
    assertEquals("From: Ainekset line: 50", anrot[n], a.getRid()); 
    n++; 
    }
    assertEquals("From: Ainekset line: 53", 3, n); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaAinekset75 */
  @Test
  public void testAnnaAinekset75() {    // Ainekset: 75
    Ainekset ainekset = new Ainekset(); 
    Aines a1 = new Aines(); 
    Aines a2 = new Aines(); 
    Aines a3 = new Aines(); 
    a1.taytaTiedolla(2); 
    a2.taytaTiedolla(2); 
    a3.taytaTiedolla(1); 
    ainekset.lisaa(a1); 
    ainekset.lisaa(a2); 
    ainekset.lisaa(a3); 
    List<Aines> loytyneet; 
    loytyneet = ainekset.annaAinekset(3); 
    assertEquals("From: Ainekset line: 91", 0, loytyneet.size()); 
    loytyneet = ainekset.annaAinekset(2); 
    assertEquals("From: Ainekset line: 93", 2, loytyneet.size()); 
    assertEquals("From: Ainekset line: 94", true, loytyneet.get(0) == a1); 
    assertEquals("From: Ainekset line: 95", false, loytyneet.get(0) == a3); 
    loytyneet = ainekset.annaAinekset(1); 
    assertEquals("From: Ainekset line: 97", 1, loytyneet.size()); 
    assertEquals("From: Ainekset line: 98", true, loytyneet.get(0) == a3); 
  } // Generated by ComTest END
}