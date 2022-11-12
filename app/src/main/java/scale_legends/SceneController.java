package scale_legends;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController{
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToLoginScene(ActionEvent event) throws IOException{
        URL url =  ClassLoader.getSystemResource("scale_legends/assets/Login.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMainScene(ActionEvent event) throws IOException{
        URL url =  ClassLoader.getSystemResource("scale_legends/assets/Main.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToNewGameScene(ActionEvent event) throws IOException{
        URL url =  ClassLoader.getSystemResource("scale_legends/assets/NewGame.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLeaderboardScene(ActionEvent event) throws IOException{
        URL url =  ClassLoader.getSystemResource("scale_legends/assets/Leaderboard.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToOptionsScene(ActionEvent event) throws IOException{
        URL url =  ClassLoader.getSystemResource("scale_legends/assets/Options.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

}