/**
 * 
 */
package keittokirja;

import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Matti
 * @version Mar 12, 2021
 * - Tietää valmistusohjeiden kentät
 * - Osaa tarkistaa oikeellisuuden
 * - Osaa muuttaa merkkijonon jossa erottajia tiedoksi
 * - Osaa antaa merkkijonona i:n kentän tiedot
 * - Osaa laittaa merkkijonon i:neksi kentäksi
 *
 */
public class Valmistusohje {
    private int tunnusNro;
    private String valmistusohje = "";
    
    private static int seuraavaNro = 1;
    
    /**
     * Alustetaan reseptin valmistusohje
     */
    public void taytaTiedolla() {
        tunnusNro = rekisteroi();
        valmistusohje = "Valmistusohje tulee tähän";
    }
    
    
    /**
     * Alustetaan valmistuohje reseptin id tiedolla
     * @param numero reseptin id
     */
    public void taytaTiedolla(int numero) {
        tunnusNro = numero;
        valmistusohje = "valmistusohje tähän";
        
    }
    
    /**
     * Tulostetaan valmistusohjeen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(valmistusohje);
    }
    
    /**
     * Antaa valmistusohjeelle rekisterinumeron
     * @return rekisterinumero
     * @example
     * <pre name="test">
     * Valmistusohje o1 = new Valmistusohje();
     * o1.getTunnusNro() === 0;
     * o1.rekisteroi();
     * Valmistusohje o2 = new Valmistusohje();
     * o2.rekisteroi();
     * int n1 = o1.getTunnusNro();
     * int n2 = o2.getTunnusNro();
     * n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    /**
     * Palauttaa valmistusohjeen tunnusnumeron
     * @return tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * Palauttaa valmistusohjeen
     * @return ohje
     */
    public String getOhje() {
        return valmistusohje;
    }
    
    
    /**
     * Selvitää valmistusohjeen tiedot merkkijonosta
     * @param jono merkkijono josta selvitetään
     */
    public void parse(String jono) {
        StringBuilder sb = new StringBuilder(jono);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        valmistusohje = Mjonot.erota(sb, '|', valmistusohje);
        
    }
    
    
    /**
     * Asetetaan reseptin tunnusnumero
     * @param num tunnusnumero
     */
    public void setTunnusNro(int num) {
        tunnusNro = num;
    }
    
    /**
     * oikeanmuotoista tallentamista varten
     */
    @Override
    public String toString() {
        return tunnusNro + "|" + valmistusohje;
    }
    
    
    /**
     * Asetetaan valmistusohje
     * @param s ohje string muodossa
     */
    public void setTeksti(String s) {
        valmistusohje = s;
    }
    
    /**
     * testataan valmistusohjetta
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Valmistusohje ohje = new Valmistusohje();
        ohje.taytaTiedolla();
        ohje.tulosta(System.out);
        
        Valmistusohje ohje2 = new Valmistusohje();
        ohje2.taytaTiedolla();
        ohje2.tulosta(System.out);
    
    }



}
