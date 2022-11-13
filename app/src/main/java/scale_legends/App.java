package scale_legends;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    public static Stage stage;
    public static FXMLLoader loader;
    public static Parent root;
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        App.stage = stage;
        URL url =  ClassLoader.getSystemResource("scale_legends/assets/Login.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Scene currentScene = new Scene(root);
        stage.setTitle("Scale Legends");
        stage.setScene(currentScene);
        stage.show();
    }
}
