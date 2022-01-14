/**
 * 
 */
package keittokirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * @author Matti
 * @version Mar 12, 2021
 * - Pitää yllä valmistusohjeita, osaa lisätä ja 
 * - poistaa valmistusohjeen
 * - Osaa lukea ja kirjoittaa valmistuohjeiden tiedostoon                                       |                   |
 * - Osaa etsiä ja lajitella
 */
public class Valmistusohjeet implements Iterable<Valmistusohje>{
    
    private String tiedostonPerusNimi = "";
    
    private final Collection<Valmistusohje> alkiot = new ArrayList<Valmistusohje>();
    
    /**
     * Iteraattori valmistusohjeiden läpikäymiseen
     * @return valmistusohjeiteraattori;
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * Valmistusohjeet ohjeet = new Valmistusohjeet();
     * Valmistusohje o1 = new Valmistusohje();
     * Valmistusohje o2 = new Valmistusohje();
     * Valmistusohje o3 = new Valmistusohje();
     * o1.taytaTiedolla(2);
     * o2.taytaTiedolla(2);
     * o3.taytaTiedolla(1);
     * ohjeet.lisaa(o1);
     * ohjeet.lisaa(o2);
     * ohjeet.lisaa(o3);
     * 
     * Iterator<Valmistusohje> i = ohjeet.iterator();
     * i.next() === o1;
     * i.next() === o2;
     * i.next() === o3;
     * i.next() === o1; #THROWS NoSuchElementException
     * 
     * int n = 0;
     * int vnrot[] = {2, 2, 1};
     * 
     * for (Valmistusohje ohj : ohjeet) {
     *      ohj.getTunnusNro() === vnrot[n];
     *      n++;
     *  }
     *  
     * n === 3; 
     * </pre>
     */
    @Override
    public Iterator<Valmistusohje> iterator() {
        return alkiot.iterator();
    }
    
    /**
     * Lisää uuden valmistusohjeen tietorakenteeseen
     * @param valmistusohje lisättävä valmistusohje
     */
    public void lisaa(Valmistusohje valmistusohje) {
        alkiot.add(valmistusohje);
    }
    
    
    /**
     * Poistaa valmistusohjeen
     * @param valmistusohje poistettava
     */
    public void poista(Valmistusohje valmistusohje) {
        alkiot.remove(valmistusohje);
    }
    
    /**
     * Haetaan reseptin valmistusohje
     * @param tunnusNro jolle valmistusohjetta etsitään
     * @return tietorakenne jossa viitteet valmistusohjeisiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * Valmistusohjeet ohjeet = new Valmistusohjeet();
     * Valmistusohje o1 = new Valmistusohje();
     * Valmistusohje o2 = new Valmistusohje();
     * Valmistusohje o3 = new Valmistusohje();
     * o1.taytaTiedolla(2);
     * o2.taytaTiedolla(2);
     * o3.taytaTiedolla(1);
     * ohjeet.lisaa(o1);
     * ohjeet.lisaa(o2);
     * ohjeet.lisaa(o3);
     * 
     * List<Valmistusohje> loytyneet;
     * loytyneet = ohjeet.annaValmistusohjeet(3);
     * loytyneet.size() === 0;
     * loytyneet = ohjeet.annaValmistusohjeet(2);
     * loytyneet.size() === 2;
     * loytyneet.get(0) == o1 === true;
     * loytyneet.get(0) == o2 === false;
     * loytyneet = ohjeet.annaValmistusohjeet(1);
     * loytyneet.size() === 1;
     * loytyneet.get(0) == o3 === true;
     * </pre>
     */
    public List<Valmistusohje> annaValmistusohjeet(int tunnusNro) {
        List<Valmistusohje> loydetyt = new ArrayList<Valmistusohje>();
        for (Valmistusohje v : alkiot) {
            if (v.getTunnusNro() == tunnusNro) loydetyt.add(v);
        }
        return loydetyt;
    }
    
    
    /**
     * Tallentaa valmistusohjeet tiedostoon
     * @throws SailoException jos epäonnistuu
     */
    public void tallenna() throws SailoException {
        File tiedosto = new File(getTiedostonNimi());
        
        try (PrintStream fo = new PrintStream(new FileOutputStream(tiedosto))) {
            for (Valmistusohje o : this) {
                fo.println(o.toString());
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("tiedosto " + tiedosto + " ei aukea");
        }
    }
    
    
    /**
     * Lukee valmistusohjeet tiedostosta
     * @param tied josta luetaan
     * @throws SailoException jos virhe luettaessa
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        File tiedosto = new File(getTiedostonNimi());
        
        try (Scanner fi = new Scanner(new FileInputStream(tiedosto))) {
            while (fi.hasNext()) {
                String s = "";
                s = fi.nextLine();
                Valmistusohje ohje = new Valmistusohje();
                ohje.parse(s);
                lisaa(ohje);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Luetaan aiemmin annetun nimisestä tiedostosta
     * @throws SailoException jos poikkeus lukiessa
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    
    /**
     * Palauttaa tiedoston nimen jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    /**
     * Asetta tiedoston perusnimen
     * @param nimi tallennustiedoston nimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }
    
    
    /**
     * palauttaa tallenuksessa käytettävän tiedostonnimen
     * @return tiedostonnimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }
    
    
    /**
     * Poistaa kaikki tietyn reseptin valmistusohjeet
     * @param tunnus poistettava resepti
     * @return montako poistettiin
     */
    public int poistaReseptin(int tunnus) {
        int n = 0;
        for (Iterator<Valmistusohje> it = alkiot.iterator(); it.hasNext(); ) {
            Valmistusohje o = it.next();
            if (o.getTunnusNro() == tunnus) {
                it.remove();
                n++;
            }
        }
        return n;
    }
    

    /**
     * Testataan valmistusohjeita
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Valmistusohjeet ohjeet = new Valmistusohjeet();
        Valmistusohje o1 = new Valmistusohje();
        o1.taytaTiedolla();
        Valmistusohje o2 = new Valmistusohje();
        o2.taytaTiedolla();
        Valmistusohje o3 = new Valmistusohje();
        o3.taytaTiedolla();
        Valmistusohje o4 = new Valmistusohje();
        o4.taytaTiedolla();
        Valmistusohje o5 = new Valmistusohje();
        o5.taytaTiedolla();
        
        ohjeet.lisaa(o1);
        ohjeet.lisaa(o2);
        ohjeet.lisaa(o3);
        ohjeet.lisaa(o4);
        ohjeet.lisaa(o5);
        
        List<Valmistusohje> ohjeet2 = ohjeet.annaValmistusohjeet(2);
        
        for (Valmistusohje v : ohjeet2) {
            System.out.println(v.getTunnusNro() + " ");
            v.tulosta(System.out);
        }
    }

    

}
