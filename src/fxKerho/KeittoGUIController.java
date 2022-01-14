package fxKerho;

import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.RadioButtonChooser;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import keittokirja.Aines;
import keittokirja.Paataulu;
import keittokirja.Resepti;
import keittokirja.SailoException;
import keittokirja.Valmistusohje;
/**
 * @author Matti
 * @version Jan 21, 2021
 *
 */
public class KeittoGUIController implements Initializable{
    @FXML private TextField hakuehto;
    @FXML private TextArea areaOhje;
    @FXML private ListChooser<Resepti> chooserReseptit;
    @FXML private StringGrid<Aines> gridAinekset;
    @FXML private TextField hakuEhto;

    @FXML private ComboBoxChooser<String> hakuKategoria;
    @FXML private RadioButtonChooser<Resepti> arvostelu;


    
    
    @FXML private void handleHakuehto() {
        hae(0);
    }
    
    /**
     * Alustus
     * @param url a
     * @param bundle b
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    @FXML private void handleAvaa() {
        String nimi = KirjavalintaController.nayta(null, "resepteja");
        lueTiedosto(nimi);
    }
    
    @FXML void handleTallenna() {
        tallenna();
    }
    
    @FXML void handleLopeta() {
        lopeta();
    }
    
    @FXML void handlePoista() {
        poista();
    }
    
    @FXML void handleUusi() {
        luoUudet();
    }
    
    @FXML void handleHaku() {
        haeResepti(hakuEhto, hakuKategoria);
    }
    
    @FXML void handleTulosta() {
        TulostusController tulostusCtrl = TulostusController.tulosta(null);
        tulostaValitut(tulostusCtrl.getTextArea());
    }
    
    
    @FXML void handleTietoja() {
        ModalController.showModal(KeittoGUIController.class.getResource("Tietoja.fxml"), "Tietoja", null, "");
    }
    
    @FXML void handleAinekset() {
        aineksetIkkuna();
    }
    
    @FXML void handleReseptit() {
        ohjeIkkuna();
    }
    
    
    @FXML void lisaaAines() {
        uusiAines();
    }

    @FXML void poistaAines() {
        poistaAineksista();
    }
    
    
    //Ei liity FXML---------------------------
    
    private Paataulu paataulu;
    private Resepti reseptiKohdalla;
    private Aines apuAines = new Aines();
    
    
    /**
     * Alustaa ohjelman käynnistyessä
     */
    protected void alusta() {
        chooserReseptit.clear();
        chooserReseptit.addSelectionListener(e -> naytaResepti());
        int eka = apuAines.ekaKentta();
        int lkm = apuAines.getKenttia();
        String[] headings = new String[lkm - eka];
        for (int i=0, k=eka; k<lkm; i++, k++) headings[i] = apuAines.getKysymys(k);
        gridAinekset.initTable(headings);
        gridAinekset.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        gridAinekset.setEditable(true);
        gridAinekset.setPlaceholder(new Label("Eipä ole mitään"));
        
        hakuKategoria.clear();
        hakuKategoria.add("nimi");
        hakuKategoria.add("kategoria");
        hakuKategoria.add("aines");
        hakuKategoria.getSelectionModel().select(0);
        
        areaOhje.setOnKeyReleased(e -> kasitteleMuutosValmistusohjeeseen((TextArea)(e.getSource())));
        gridAinekset.setOnKeyReleased(e -> kasitteleMuutosAineksiin((StringGrid<Aines>)(e.getSource())));
        arvostelu.addSelectionListener(x -> kasitteleMuutosArvosteluun());
    }
    
    
    /**
     * Käsittelee arvostelun muutoksen
     */
    public void kasitteleMuutosArvosteluun() {
        int tahtia = arvostelu.getSelectedIndex() + 1; 
        reseptiKohdalla.setArvostelu(tahtia);
    }
    
    
    /**
     * Käsittelee aineksien muutoksen luomalla uuden aineslistan 
     * kenttien perusteella ja korvaamalla vanhan sillä
     * @param stringGrid aineksien kentät
     */
    public void kasitteleMuutosAineksiin(StringGrid<Aines> stringGrid) {
        if (reseptiKohdalla == null) return;
        List<Aines> ainekset = paataulu.annaAinekset(reseptiKohdalla);
        List<Aines> apuAinekset = new ArrayList<Aines>(ainekset.size());
        for (int i = 0; i < ainekset.size(); i++) {
            Aines a = new Aines();
            String nimi = stringGrid.get(i, 0);
            
            int maara = 0;
            try { 
            maara = Integer.parseInt(stringGrid.get(i, 1));
            } catch (NumberFormatException e){
                Alert haly = new Alert(AlertType.INFORMATION);
                haly.setTitle("Huomaa tämä");
                haly.setContentText("Käytä vain numeroita määrässä");
                haly.showAndWait();
            }
            
            String mA = stringGrid.get(i, 2);
            a.taytaTiedolla(reseptiKohdalla.getTunnusNro());
            a.rekisteroi();
            a.asetaNimi(nimi);
            a.asetaMaara(maara);
            a.asetaMittaAsteikko(mA);
            apuAinekset.add(a);
        } 
        for (Aines a : ainekset) {
            paataulu.poista(a);
        }   
        
        for (Aines a : apuAinekset) {
           paataulu.lisaa(a);
        }
        
    }
    
    
    /**
     * Poistaa aineksista valitun
     */
    public void poistaAineksista() {
        if (reseptiKohdalla == null) return;
        String mika = gridAinekset.get(gridAinekset.getRowNr(), 0);
        List<Aines> ainekset = paataulu.annaAinekset(reseptiKohdalla);
        for (Aines a : ainekset) {
            String nimi = a.getNimi();
            if (nimi.replaceAll("//s", "").equals(mika.replaceAll("//s", ""))) {
                paataulu.poista(a);
                break;
            }
        }
        naytaAinekset(reseptiKohdalla);
        
    }

    
    
