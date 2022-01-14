package fxKerho;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import keittokirja.Aines;
import keittokirja.Paataulu;
import keittokirja.Resepti;


/**
 * @author Matti
 * @version Apr 11, 2021
 *  Hoitaa Reseptiikkunan näyttämisen
 */
public class AinesIkkunaController implements ModalControllerInterface<Resepti>, Initializable {
    
    @FXML StringGrid<Aines> gridAinekset;


    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }

        
    private List<Aines> reseptinAinekset;
    private Resepti reseptiKohdalla;
    private Paataulu paataulu; 
       
    /**
     * Alustaa ikkunan avautuessa
     */
    public void alusta() {
        System.out.println("testi");
        if(reseptiKohdalla == null) return;
    }
    
    
    /**
     * Hakee ainekset
     */
    public void haeAinekset() {
        if (reseptiKohdalla == null) return; 
        reseptinAinekset = paataulu.annaAinekset(reseptiKohdalla);
        alustaGrid();
        naytaAinekset(reseptiKohdalla);
    }
    
    
    /**
     * Nayttaa ainekset
     * @param resepti resepti jonka ainekset näytetään
     */
    public void naytaAinekset(Resepti resepti) {
        if (resepti == null) return;
        if (reseptinAinekset.size() == 0) return;
        for (Aines a : reseptinAinekset) naytaAines(a);
    }
    
    
    /**
     * Asettaa aineksen tiedot omalle kentälle
     * @param aines jota käytetään
     */
    public void naytaAines(Aines aines) {
        int kenttia = aines.getKenttia();
        String[] rivi = new String[kenttia - aines.ekaKentta()];
        for (int i = 0, k=aines.ekaKentta(); k < kenttia; i++, k++)
            rivi[i] = aines.anna(k);
        gridAinekset.add(aines, rivi);
    }
    
    
    /**
     * Alustaa string gridin halutunlaiseksi
     */
    public void alustaGrid() {
        if (reseptinAinekset.size() == 0) return;
        Aines a = reseptinAinekset.get(0);
        int eka = a.ekaKentta();
        int lkm = a.getKenttia();
        String[] headings = new String[lkm - eka];
        for (int i=0, k=eka; k<lkm; i++, k++) headings[i] = a.getKysymys(k);
        gridAinekset.initTable(headings);
        gridAinekset.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        gridAinekset.setEditable(false);
        gridAinekset.setPlaceholder(new Label("Eipä ole mitään"));
    }
            
    
    /**
     * Asettaa päätaulun
     * @param pt päätauluolio
     */
    public void setPaataulu(Paataulu pt) {
        this.paataulu = pt;
        haeAinekset();
    }


    @Override
    public Resepti getResult() {
        return reseptiKohdalla;
    }

    @Override
    public void handleShown() {
        //
        
    }

    @Override
    public void setDefault(Resepti oletus) {
        reseptiKohdalla = oletus;
    }
     
    
    /**
     * Luodaan uuden reseptin tekemisdialogi ja palautetaan tietue
     * @param modalityStage mille modaalisia
     * @param oletus mitä näytetään oletuksena
     * @param paataulu Päätaulu joka käytössä
     * @return jos peruuta
     */
    public static Resepti mikaResepti(Stage modalityStage, Resepti oletus, Paataulu paataulu) {
        return ModalController.<Resepti, AinesIkkunaController>showModal(
                AinesIkkunaController.class.getResource("Aineksetikkuna.fxml"),
                "Aineksien ikkuna", modalityStage, oletus,
                ctrl -> ctrl.setPaataulu(paataulu));
    }
    

}
