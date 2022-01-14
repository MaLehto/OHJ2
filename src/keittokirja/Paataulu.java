package keittokirja;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import fi.jyu.mit.ohj2.WildChars;


/**
 * @author Matti
 * @version Mar 10, 2021
 *
 */
public class Paataulu {
    
    private Reseptit reseptit = new Reseptit();
    private Ainekset ainekset = new Ainekset();
    private Valmistusohjeet ohjeet = new Valmistusohjeet();
    
    /**
     * Lisätään päätauluun uusi resepti
     * @param resepti Lisättävä resepti
     * @throws SailoException Jos lisäystä ei voida tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Paataulu paataulu = new Paataulu();
     * Resepti r1 = new Resepti();
     * Resepti r2 = new Resepti();
     * r1.rekisteroi();
     * r2.rekisteroi();
     * paataulu.getResepteja() === 0;
     * paataulu.lisaa(r1);
     * paataulu.lisaa(r2);
     * paataulu.getResepteja() === 2;
     * paataulu.annaResepti(0) === r1;
     * paataulu.annaResepti(1) === r2;
     * </pre>
     */
    public void lisaa(Resepti resepti) throws SailoException {
        reseptit.lisaa(resepti);
    }
    
    /**
     * Lisätään päätauluun uusi valmistusohje
     * @param ohje lisättävä ohje
     * @example
     */
    public void lisaa(Valmistusohje ohje) {
        ohjeet.lisaa(ohje);
    }
    
    
    /**
     * Poistaa päätaulusta ohjeen
     * @param ohje poistettava ohje
     */
    public void poista(Valmistusohje ohje) {
        ohjeet.poista(ohje);
    }
    
    
    /**
     * Poistaa aineksen
     * @param aines poistettava
     */
    public void poista(Aines aines) {
        ainekset.poista(aines);
    }
    
    /**
     * Lisätään uusi aines päätauluun
     * @param aines lisättävä aines
     */
    public void lisaa(Aines aines) {
        ainekset.lisaa(aines);
    }
    
    /**
     * Palauttaa päätaulun jäsenten lukumäärän
     * @return lukumäärä
     */
    public int getResepteja() {
        return reseptit.getLkm();
    }
    
    
    /**
     * Palauttaa reseptit
     * @return reseptit
     */
    public Reseptit getReseptit() {
        return reseptit;
    }
    
    /**
     * Palauttaa i:n reseptin
     * @param i indeksi josta haetaan
     * @return resepti indeksissä i
     */
    public Resepti annaResepti(int i) {
        return reseptit.anna(i);
    }
   
     
    /**
     * Palauttaa i:n valmistusohjeen
     * @param resepti indeksi josta haetaan
     * @return ohje indeksissä i
     * <pre name="test">
     * #import java.util.*;
     * Paataulu paataulu = new Paataulu();
     * Resepti r1 = new Resepti();
     * Resepti r2 = new Resepti();
     * r1.rekisteroi();
     * r2.rekisteroi();
     * int id1 = r1.getTunnusNro();
     * int id2 = r2.getTunnusNro();
     * Valmistusohje o1 = new Valmistusohje();
     * Valmistusohje o2 = new Valmistusohje();
     * o1.taytaTiedolla(id1);
     * o2.taytaTiedolla(id2);
     * paataulu.lisaa(o1);
     * paataulu.lisaa(o2);
     * 
     * List<Valmistusohje> loytyneet;
     * loytyneet = paataulu.annaValmistusohjeet(r1);
     * loytyneet.size() === 1;
     * loytyneet = paataulu.annaValmistusohjeet(r2);
     * loytyneet.size() === 1;
     * loytyneet.get(0) == o2 === true;
     * loytyneet.get(0) == o1 === false;
     * </pre>
   
     */
    public List<Valmistusohje> annaValmistusohjeet(Resepti resepti) {
        return ohjeet.annaValmistusohjeet(resepti.getTunnusNro());
    }
    
    
    /**
     * Palauttaa reseptit
     * @return reseptit
     */
    public List<Resepti> annaReseptit() {
        return reseptit.annaReseptit();
    }
    
