package fxKerho;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import keittokirja.Paataulu;
import keittokirja.Valmistusohje;

/**
 * @author Matti
 * @version Apr 11, 2021
 *
 */
public class OhjeIkkunaController implements ModalControllerInterface<Valmistusohje>, Initializable{

    @FXML TextArea ohjeArea;
    
    private Paataulu paataulu;
    private Valmistusohje ohjeKohdalla;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("testi");
        //asetaOhje();
        //List<Valmistusohje> ohjeet = paataulu.annaValmistusohjeet(reseptiKohdalla);
        
    }
    
    
    /**
     * Asettaa Reseptin ohjeen sille tarkoitetttuun kenttään
     */
    public void asetaOhje() {
      ohjeArea.clear();
      String ohje = ohjeKohdalla.getOhje();
      ohjeArea.setText(ohje);
        
    }
    
    
    /**
     * Asettaa päätaulun
     * @param pt päätauluolio
     */
    public void setPaataulu(Paataulu pt) {
        this.paataulu = pt;
    }
    
    
    /**
     * @param modalityStage jo
     * @param oletus j
     * @param paataulu h
     * @return j
     */
    public static Valmistusohje mikaResepti(Stage modalityStage, Valmistusohje oletus, Paataulu paataulu) {
        return ModalController.<Valmistusohje, OhjeIkkunaController>showModal(
                OhjeIkkunaController.class.getResource("Reseptiikkuna.fxml"),
                "Aineksien ikkuna", modalityStage, oletus,
                ctrl -> ctrl.setPaataulu(paataulu));
    }


    @Override
    public Valmistusohje getResult() {
        return ohjeKohdalla;
    }


    @Override
    public void handleShown() {
        ohjeArea.requestFocus();
        
    }


    @Override
    public void setDefault(Valmistusohje oletus) {
        ohjeKohdalla = oletus;
        asetaOhje();
        
    }


}
