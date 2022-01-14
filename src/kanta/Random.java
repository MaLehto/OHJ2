/**
 * 
 */
package kanta;

/**
 * @author Matti
 * @version Mar 10, 2021
 *
 */
public class Random {
    
    /**
     * @param ala alaraja
     * @param yla yläraja
     * @return satunnainen numero väliltä ala, ylä
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    }
    

}
