package scale_legends;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;  
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import scale_legends.gamemodes.*;

public class PlayGameSceneController {
    public static final Scene scene = App.loadScene("PlayGame");
    @FXML Canvas cnvCanvas;
    GraphicsContext graphicsContext;

    private static GameMode gameMode;

    private int WIDTH;
    private int HEIGHT;

    private static Timeline timeline;
    private static final int frameTickRatio = 10;
    private static final double inputAcceptancePercent = 1.0;
    private static int frameCount;

    public void initialize() {
        WIDTH = (int) cnvCanvas.getWidth();
        HEIGHT = (int) cnvCanvas.getHeight();
        
        gameMode = new ClassicMode(WIDTH, HEIGHT);

        graphicsContext = cnvCanvas.getGraphicsContext2D();
        timeline = new Timeline(new KeyFrame(Duration.millis(25), f -> { run(); }));
        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.play();
    }

    public static void setKeyControls(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((double)(frameCount % frameTickRatio) / frameTickRatio < inputAcceptancePercent) {
                    KeyCode code = event.getCode();
                    
                    if (gameMode.handleInput(code)) {

                    } else if (code == KeyCode.SPACE) {
                        if (gameMode.isRunning() == false) {
                            reset();
                            startGameplay();
                        }
                    }
                }
            }
        });
    }
    
    private static void reset() {
        frameCount = 0;

        gameMode.reset();
    }

    private static void startGameplay() {
        gameMode.startGameplay();
        
        timeline.play();
    }

    private void run() {
        if (frameCount % frameTickRatio == 0) {
            if (gameMode.isGameStateChanged()) {
                gameStateFirstTick();
            }
            
            gameMode.run();
            gameMode.draw(graphicsContext);
        }

        frameCount++;
    }

    private void gameStateFirstTick() {
        switch (gameMode.getGameState()) {
            case LOSS: {
                System.out.println("LOSS");
                //TODO Send data to database
    
                break;
            }
            case WIN: {
                System.out.println("WIN");

                //TODO Send data to database
    
                break;
            }
            default:
                // hopefully unreachable
                break;
        }
    }

}
