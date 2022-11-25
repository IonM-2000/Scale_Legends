package scale_legends;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class OptionsSceneController {
    public static Scene scene = App.loadScene("Options");
    @FXML public Button btnCancel;

    public void btnCancelClick() {
        App.changeScene(MainSceneController.scene);
    }
}
