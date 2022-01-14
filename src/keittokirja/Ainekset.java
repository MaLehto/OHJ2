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
 * - Pitää yllä aineksia, osaa lisätä ja poistaa
 * - Osaa kirjoittaa ja lukea aineksien tiedostoa
 * - Osaa etsiä ja lajitella
 */
public class Ainekset implements Iterable<Aines>{
    private String tiedostonNimi = "";
    
    private final Collection<Aines> alkiot = new ArrayList<Aines>();
    
    /**
     * iteraattoiri aineksien läpikäyntiin
     * @return ainesiteraattori
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Ainekset ainekset = new Ainekset();
     * Aines a1 = new Aines();
     * Aines a2 = new Aines();
     * Aines a3 = new Aines();
     * a1.taytaTiedolla(2);
     * a2.taytaTiedolla(2);
     * a3.taytaTiedolla(1);
     * ainekset.lisaa(a1);
     * ainekset.lisaa(a2);
     * ainekset.lisaa(a3);
     * 
     * Iterator<Aines> i2=ainekset.iterator();
     * i2.next() === a1;
     * i2.next() === a2;
     * i2.next() === a3;
     * i2.next() === a1; #THROWS NoSuchElementException
     * 
     * int n = 0;
     * int anrot[] = {2, 2, 1};
     * for (Aines a : ainekset) {
     *      a.getRid() === anrot[n];
     *      n++;
     *  }
     * n === 3; 
     *  
     * </pre>
     */
    @Override
    public Iterator<Aines> iterator() {
        return alkiot.iterator();
    }
    
    /**
     * Lisää uuden aineksen tietorakenteeseen
     * @param aines lisättävä aines
     */
    public void lisaa(Aines aines) {
        alkiot.add(aines);
    }
    
    /**
     * haetaan kaikki reseptin ainekset
     * @param tunnusNro tunnusnumero jolle aineksia haetaan
     * @return tietorakenne jossa viiteet aineksiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     * Ainekset ainekset = new Ainekset();
     * Aines a1 = new Aines();
     * Aines a2 = new Aines();
     * Aines a3 = new Aines();
     * a1.taytaTiedolla(2);
     * a2.taytaTiedolla(2);
     * a3.taytaTiedolla(1);
     * ainekset.lisaa(a1);
     * ainekset.lisaa(a2);
     * ainekset.lisaa(a3);
     * 
     * List<Aines> loytyneet;
     * loytyneet = ainekset.annaAinekset(3);
     * loytyneet.size() === 0;
     * loytyneet = ainekset.annaAinekset(2);
     * loytyneet.size() === 2;
     * loytyneet.get(0) == a1 === true;
     * loytyneet.get(0) == a3 === false;
     * loytyneet = ainekset.annaAinekset(1);
     * loytyneet.size() === 1;
     * loytyneet.get(0) == a3 === true;
     * </pre>
     */
    public List<Aines> annaAinekset(int tunnusNro) {
        List<Aines> loydetyt = new ArrayList<Aines>();
        for (Aines a : alkiot) {
            if(a.getRid() == tunnusNro) loydetyt.add(a);
        }
        return loydetyt;
    }
    
    
    /**
     * Tallentaa ainekset tiedostoon
     * @throws SailoException jos epäonnistuu
     */
    public void tallenna() throws SailoException {
        File tiedosto = new File(getTiedostonNimi());
        
        try (PrintStream fo = new PrintStream(new FileOutputStream(tiedosto))) {
            for (Aines a : this) {
                fo.println(a.toString());
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("tiedosto " + tiedosto + " ei aukea");
        }
    }
    
    
    /**
     * Lukee ainekset tiedostosta
     * @param tied josta luetaan
     * @throws SailoException jos virhe lukemisessa
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        File tiedosto = new File(getTiedostonNimi());
        
        try (Scanner fi = new Scanner(new FileInputStream(tiedosto))) {
            while (fi.hasNext()) {
                String s = "";
                s = fi.nextLine();
                Aines aines = new Aines();
                aines.parse(s);
                lisaa(aines);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * luetaan aiemmin määritellystä tiedostosta
     * @throws SailoException jos poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    
    /**
     * Palauttaa tallennukseen käytettävän tiedostonNimen
     * @return tiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonNimi + ".dat";
    }
    
    
    /**
     * Palauttaa tiedoston nimen jota käytetään tallennuksessa
     * @return tiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonNimi;
    }
    
    
    /**
     * Asettaa tiedoston nimen
     * @param nimi tiedoston nimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonNimi = nimi;
    }
    
    
    /**
     * Poistaa kaikki tietyn reseptin ainekset
     * @param tunnus mistä reseptistä poistetaan
     * @return monta poistettiin
     */
    public int poistaReseptin(int tunnus) {
        int n = 0;
        for (Iterator<Aines> it = alkiot.iterator(); it.hasNext(); ) {
            Aines a = it.next();
            if (a.getRid() == tunnus) {
                it.remove();
                n++;
            }
        }
        return n;
    }

    /**
     * Testataan Aineksia
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Ainekset ainekset = new Ainekset();
        
        try {
            ainekset.lueTiedostosta("testi");
        } catch (SailoException e) {
            e.printStackTrace();
        }
        
        Aines a1 = new Aines();
        a1.taytaTiedolla(2);
        Aines a2 = new Aines();
        a2.taytaTiedolla(1);
        Aines a3 = new Aines();
        a3.taytaTiedolla(2);
        Aines a4 = new Aines();
        a4.taytaTiedolla(2);
        Aines a5 = new Aines();
        a5.taytaTiedolla(2);
        
        ainekset.lisaa(a1);
        ainekset.lisaa(a2);
        ainekset.lisaa(a3);
        ainekset.lisaa(a4);
        ainekset.lisaa(a5);
        
        List<Aines> ainekset2 = ainekset.annaAinekset(2); 
        
        for (Aines a : ainekset2) {
            System.out.println(a.getAid());
            System.out.println(a.getRid() + " ");
            a.tulosta(System.out);
        }
        
        try {
            ainekset.tallenna();
        } catch (SailoException e) {
            e.printStackTrace();
        }
        
    
    }

    /**
     * Poistaa aineksen
     * @param aines poistettava
     */
    public void poista(Aines aines) {
        alkiot.remove(aines);
        
    }
}
