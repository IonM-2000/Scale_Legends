package scale_legends;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import scale_legends.gamemodes.GameMode.GameModeType;

public class NewGameSceneController {
    public static Scene scene = App.loadScene("NewGame");
    @FXML public Button btnMain;
    @FXML public Button btnPlay;
    @FXML public ListView<String> listGamemodes;
    
    public void initialize() {
        ObservableList<String> gamemodes = FXCollections.observableArrayList(
            GameModeType.CLASSIC.toString(),
            GameModeType.FREEDOM.toString(),
            GameModeType.OBSTACLES.toString(),
            GameModeType.PORTALS.toString()
        );

        listGamemodes.setItems(gamemodes);
        listGamemodes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listGamemodes.getSelectionModel().select(0);
    }
    
    public void btnMainClick() {
        App.changeScene(MainSceneController.scene);
    }

    public void btnPlayClick() {
        App.changeScene(PlayGameSceneController.scene);
        
        String gameModeString = listGamemodes.getSelectionModel().getSelectedItem();
        GameModeType gameModeType = GameModeType.valueOf(gameModeString);
        PlayGameSceneController.gameModeType = gameModeType;
        
        PlayGameSceneController.updateGameMode();
    }
    
}
