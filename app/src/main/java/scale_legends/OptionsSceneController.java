package scale_legends;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class OptionsSceneController {
    public Button btnCancel;
    public FXMLLoader loader;

    
    public void btnCancelClick() {
        URL url =  ClassLoader.getSystemResource("scale_legends/assets/Main.fxml");
        loader = new FXMLLoader(url);
        try {
            App.root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene mainScene = new Scene( App.root);
        App.stage.setScene(mainScene);
        App.stage.show();
    }
}
