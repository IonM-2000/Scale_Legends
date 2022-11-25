package scale_legends;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class LoginSceneController {
    public static Scene scene = App.loadScene("Login");
    @FXML public Button btnLogin;
    
    public void btnLoginClick() {
        App.changeScene(MainSceneController.scene);
    }
}
