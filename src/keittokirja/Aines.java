/**
 * 
 */
package keittokirja;

import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Matti
 * @version Mar 12, 2021
 * - Tietää reseptin kentät
 * - Osaa tarkistaa oikeellisuuden
 * - Osaa muuttaa merkkijonon jossa erottajia tiedoksi
 * - Osaa antaa merkkijonona i:n kentän tiedot 
 * - Osaa laittaa merkkijonon i:neksi kentäksi
 */
public class Aines {
    private int aid; //tunnusNro;
    private int rid;
    private String ainesNimi = "";
    private int maara;
    private String mittaAsteikko = "";
    
    private static int seuraavaNro = 1;
    
    /**
     * Alustetaan aines
     */
    public Aines() {
        //
    }
    
    /**
     * Alustetaan reseptin aines
     * @param nro reseptin viitenumero
     */
    public void taytaTiedolla(int nro) {
        rid = nro;
        ainesNimi = "Nimi";
        maara = 99999;
        mittaAsteikko = "mitta-asteikko tähän";
    }
    
    /**
     * Tulostaa aineksen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(aid + " " + rid + " " + ainesNimi + " " + maara + " " + mittaAsteikko);
    }
    
    /**
     * Palautetaan aineksen id
     * @return aineksen id
     */
    public int getAid() {
        return aid;
    }
    

    /**
     * Asetetaan reseptit id
     * @param luku lisättävä rid
     */
    public void setRid(int luku) {
        rid = luku;
    }
    
    
    /**
     * Asetetaan aineksen id
     * @param luku lisättävä aid
     */
    public void setAid(int luku) {
        aid = luku;
        if (aid >= seuraavaNro) seuraavaNro = aid + 1;
    }
    
    /**
     * Palautetaan reseptin id
     * @return reseptin id
     */
    public int getRid() {
        return rid;
    }
    
    
    /**
     * Asetetaan aineksen määrä
     * @param luku kuinka paljon ainesta on
     */
    public void setMaara(int luku) {
        maara = luku;
    }
    
    
    /**
     * @param k minkä kentän sisältö halutaan
     * @return sisältö
     */
    public String anna(int k) {
        switch(k) {
        case 0 : return "" + aid;
        case 1 : return "" + rid;
        case 2 : return "" + ainesNimi;
        case 3 : return "" + maara;
        case 4 : return "" + mittaAsteikko;
        default: return "?";
        }
    }
    
    
    /**
     * @param k minkä kentän kysymys
     * @return kysymysteksti
     */
    public String getKysymys(int k) {
        switch(k) {
        case 0 : return "aid";
        case 1 : return "rid";
        case 2 : return "AinesNimi";
        case 3 : return "maara";
        case 4 : return "mittaAsteikko";
        default : return "??";
        
        }
    }
    
    
    /**
     * Antaa ainekselle rekisterinumeron
     * @return uusi rekisterinumero
     * @example
     * <pre name="test">
     *  Aines a1 = new Aines();
     *  Aines a2 = new Aines();
     *  a1.getRid() === 0;
     *  a1.getAid() === 0;
     *  a1.rekisteroi();
     *  a2.rekisteroi();
     *  int n1 = a1.getRid();
     *  int n2 = a2.getRid();
     *  n1 === n2 - n1;
     *  
     * </pre>
     */
    public int rekisteroi() {
        aid = seuraavaNro;
        seuraavaNro++;
        return aid;
    }
    
    /**
     * Tallentamisen oikeamuotoisuutta varten
     */
    @Override
    public String toString() {
        return aid + "|" + rid + "|" + ainesNimi + "|" + maara + "|" + mittaAsteikko;
        
    }
    
    /**
     * Selvittää aineksen tiedot merkkijonosta
     * @param rivi merkkijono
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setAid(Mjonot.erota(sb, '|', getAid()));
        setRid(Mjonot.erota(sb, '|', getAid()));
        ainesNimi = Mjonot.erota(sb, '|', ainesNimi);
        setMaara(Mjonot.erota(sb, '|', getAid()));
        mittaAsteikko = Mjonot.erota(sb, '|', mittaAsteikko);
    }
    
    
    /**
     * @return aineksen kenttien lkm
     */
    public int getKenttia() {
        return 5;
    }
    
    
    /**
     * @return Ekan kentän numero
     */
    public int ekaKentta() {
        return 2;
    }
    

    /**
     * Testataan Ainesta
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Aines aines = new Aines();
        Aines aines2 = new Aines();
        aines.rekisteroi();
        aines2.rekisteroi();
        aines.taytaTiedolla(2);
        aines2.taytaTiedolla(3);
        aines.tulosta(System.out);
        aines2.tulosta(System.out);
    
    }

    /**
     * Asettaa aineksen nimen
     * @param s jono joka nimeksi
     */
    public void asetaNimi(String s) {
        ainesNimi = s;
        
    }
    
    
    /**
     * Asettaa aineksen määrän
     * @param i joka määräksi
     */
    public void asetaMaara(int i) {
        maara = i;
    }
    
    
    /**
     * Asettaa mitta-asteikon
     * @param s jono joka asteikoksi
     */
    public void asetaMittaAsteikko(String s) {
        mittaAsteikko = s;
    }

    
    /**
     * Palauttaa aineksen nimen
     * @return nimi
     */
    public String getNimi() {
        return ainesNimi;
    }
}
