package keittokirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;


/**
 * @author Matti
 * @version Mar 10, 2021
 */
public class Reseptit {
    private static int MAX_RESEPTEJA = 2;
    private int lkm = 0;
    private Resepti alkiot[] = new Resepti[MAX_RESEPTEJA];
    private String tiedostonNimi = "resepteja";
    
    /**
     * Lisää uuden reseptin tietorakenteeseen 
     * @param resepti Lisättävän reseptin viite
     * @throws SailoException Jos rakenne on täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Reseptit reseptit = new Reseptit();
     * Resepti r1 = new Resepti();
     * Resepti r2 = new Resepti();
     * reseptit.getLkm() === 0;
     * reseptit.lisaa(r1);
     * reseptit.lisaa(r2);
     * reseptit.getLkm() === 2;
     * reseptit.anna(0) === r1;
     * reseptit.anna(1) === r2;
     * </pre>
     */
    public void lisaa(Resepti resepti) throws SailoException {
        if (lkm >= alkiot.length) {
            Resepti apu[] = new Resepti[MAX_RESEPTEJA];
            for (int i = 0; i < alkiot.length; i++) {
                apu[i] = alkiot[i];
            }
            MAX_RESEPTEJA += 2;
            this.alkiot = new Resepti[MAX_RESEPTEJA];
            for (int i = 0; i < apu.length; i++) {
                alkiot[i] = apu[i];
            }
        }
            
        alkiot[lkm] = resepti;
        lkm++;
        //throw new SailoException("Liikaa alkioita"); Tämä on tässä ihan harjoituksen vuoksi
    }
    
    
    /**
     * Palauttaa reseptien lukumäärän
     * @return lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Tallentaa reseptit tiedostoon muodossa
     * tunnusNro|Nimi|ainesosaTied|arvostelu|kategoria
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna() throws SailoException {
        // if (muutettu) return;
        File tiedosto = new File(getTiedostonNimi());
        try (PrintStream fo = new PrintStream(new FileOutputStream(tiedosto))) {
            for (int i = 0; i < getLkm(); i++) {
                Resepti resepti = anna(i);
                fo.println(resepti.toString());
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("tiedosto " + tiedosto + " ei aukea");
        }
        
    }
    
    
    /**
     * Lukee reseptit tiedostosta
     * @param tiedNimi tiedosto josta luetaan
     * @throws SailoException jos heittää virheen
     */
    public void lueTiedostosta(String tiedNimi) throws SailoException {
        setTiedostonPerusNimi(tiedNimi);
        File tiedosto = new File(getTiedostonNimi());
        
        try (Scanner fi = new Scanner(new FileInputStream(tiedosto))) {
            while (fi.hasNext()) {
                String s = "";
                s = fi.nextLine();
                Resepti resepti = new Resepti();
                resepti.parse(s);
                lisaa(resepti);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
    }
    
    
    /**
     * Luetaan aiemmin annetun nimisestä tiedostosta
     * @throws SailoException jos poikkeus
     */
    public void lueTiedostosta() throws SailoException{
        lueTiedostosta(tiedostonNimi);
    }
    
    
    /**
     * Palauttaa tiedoston nimen jota käytetään tallennuksessa
     * @return tiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonNimi + ".dat";
    }
    
    
    /**
     * ASettaa tiedoston perusnimen
     * @param nimi perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonNimi = nimi;
    }
    
    
    /**
     * Palauttaa viiteen i jäseneen
     * @param i reseptin indeksi jonka halutaan
     * @return viite reseptiin
     * @throws IndexOutOfBoundsException jos i ei sallitulla alueella
     */
    public Resepti anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i) 
            throw new IndexOutOfBoundsException("laiton indeksi " + i);
        return alkiot[i];
    }
    
    
    /**
     * Poistetaan tunnukseltaan id:tä vastaava resepti
     * @param id poistettava resepti
     * @return montako poistettiin
     */
    public int poista (int id) {
        int ind = etsiId(id);
        if (ind < 0) return 0;
        lkm--;
        for (int i = ind; i < lkm; i++) alkiot[i] = alkiot[i+1];
        alkiot[lkm] = null;
        return 1;
    }
    
    
    /**
     * Etsitään resepti jonka tunnus vastaa id:tä
     * @param id etsittävä tunnus
     * @return mistä paikasta löytyy
     */
    public int etsiId(int id) {
        for (int i = 0; i < lkm; i++) {
            if (id == alkiot[i].getTunnusNro()) return i;
        }
        return -1;
    }
    
    /**
     * testataan reseptejä
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Reseptit reseptit = new Reseptit();
        
        try {
            reseptit.lueTiedostosta("resepteja");
        } catch (SailoException e) {
            e.printStackTrace();
        }
        
        Resepti r1 = new Resepti();
        Resepti r2 = new Resepti();
        r1.rekisteroi();
        r1.taytaTiedoilla();
        r2.rekisteroi();
        r2.taytaTiedoilla();
        
        try {
            reseptit.lisaa(r1);
            reseptit.lisaa(r2);

            for (int i = 0; i < reseptit.getLkm(); i++) {
                Resepti resepti = reseptit.anna(i);
                System.out.println("Jäsen nro: " + i);
                resepti.tulosta(System.out);
                System.out.println("\n");
            } 
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
        
        try {
            reseptit.tallenna();
        } catch (SailoException e) {
            e.printStackTrace();
        }

    }


    /**
     * Antaa reseptit
     * @return reseptit
     */
    public List<Resepti> annaReseptit() {
        List<Resepti> reseptit = new ArrayList<Resepti>();
        for (Resepti r : alkiot) {
            reseptit.add(r);
        }
        return reseptit;
    }


    /**
     * @param ehto millä etsitään
     * @param valittu mikä valittu listasta
     * @return lista jotka täyttävät ehdon
     */
    public List<Resepti> etsi(String ehto, String valittu) {
        List<Resepti> loytyneet = new ArrayList<Resepti>();
        
        if (valittu.equals("nimi")) {
            for (Resepti r : alkiot) {
                if (r == null) continue;
                if (WildChars.onkoSamat(r.getNimi(), ehto)) loytyneet.add(r);
            }
        }
        if (valittu.equals("kategoria")) {
            for (Resepti r : alkiot) {
                if (r == null) continue;
                if (WildChars.onkoSamat(r.getKategoria(), ehto)) loytyneet.add(r);
            }
        }
        //Collections.sort(loytyneet); //TODO: selvitä miksi ei toimi
        return loytyneet;
    }

}
