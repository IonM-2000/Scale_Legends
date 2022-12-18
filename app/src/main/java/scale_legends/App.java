package scale_legends;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static FXMLLoader currentLoader;
    public static Parent currentRoot;
    public static Scene currentScene;
    public static Stage stage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        App.stage = stage;
        stage.setTitle("Scale Legends");
        
        //TODO Change to LoginSceneController
        changeScene(NewGameSceneController.scene);
    }
    
    public static void changeScene(Scene scene) {
        stage.setScene(scene);
        stage.show();

        if (scene == PlayGameSceneController.scene) {
            PlayGameSceneController.setKeyControls(scene);
        }
    }

    public static Scene loadScene(String sceneName) {
        String path = "scale_legends/assets/" + sceneName + ".fxml";
        URL url = ClassLoader.getSystemResource(path);

        try {
            currentLoader = new FXMLLoader(url);
            currentRoot = currentLoader.load();
            currentScene = new Scene(currentRoot);
            return currentScene;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

}
