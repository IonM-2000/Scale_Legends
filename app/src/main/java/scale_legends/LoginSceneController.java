package scale_legends;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class LoginSceneController {
    public Button btnLogin;
    public FXMLLoader loader;

    
    public void btnLoginClick(){
        loader = new FXMLLoader(getClass().getResource("Main.fxml"));
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
