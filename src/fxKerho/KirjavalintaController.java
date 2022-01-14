package fxKerho;

import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @author Matti
 * @version Feb 11, 2021
 * Kirjavalinnan käsittelyn controlleri
 */
public class KirjavalintaController implements ModalControllerInterface<String>{
    @FXML Label YlinLabel;
    @FXML private ListChooser<String> listValitut;
    
    @FXML void handleValinta() {
        nimi = listValitut.getSelectedText();
        ModalController.closeStage(YlinLabel);
    }
    
    private String nimi = null;
    
    /**
     * Näyttää tulostusalueessa tekstin
     * @param modalityStage mille modaalisia
     * @param oletus mikä nimi oletuksena
     * @return kontrolleri jolta voidaan pyytää lisää tietoa
     */
    public static String nayta(Stage modalityStage, String oletus) {
        return ModalController.showModal(KirjavalintaController.class.getResource("Kirjavalinta.fxml"), "valinta", modalityStage, oletus);
    }
    
    
    @Override
    public String getResult() {
        return nimi;
    }

    @Override
    public void handleShown() {
        listValitut.requestFocus(); 
        
    }

    @Override
    public void setDefault(String oletus) {
        nimi = oletus;
        
    }
}
