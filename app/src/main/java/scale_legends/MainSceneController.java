package scale_legends;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class MainSceneController {
    public static Scene scene = App.loadScene("Main");
    @FXML public Button btnExit;
    @FXML public Button btnNewGame;
    @FXML public Button btnLeaderboard;
    @FXML public Button btnOptions;
    
    public void btnExitClick() {
        App.changeScene(LoginSceneController.scene);
    }

    public void btnNewGameClick() {
        App.changeScene(NewGameSceneController.scene);
    }

    public void btnLeaderboardClick() {
        App.changeScene(LeaderboardSceneController.scene);
    }

    public void btnOptionsClick() {
        App.changeScene(OptionsSceneController.scene);
    }
}
