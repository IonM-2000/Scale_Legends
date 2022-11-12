package scale_legends;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class LeaderboardSceneController {
    public Button btnMain;
    public FXMLLoader loader;

    
    public void btnMainClick(){
        loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        try {
            App.root = loader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Scene mainScene = new Scene( App.root);
        App.stage.setScene(mainScene);
        App.stage.show();
    }
}
