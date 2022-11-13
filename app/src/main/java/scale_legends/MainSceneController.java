package scale_legends;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class MainSceneController {
    public Button btnExit;
    public Button btnNewGame;
    public Button btnLeaderboard;
    public Button btnOptions;
    public FXMLLoader loader;

    
    public void btnExitClick() {
        loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        try {
            App.root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene loginScene = new Scene( App.root);
        App.stage.setScene(loginScene);
        App.stage.show();
    }

    public void btnNewGameClick() {
        loader = new FXMLLoader(getClass().getResource("NewGame.fxml"));
        try {
            App.root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene newGameScene = new Scene( App.root);
        App.stage.setScene(newGameScene);
        App.stage.show();
    }

    public void btnLeaderboardClick() {
        loader = new FXMLLoader(getClass().getResource("Leaderboard.fxml"));
        try {
            App.root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene leaderboardScene = new Scene( App.root);
        App.stage.setScene(leaderboardScene);
        App.stage.show();
    }

    public void btnOptionsClick() {
        loader = new FXMLLoader(getClass().getResource("Options.fxml"));
        try {
            App.root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene optionsScene = new Scene( App.root);
        App.stage.setScene(optionsScene);
        App.stage.show();
    }
}