    /**
     * Palauttaa reseptin tunnusnumeron mukaisen jäsenen
     * @param reseptiKohdalla resepti josta haetaan
     * @return aines indeksissä resepti
     * @example
     * <pre name="test">
     * #import java.util.*;
     * Paataulu paataulu = new Paataulu();
     * Resepti r1 = new Resepti();
     * Resepti r2 = new Resepti();
     * r1.rekisteroi();
     * r2.rekisteroi();
     * int id1 = r1.getTunnusNro();
     * int id2 = r2.getTunnusNro();
     * Aines o1 = new Aines();
     * Aines o2 = new Aines();
     * o1.taytaTiedolla(id1);
     * o2.taytaTiedolla(id2);
     * paataulu.lisaa(o1);
     * paataulu.lisaa(o2);
     * 
     * List<Aines> loytyneet;
     * loytyneet = paataulu.annaAinekset(r1);
     * loytyneet.size() === 1;
     * loytyneet = paataulu.annaAinekset(r2);
     * loytyneet.size() === 1;
     * loytyneet.get(0) == o2 === true;
     * loytyneet.get(0) == o1 === false;
     * </pre>
     */
    public List<Aines> annaAinekset (Resepti reseptiKohdalla) {
        return ainekset.annaAinekset(reseptiKohdalla.getTunnusNro());
    }
    
    
    /**
     * Lukee päätaulun tiedot tiedostosta
     * @param nimi jota käytetään lukemisessa
     * @throws SailoException jos virhe
     */
    public void lueTiedostosta(String nimi) throws SailoException{
        reseptit = new Reseptit();
        ainekset = new Ainekset();
        ohjeet = new Valmistusohjeet();
        
        setTiedosto(nimi);
        reseptit.lueTiedostosta();
        ainekset.lueTiedostosta();
        ohjeet.lueTiedostosta();
    }
    
    
    /**
     * Tallentaa paataulun tiedot tiedostoon
     * @throws SailoException jos ongelma tallentamisessa
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            reseptit.tallenna();
        } catch (SailoException e) {
            virhe = e.getMessage();
        }
        try {
            ainekset.tallenna();
        } catch (SailoException e) {
            virhe += e.getMessage();
        }
        try {
            ohjeet.tallenna();
        } catch (SailoException e) {
            virhe += e.getMessage();
        }
        
        if (!"".equals(virhe)) throw new SailoException(virhe);
    }
    
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if (!nimi.isEmpty()) hakemistonNimi = nimi + "/";
        reseptit.setTiedostonPerusNimi(hakemistonNimi + "maa");
        ainekset.setTiedostonPerusNimi(hakemistonNimi + "ainekset");
        ohjeet.setTiedostonPerusNimi(hakemistonNimi + "ohjeet");
    }
    
    
    /**
     * Poistaa reseptin tiedot
     * @param resepti poistettava resepti
     * @return montako poistettiin
     */
    public int poista(Resepti resepti) {
        if (resepti == null) return 0;
        int pal = reseptit.poista(resepti.getTunnusNro());
        ainekset.poistaReseptin(resepti.getTunnusNro());
        ohjeet.poistaReseptin(resepti.getTunnusNro());
        return pal;
    }
    
    
    /**
     * testataan päätaulua
     * @param args ei käytössä
     */ /*
    public static void main(String args[]) {
        Paataulu paataulu = new Paataulu();
        
        try {
            paataulu.lueTiedostosta("resepteja");
            
            Resepti r1 = new Resepti();
            Resepti r2 = new Resepti();
            r1.rekisteroi();
            r2.rekisteroi();
            r1.taytaTiedoilla();
            r2.taytaTiedoilla();
            
            Valmistusohje o1 = new Valmistusohje();
            o1.rekisteroi();
            o1.taytaTiedolla();
            
            Aines a1 = new Aines();
            a1.rekisteroi();
            a1.taytaTiedolla(2);
            Aines a2 = new Aines();
            a2.rekisteroi();
            a2.taytaTiedolla(1);
            
            paataulu.lisaa(o1);
            paataulu.lisaa(r1);
            paataulu.lisaa(r2);
            paataulu.lisaa(a1);
            paataulu.lisaa(a2);
            
            for (int i = 0; i < paataulu.getResepteja(); i++) {
                Resepti resepti = paataulu.annaResepti(i);
                System.out.println("Resepti paikassa " + i);
                resepti.tulosta(System.out);
                System.out.println("\n");
            }
            
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
        
        try {
            paataulu.tallenna();
        } catch (SailoException e) {
            e.printStackTrace();
        }
    } */

    /**
     * Etsii ehtoa vastaavat
     * @param ehto millä etsitään
     * @param valittu mikä etsittävä valittu listasta
     * @return hakua vastaavat
     */
    public List<Resepti> etsi(String ehto, String valittu) {
        return reseptit.etsi(ehto, valittu);
        
    }

    /**
     * Palauttaa Reseptit joissa tietty aines
     * @param text aines jota halutaan
     * @return reseptejä
     */
    public List<Resepti> etsiAinekset(String text) {
        List<Resepti> kaikkiReseptit = reseptit.annaReseptit();
        List<Resepti> loydetyt = new ArrayList<Resepti>();
        
        for (Resepti r : kaikkiReseptit) {
            if (r == null) continue;
            List<Aines> aineksia = annaAinekset(r);
            
            int maara = 0;
            for (Aines a : aineksia) {
                if (WildChars.onkoSamat(a.getNimi(), text)) {
                    if (maara > 0) continue;
                    maara++;
                    loydetyt.add(r);
                }
            } 
        }
        return loydetyt;
        //WildChars.onkoSamat(r.getNimi(), ehto)
    } 
}
