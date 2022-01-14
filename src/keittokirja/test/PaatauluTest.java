package keittokirja.test;
// Generated by ComTest BEGIN
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import keittokirja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.03.16 12:37:17 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class PaatauluTest {


  // Generated by ComTest BEGIN
  /** 
   * testLisaa21 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa21() throws SailoException {    // Paataulu: 21
    Paataulu paataulu = new Paataulu(); 
    Resepti r1 = new Resepti(); 
    Resepti r2 = new Resepti(); 
    r1.rekisteroi(); 
    r2.rekisteroi(); 
    assertEquals("From: Paataulu line: 28", 0, paataulu.getResepteja()); 
    paataulu.lisaa(r1); 
    paataulu.lisaa(r2); 
    assertEquals("From: Paataulu line: 31", 2, paataulu.getResepteja()); 
    assertEquals("From: Paataulu line: 32", r1, paataulu.annaResepti(0)); 
    assertEquals("From: Paataulu line: 33", r2, paataulu.annaResepti(1)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaValmistusohjeet78 */
  @Test
  public void testAnnaValmistusohjeet78() {    // Paataulu: 78
    Paataulu paataulu = new Paataulu(); 
    Resepti r1 = new Resepti(); 
    Resepti r2 = new Resepti(); 
    r1.rekisteroi(); 
    r2.rekisteroi(); 
    int id1 = r1.getTunnusNro(); 
    int id2 = r2.getTunnusNro(); 
    Valmistusohje o1 = new Valmistusohje(); 
    Valmistusohje o2 = new Valmistusohje(); 
    o1.taytaTiedolla(id1); 
    o2.taytaTiedolla(id2); 
    paataulu.lisaa(o1); 
    paataulu.lisaa(o2); 
    List<Valmistusohje> loytyneet; 
    loytyneet = paataulu.annaValmistusohjeet(r1); 
    assertEquals("From: Paataulu line: 96", 1, loytyneet.size()); 
    loytyneet = paataulu.annaValmistusohjeet(r2); 
    assertEquals("From: Paataulu line: 98", 1, loytyneet.size()); 
    assertEquals("From: Paataulu line: 99", true, loytyneet.get(0) == o2); 
    assertEquals("From: Paataulu line: 100", false, loytyneet.get(0) == o1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaAinekset113 */
  @Test
  public void testAnnaAinekset113() {    // Paataulu: 113
    Paataulu paataulu = new Paataulu(); 
    Resepti r1 = new Resepti(); 
    Resepti r2 = new Resepti(); 
    r1.rekisteroi(); 
    r2.rekisteroi(); 
    int id1 = r1.getTunnusNro(); 
    int id2 = r2.getTunnusNro(); 
    Aines o1 = new Aines(); 
    Aines o2 = new Aines(); 
    o1.taytaTiedolla(id1); 
    o2.taytaTiedolla(id2); 
    paataulu.lisaa(o1); 
    paataulu.lisaa(o2); 
    List<Aines> loytyneet; 
    loytyneet = paataulu.annaAinekset(r1); 
    assertEquals("From: Paataulu line: 131", 1, loytyneet.size()); 
    loytyneet = paataulu.annaAinekset(r2); 
    assertEquals("From: Paataulu line: 133", 1, loytyneet.size()); 
    assertEquals("From: Paataulu line: 134", true, loytyneet.get(0) == o2); 
    assertEquals("From: Paataulu line: 135", false, loytyneet.get(0) == o1); 
  } // Generated by ComTest END
}