package scale_legends;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class LeaderboardSceneController {
    public static Scene scene = App.loadScene("Leaderboard");
    @FXML public Button btnMain;
    
    public void btnMainClick() {
        App.changeScene(MainSceneController.scene);
    }
}
