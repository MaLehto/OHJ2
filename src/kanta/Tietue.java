package kanta;

/**
 * @author Matti
 * @version Apr 5, 2021
 *
 */
public interface Tietue {
    
    /**
     * Asettaa kentän sisällön
     * @param s merkkijono
     */
    public abstract void setTeksti(String s);
    
    
    /**
     * Palauttaa olion tunnusnumeron
     * @return Tunnusnumero
     */
    public abstract int getTunnusNro();
    
    
    @Override
    public abstract String toString();


    /**
     * Kenttien lukummärä
     * @return kenttä
     */
    public abstract int getKenttia();


    /**
     * Palauttaa nimen 
     * @return nimi
     */
    public abstract String getNimi();


    /**
     * Asettaa kategorian
     * @param s miksikä asetetaan
     */
    public abstract void setKategoria(String s);
}
