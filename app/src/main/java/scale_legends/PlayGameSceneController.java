package scale_legends;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;  
import javafx.scene.canvas.GraphicsContext;

public class PlayGameSceneController {
    public static final Scene scene = App.loadScene("PlayGame");
    @FXML Canvas cnvCanvas;
    GraphicsContext graphicsContext;
}
