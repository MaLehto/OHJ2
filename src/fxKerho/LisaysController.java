package fxKerho;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kanta.Tietue;
import keittokirja.Valmistusohje;
import keittokirja.Aines;
import keittokirja.Paataulu;

/**
 * @author Matti
 * @version Feb 11, 2021
 * Controlleri joka hoitaa lisäyksen käsittelyä
 * @param <TYPE> minkä tyyppistä oliota käsitellään
 */
public class LisaysController<TYPE extends Tietue> implements ModalControllerInterface<TYPE>, Initializable{
    @FXML GridPane gridTietue;
    @FXML TextArea editOhje;
    @FXML TextField editNimi;
    @FXML TextField editKategoria;
    @FXML ScrollPane paneAinekset;

    
    
    /**
     * Alustus
     * @param url a
     * @param bundle b
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }

    
    @FXML void handlePeruuta() {
        reseptiKohdalla = null;
        ModalController.closeStage(editOhje);
    }
    
    @FXML void handleLisaa() {
        if (reseptiKohdalla.getNimi().equals("")) {
            nimiVirhe();
            return;
        }
        tallennaValmistusohje();
        tallennaAines();
        ModalController.closeStage(editOhje);
    }
    
    private TYPE reseptiKohdalla;
    private Valmistusohje palautettavaVO = new Valmistusohje();
    private Paataulu paataulu; 
    private TextField[] edits;
     
    
    /**
     * Tekee tarvittavat alustukset uuden reseptin lisäämistä varten 
     */
    public void alusta() {
        edits = luoKentat(gridTietue);
        editNimi.setOnKeyReleased(e -> kasitteleMuutosReseptiin((TextField)(e.getSource())));
        editKategoria.setOnKeyReleased(e -> kasitteleMuutosKategoriaan((TextField)(e.getSource())));
        editOhje.setOnKeyReleased(e -> kasitteleMuutosValmistusohjeeseen((TextArea)(e.getSource())));
    }
    
    
    
    /**
     * Virhe jos nimessä vikaa
     */
    public void nimiVirhe() {
        Alert haly = new Alert(AlertType.INFORMATION);
        haly.setTitle("Huomaa tämä");
        haly.setContentText("Nimi ei saa olla tyhjä");
        haly.showAndWait();
    }
    
    /**
     * Käsittelee reseptin kentän muutoksen
     * @param edit muokattava kenttä
     */
    private void kasitteleMuutosReseptiin(TextField edit) {
        if (reseptiKohdalla == null) return;
        String s = edit.getText();
        reseptiKohdalla.setTeksti(s);
    }
    
    /**
     * Käsittelee reseptin kentän muutoksen
     * @param edit muokattava kenttä
     */
    private void kasitteleMuutosKategoriaan(TextField edit) {
        if (reseptiKohdalla == null) return;
        String s = edit.getText();
        reseptiKohdalla.setKategoria(s);
    }
    
    
    /**
     * Käsittelee valmistusohjeen kentän muutoksen
     * @param edit muokattava kenttä
     */
    private void kasitteleMuutosValmistusohjeeseen(TextArea edit) {
        if (reseptiKohdalla == null) return;
        String s = edit.getText();
        palautettavaVO.taytaTiedolla(reseptiKohdalla.getTunnusNro());
        palautettavaVO.setTeksti(s); 
    }
    
    
    /**
     * Luo aineksien tallennuksessa käytettävät kentät
     * @param gridTietue mihin luodaan
     * @return kasa tekstikenttiä
     */
    public static TextField[] luoKentat(GridPane gridTietue) {
        gridTietue.getChildren().clear();
        TextField[] edits = new TextField[8];
        
        for (int k = 1; k < 8; k++) {
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridTietue.add(edit, 0, k);
        } 
        return edits;
    }
    
    
    /**
     * Tallentaa ja rekisteröi valmistusohjeen valmistusohjeen 
     */
    public void tallennaValmistusohje(){
        paataulu.lisaa(palautettavaVO);
    }
    
    
    /**
     * Tallentaa aineksen
     */
    public void tallennaAines(){
        for (TextField ed : edits) {
            if (ed != null) {
                if (ed.getText() != "") {
                Aines ai = new Aines();
                ai.taytaTiedolla(reseptiKohdalla.getTunnusNro());
                ai.rekisteroi();
                StringBuilder sb = new StringBuilder();
                String s = ed.getText();
                sb.append(s);
                ai.asetaNimi(Mjonot.erota(sb, ',', "nimi"));
                ai.asetaMaara(Mjonot.erota(sb, ',', 999999));
                ai.asetaMittaAsteikko(Mjonot.erota(sb, ',', "Kirjoita mitta-asteikko määrän perään välilyönnillä eroteltuna"));
                paataulu.lisaa(ai);
                }
            }
            
        }
        
    }
    
      
    /**
     * Asettaa päätaulun
     * @param pt päätauluolio
     */
    public void setPaataulu(Paataulu pt) {
        this.paataulu = pt;
    }
    
    
    /**
     * Luodaan uuden reseptin tekemisdialogi ja palautetaan tietue
     * @param <TYPE> olion tyyppi
     * @param modalityStage mille modaalisia
     * @param oletus mitä näytetään oletuksena
     * @param paataulu Päätaulu joka käytössä
     * @return jos peruuta
     */
    public static <TYPE extends Tietue> TYPE mikaResepti(Stage modalityStage, TYPE oletus, Paataulu paataulu) {
        return ModalController.<TYPE, LisaysController<TYPE>>showModal(
                LisaysController.class.getResource("Lisays.fxml"),
                "Keittokirja", modalityStage, oletus,
                ctrl -> ctrl.setPaataulu(paataulu));
    }
    

    @Override
    public TYPE getResult() {
        return reseptiKohdalla;
    }

    @Override
    public void handleShown() {
        editNimi.requestFocus();
        
    }

    @Override
    public void setDefault(TYPE oletus) {
        reseptiKohdalla = oletus;
        
    }
    
}

