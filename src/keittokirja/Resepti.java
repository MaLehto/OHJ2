package keittokirja;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.Tietue;

import static kanta.Random.*;

/**
 * @author Matti
 * @version Mar 10, 2021
 * 
 */
public class Resepti implements Tietue{
    private int tunnusNro;
    private String nimi = "";
    private int ainesosaTied;
    private int arvostelu = 1;
    private String kategoria = "";
    
    private static int seuraavaNro = 1;
    
    /**
     * Antaa reseptille tunnusnumeron
     * @return tunnusnumero
     * @example
     * <pre name="test">
     * Resepti r1 = new Resepti();
     * r1.getTunnusNro() === 0;
     * r1.rekisteroi();
     * Resepti r2 = new Resepti();
     * r2.rekisteroi();
     * int n1 = r1.getTunnusNro();
     * int n2 = r2.getTunnusNro();
     * n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    /**
     * tulostetaan henkilön tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("Nimi: " + nimi);
        out.println("Kategoria: " + kategoria);
        out.println("Arvostelu: " + arvostelu + "/5");
    }
    
    /**
     * tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Täyttää reseptin tiedoilla
     */
    public void taytaTiedoilla() {
        nimi = "Resepti " + rand(1, 100);
        ainesosaTied = rand(1, 10);
        arvostelu = rand(1, 5);
        kategoria = "ruoka";
                
    }
    
    
    /**
     * Selvittää reseptin tiedot merkkijonosta joka eroteltu | merkillä
     * @param rivi josta tiedot otetaan
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        setAinesosaTied(Mjonot.erota(sb, '|', getTunnusNro()));
        setArvostelu(Mjonot.erota(sb, '|', getTunnusNro()));
        kategoria = Mjonot.erota(sb, '|', kategoria);
        
    }
    
    
    
    /**
     * toString metodi tallentamisen oikeamuotoisuutta varten
     */
    @Override
    public String toString() {
        return tunnusNro + "|" + nimi + "|" + ainesosaTied + "|" 
                + arvostelu + "|" + kategoria;
    }
    
    
    /**
     * testataan reseptiä
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Resepti r1 = new Resepti();
        Resepti r2 = new Resepti();
        r1.rekisteroi();
        r1.taytaTiedoilla();
        r1.tulosta(System.out);
        System.out.println("\n");
        r2.rekisteroi();
        r2.taytaTiedoilla();
        r2.tulosta(System.out);
        
    }
    
    
    /**
     * Asetetaan arvostelu
     * @param num asetettava arvostelu
     */
    public void setArvostelu(int num) {
        arvostelu = num;
    }
    
   
    /**
     * Asetetaan ainesosatiedosto
     * @param num asetettava numero
     */
    public void setAinesosaTied(int num) {
        ainesosaTied = num;
    }
    

    /**
     * Palautetaan olion tunnusnumero
     * @return tunnusnumero
     */
    @Override
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * Asettaa tunnusnumeron ja varmistaa että seuraava numero on suurempi kuin
     * suurin
     * @param num asetettava numero 
     */
    public void setTunnusNro(int num) {
        tunnusNro = num;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }

    /**
     * palauttaa olion nimen
     * @return olion nimi
     */
    @Override
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * Palauttaa olion kategorian
     * @return kategoria
     */
    public String getKategoria() {
        return kategoria;
    }

    
    /**
     * Asetetaan reseptin nimi
     * @param s nimi string muodossa
     */
    @Override
    public void setTeksti(String s) {
        nimi = s;
        
    }
    
    @Override
    public void setKategoria(String s) {
        kategoria = s;
    }
    

    @Override
    public int getKenttia() {
        return 5;
    }

    
    /**
     * @return eka kenttä (nimi)
     */
    public int ekaKentta() {
        return 1;
    }

    /**
     * @return arvostelu "tähdissä"
     */
    public int getArvostelu() {
        if (arvostelu == 0) return 1;
        return arvostelu;
    }

}
