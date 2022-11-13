package scale_legends;

import java.io.IOException;
import java.net.URL;

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
        URL url =  ClassLoader.getSystemResource("scale_legends/assets/Login.fxml");
        loader = new FXMLLoader(url);
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
        URL url =  ClassLoader.getSystemResource("scale_legends/assets/NewGame.fxml");
        loader = new FXMLLoader(url);
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
        URL url =  ClassLoader.getSystemResource("scale_legends/assets/Leaderboard.fxml");
        loader = new FXMLLoader(url);
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
        URL url =  ClassLoader.getSystemResource("scale_legends/assets/Options.fxml");
        loader = new FXMLLoader(url);
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
