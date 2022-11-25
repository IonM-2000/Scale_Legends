package scale_legends;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class NewGameSceneController {
    public static Scene scene = App.loadScene("NewGame");
    @FXML public Button btnMain;
    @FXML public Button btnPlay;
    @FXML public ListView<String> listGamemodes;
    
    public void initialize() {
        ObservableList<String> gamemodes = FXCollections.observableArrayList(
            "Golf Balls", "Wedges", "Irons", "Tees", "Driver", "Putter"
        );
        listGamemodes = new ListView<String>(gamemodes);
        listGamemodes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
    }
    
    public void btnMainClick() {
        App.changeScene(MainSceneController.scene);
    }

    public void btnPlayClick() {
        App.changeScene(PlayGameSceneController.scene);
    }
    
}