    /**
     * Käsittelee valmistusohjelaatikon muutoksen luomalla uuden
     * teksikentän perusteella ja poistamalla vanhan
     * @param textArea tekstilaatikko jossa muokataan
     */
    public void kasitteleMuutosValmistusohjeeseen(TextArea textArea) {
        if (reseptiKohdalla == null) return;
        Valmistusohje v = new Valmistusohje();
        String s = textArea.getText();
        v.taytaTiedolla(reseptiKohdalla.getTunnusNro());
        v.setTeksti(s);
        paataulu.poista(etsi(reseptiKohdalla.getTunnusNro()));
        paataulu.lisaa(v);
    }
    
    
    
    /**
     * Etsii valmistusohjeen jolla sama tunnusnumero kuin "etsittava"
     * @param etsittava verrataan tunnusnumeroon
     * @return valmistusohje jos löytyy, muuten null 
     */
    public Valmistusohje etsi(int etsittava) {
        List<Valmistusohje> valmistusohjeet = paataulu.annaValmistusohjeet(reseptiKohdalla);
        for (Valmistusohje v : valmistusohjeet) {
            if (v.getTunnusNro() == etsittava) return v;
        }
        return null;
    }
    

    private void poista() {
        Resepti resepti = reseptiKohdalla;
        if (resepti == null) return;
        if (!Dialogs.showQuestionDialog("poisto", "Poistetaanko " + reseptiKohdalla.getNimi(), "kyllä", "ei")) return;
        paataulu.poista(resepti);
        int indeksi = chooserReseptit.getSelectedIndex();
        hae(0);
        chooserReseptit.setSelectedIndex(indeksi);
    }
    
    
    private String tallenna() {
        try {
            paataulu.tallenna();
            return null;
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa ongelma" + e.getMessage());
            return e.getMessage();
        }
    }
    
    
    /**
     * Alustaa keittokirjan lukemalla tiedoston
     * @param nimi tiedosto jota luetaan
     * @return null jos onnistuu, muuten teksti
     */
    protected String lueTiedosto(String nimi) {
        try {
            paataulu.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage();
            if (virhe != null) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    
    
    /**
     * Hoitaa lopetuksen, tallentaa ja lopettaa
     */
    public void lopeta() {
        tallenna();
        Platform.exit();
    }
    
    
    /**
     * Näyttää virheen sellaisen ilmeentyessä
     * @param virhe syntynyt virhe
     */
    public void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            Dialogs.showMessageDialog("virhe");
        }
    }
    
