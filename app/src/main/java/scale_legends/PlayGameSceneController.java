package scale_legends;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;  
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import scale_legends.gamemodes.*;
import scale_legends.gamemodes.GameMode.GameModeType;

public class PlayGameSceneController {
    public static final Scene scene = App.loadScene("PlayGame");
    @FXML Canvas cnvCanvas;
    @FXML Button butBack;
    GraphicsContext graphicsContext;

    private static GameMode gameMode;
    public static GameModeType gameModeType = GameModeType.CLASSIC;

    private static int WIDTH;
    private static int HEIGHT;

    private static Timeline timeline;
    private static final int frameTickRatio = 10;
    private static final double inputAcceptancePercent = 1.0;
    private static int frameCount;

    public void initialize() {
        graphicsContext = cnvCanvas.getGraphicsContext2D();
        timeline = new Timeline(new KeyFrame(Duration.millis(25), f -> { run(); }));
        timeline.setCycleCount(Animation.INDEFINITE);

        WIDTH = (int) cnvCanvas.getWidth();
        HEIGHT = (int) cnvCanvas.getHeight();

        timeline.play();
    }

    public static void updateGameMode() {
        switch (gameModeType) {
            case CLASSIC: {
                gameMode = new ClassicMode(WIDTH, HEIGHT);
                break;
            }
            case FREEDOM: {
                gameMode = new FreedomMode(WIDTH, HEIGHT);
                break;
            }
            case OBSTACLES: {
                gameMode = new ObstaclesMode(WIDTH, HEIGHT);
                break;
            }
            case PORTALS: {
                gameMode = new PortalsMode(WIDTH, HEIGHT);
                break;
            }
        }
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
        butBack.setFocusTraversable(false);

        if (gameMode != null) {
            if (frameCount % frameTickRatio == 0) {
                if (gameMode.isGameStateChanged()) {
                    gameStateFirstTick();
                }
                
                gameMode.run();
                gameMode.drawGamePlay(graphicsContext);
                gameMode.drawGameHeader(graphicsContext);
            }
    
            frameCount++;
        }
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

    public void btnBackClick() {
        App.changeScene(NewGameSceneController.scene);
    }

}
