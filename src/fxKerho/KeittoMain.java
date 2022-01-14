package fxKerho;
    
import javafx.application.Application;
import javafx.stage.Stage;
import keittokirja.Paataulu;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * @author Matti
 * @version Jan 21, 2021
 *
 */
public class KeittoMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("KeittoGUIView.fxml"));
            final BorderPane root = (BorderPane)ldr.load();
            final KeittoGUIController keittoCtrl = (KeittoGUIController)ldr.getController();
            
            Scene scene = new Scene(root,800,600);
            scene.getStylesheets().add(getClass().getResource("kerho.css").toExternalForm());
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Keittokirja");
            
            Paataulu paataulu = new Paataulu();
            keittoCtrl.setPaataulu(paataulu);
            
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}