    /**
     * Asettaa päätaulun josta menee tiedostojen lukemiseen
     * @param paataulu Päätaulu joka näytetään käyttöliittymässä
     */
    public void setPaataulu(Paataulu paataulu) {
        this.paataulu = paataulu;
        lueTiedosto("resepteja");
        naytaResepti();
    }
    
    /**
     * Näyttää listasta valitun reseptin tiedot
     */
    public void naytaResepti() {
        reseptiKohdalla = chooserReseptit.getSelectedObject();
        if (reseptiKohdalla == null) return;
        
        areaOhje.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaOhje)) {
            tulosta(os, reseptiKohdalla);
        }
        naytaAinekset(reseptiKohdalla);
        arvostelu.setSelectedIndex(reseptiKohdalla.getArvostelu() - 1);
                
    }
    
    /**
     * Näyttää reseptille kuuluvat ainekset taulukossa
     * @param resepti jonka omat näytetään
     */
    public void naytaAinekset(Resepti resepti) {
        gridAinekset.clear();
        if (resepti == null) return;
        List<Aines> ainekset = paataulu.annaAinekset(resepti);
        if (ainekset.size() == 0) return;
        for (Aines a : ainekset) naytaAines(a);
    }
    
    
    /**
     * Lisää aineksen tiedot taulukkoon
     * @param aines lisättävä aines
     */
    public void naytaAines(Aines aines) {
        int kenttia = aines.getKenttia();
        String[] rivi = new String[kenttia - aines.ekaKentta()];
        for (int i = 0, k=aines.ekaKentta(); k < kenttia; i++, k++)
            rivi[i] = aines.anna(k);
        gridAinekset.add(aines, rivi);
    }
    
    
    
    private void tulosta(PrintStream os, Resepti resepti) {
        List<Valmistusohje> valmistusohjeet = paataulu.annaValmistusohjeet(resepti);
        for (Valmistusohje v : valmistusohjeet) {
            v.tulosta(os);
        }
    }
    
    /**
     * Haetaan reseptin tiedot listaan
     * @param nro reseptin numero joka aktivoidaan
     */
    public void hae(int nro) {
        int num = nro;
        chooserReseptit.clear();
        areaOhje.clear();
        gridAinekset.clear();
        gridAinekset.setPlaceholder(new Label("Aloita painamalla uusi nappia"));
        
        if (num <= 0) {
            Resepti kohdalla = reseptiKohdalla;
            if (kohdalla != null) num = kohdalla.getTunnusNro();
        }
        
        int indeksi = 0;
        for (int i = 0; i < paataulu.getResepteja(); i++) {
            Resepti resepti = paataulu.annaResepti(i);
            if (resepti.getTunnusNro() == nro) indeksi = i;
            chooserReseptit.add(resepti.getNimi(), resepti);
        }
        chooserReseptit.setSelectedIndex(indeksi);
        chooserReseptit.addSelectionListener(e -> naytaResepti());
    }
    
    
    /**
     * Hakee resepteistä ehtoa vastaavaa
     * @param ehto jolla haetaan
     * @param haku kategoria jolla haetaan
     */
    public void haeResepti(TextField ehto, ComboBoxChooser<String> haku) {
        if (ehto.getText() == "") hae(0);
        String s = haku.getValue().getObject().toString();
        List<Resepti> reseptit = new ArrayList<Resepti>();
        
        if (s.equals("nimi") || s.equals("kategoria")) reseptit = paataulu.etsi(ehto.getText(), s);
        if (s.equals("aines")) reseptit = paataulu.etsiAinekset(ehto.getText());
        
        if (reseptit.size() == 0) return;
        reseptiKohdalla = reseptit.get(0);
        areaOhje.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaOhje)) {
            tulosta(os, reseptiKohdalla);
        }
        naytaAinekset(reseptiKohdalla);
        chooserReseptit.clear();
        for (Resepti r : reseptit) {
            chooserReseptit.add(r.getNimi(), r);
        }
        
    }
    
    
    /**
     * Luodaan uusi tyhjä resepti
     */
    public void uusiResepti() {
        Resepti uusi = new Resepti();
        uusi.rekisteroi();
        uusi.taytaTiedoilla();
        try {
            paataulu.lisaa(uusi);
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Ongelmia luomisessa " + ex.getMessage());
            return;
        }
        hae(uusi.getTunnusNro());
    }
    
    
    /**
     * Luodaan uusi tyhjä valmistusohje
     */
    public void uusiValmistusohje() {
        if (reseptiKohdalla == null) {
            Valmistusohje ohje = new Valmistusohje();
            ohje.rekisteroi();
            ohje.taytaTiedolla();
            paataulu.lisaa(ohje);
            hae(1);
            return;
        }
        Valmistusohje ohje = new Valmistusohje();
        ohje.rekisteroi();
        ohje.taytaTiedolla(reseptiKohdalla.getTunnusNro());
        paataulu.lisaa(ohje);
        hae(reseptiKohdalla.getTunnusNro());
        
    }
    
    /**
     * Luodaan uusi tyhjä aines
     */
    public void uusiAines() {
        if ( reseptiKohdalla == null ) {
            Aines a = new Aines();
            a.rekisteroi();
            a.taytaTiedolla(1);
            
            paataulu.lisaa(a);
            
            hae(1);
            return;
        }
        Aines a = new Aines();
        a.rekisteroi();
        a.taytaTiedolla(reseptiKohdalla.getTunnusNro());
        
        paataulu.lisaa(a);
        
        hae(reseptiKohdalla.getTunnusNro());
            
    }
    
    
    /**
     * Luodaan uusi resepti, ainekset ja valmistusohje samassa metodissa 
     * Näytetään lisäyksen dialogi
     */
    public void luoUudet() {
        try {                      
            Resepti resepti = new Resepti();
            resepti.rekisteroi();
            resepti = LisaysController.mikaResepti(null, resepti, paataulu);
            if (resepti == null) return;
            paataulu.lisaa(resepti);
        
            hae(resepti.getTunnusNro());
        } catch (SailoException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     *  Näyttää reseptin ainekset omassa ikkunassa
     */
    public void aineksetIkkuna() {
        AinesIkkunaController.mikaResepti(null, reseptiKohdalla, paataulu);
    }
    
    
    /**
     * Näyttää valmistusohjeen omassa ikkunassa
     */
    public void ohjeIkkuna() {
        List<Valmistusohje> ohjeet = paataulu.annaValmistusohjeet(reseptiKohdalla);
        if (ohjeet.size() != 0) OhjeIkkunaController.mikaResepti(null, ohjeet.get(0), paataulu);
        else {
            Valmistusohje v = new Valmistusohje();
            v.rekisteroi();
            OhjeIkkunaController.mikaResepti(null, v, paataulu);
        }
        
    }
    
    
    /**
     * Tulostaa listassa olevat jäsenet tekstialueeseen
     * @param teksti johon tulostetaan
     */
    public void tulostaValitut(TextArea teksti) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(teksti)) {
            tulostaYhdenTiedot(os, reseptiKohdalla);
        }
    }
    
    
    /**
     * Tulostaa reseptin tiedot
     * @param os minne tulostetaan
     * @param resepti mikä resepti
     */
    public void tulostaYhdenTiedot(PrintStream os, Resepti resepti) {
        resepti.tulosta(os);
        os.print("\n");
        os.print("Ainekset:\n");
        
        List<Aines> ainekset = paataulu.annaAinekset(resepti);
        for (Aines v : ainekset) {
            v.tulosta(os);
        }
        
        os.print("\n");
        os.print("Valmistusohjeet:");
        os.print("\n");
        List<Valmistusohje> valmistusohjeet = paataulu.annaValmistusohjeet(resepti);
        for (Valmistusohje v : valmistusohjeet) {
            v.tulosta(os);
        }
    }
    
    
}
