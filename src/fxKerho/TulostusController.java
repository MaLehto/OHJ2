package fxKerho;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;

/**
 * @author Matti
 * @version Feb 11, 2021
 * Controlleri joka hoitaa tulostuksen käsittelyn
 */
public class TulostusController implements ModalControllerInterface<String>{
    @FXML TextArea tulostusAlue;
    
    @FXML private void handlePeruuta() {
        ModalController.closeStage(tulostusAlue);
    }
    
    @FXML private void handleTulosta() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(null)) {
            WebEngine webEngine = new WebEngine();
            webEngine.loadContent("<pre>" + tulostusAlue.getText() + "</pre>");
            webEngine.print(job);
            job.endJob();
        }
    }
    
    /**
     * Näyttää tulostusalueessa tekstin
     * @param tulostus tulostettava teksti
     * @return kontrolleri jolta voidaan pyytää lisää tietoa
     */
    public static TulostusController tulosta(String tulostus) {
        TulostusController tulostusCtrl = ModalController.showModeless(TulostusController.class.getResource("Tulosta.fxml"), "tulostus", tulostus);
        return tulostusCtrl;
    }

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void handleShown() {
        //
        
    }

    @Override
    public void setDefault(String oletus) {
        tulostusAlue.setText(oletus);      
        
    }
    

    /**
     * @return alue johon tulostetaan
     */
    public TextArea getTextArea() {
        return tulostusAlue;
    }
    
    
}
