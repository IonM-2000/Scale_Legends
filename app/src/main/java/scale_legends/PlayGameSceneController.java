package scale_legends;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class PlayGameSceneController {
    public static final Scene scene = App.loadScene("PlayGame");
    @FXML Canvas cnvCanvas;
    GraphicsContext graphicsContext;

    private static final int SQUARE_SIZE = 50;
    private static int WIDTH;
    private static int HEIGHT;
    private static int ROWS;
    private static int COLUMNS;

    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private static List<Point> snakeBody;
    private static Point snakeHead;
    private static Point foodPos;
    private static GameState gameState;
    private static boolean isGameStateFirstTick;
    private static int currentDirection;
    private static int lastDirection;
    private static int score;
    private static int segmentsToGrow;
    
    private static Timeline timeline;
    private static final int frameTickRatio = 10;
    private static final double inputAcceptancePercent = 1.0;
    private static int frameCount;

    public void initialize() {
        WIDTH = (int) cnvCanvas.getWidth();
        HEIGHT = (int) cnvCanvas.getHeight();
        ROWS = HEIGHT / SQUARE_SIZE - 1;
        COLUMNS = WIDTH / SQUARE_SIZE;

        graphicsContext = cnvCanvas.getGraphicsContext2D();
        timeline = new Timeline(new KeyFrame(Duration.millis(25), f -> { run(); }));
        timeline.setCycleCount(Animation.INDEFINITE);

        foodPos = new Point(-1, -1);
        snakeBody = new ArrayList<Point>();
        
        gameState = GameState.LIMBO;
        reset();

        timeline.play();
    }

    public static void setKeyControls(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((double)(frameCount % frameTickRatio) / frameTickRatio < inputAcceptancePercent) {
                    KeyCode code = event.getCode();
                    
                    if (code == KeyCode.RIGHT || code == KeyCode.D) {
                        if (lastDirection != LEFT) {
                            currentDirection = RIGHT;
                        }
                    } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                        if (lastDirection != RIGHT) {
                            currentDirection = LEFT;
                        }
                    } else if (code == KeyCode.UP || code == KeyCode.W) {
                        if (lastDirection != DOWN) {
                            currentDirection = UP;
                        }
                    } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                        if (lastDirection != UP) {
                            currentDirection = DOWN;
                        }
                    } else if (code == KeyCode.SPACE) {
                        if (gameState != GameState.RUNNING) {
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
        currentDirection = RIGHT;
        lastDirection = LEFT;
        score = 0;
    }

    private static void startGameplay() {
        gameState = GameState.RUNNING;
        isGameStateFirstTick = true;

        snakeBody.clear();
        snakeBody.add(new Point(COLUMNS / 2 - 1, ROWS / 2 - 1));
        snakeHead = snakeBody.get(0);
        segmentsToGrow = 2;

        generateFood();
        
        timeline.play();
    }

    private void run() {
        if (frameCount % frameTickRatio == 0) {
            if (isGameStateFirstTick == true) {
                gameStateFirstTick();
            }
            isGameStateFirstTick = false;

            if (gameState == GameState.RUNNING) {
                checkGameOver();
                moveSnake();
                eatFood();
            }

            drawBackground();
            drawScore();
            drawFood();
            drawSnake();

            drawGameState();
        }

        frameCount++;
    }

    private void gameStateFirstTick() {
        switch (gameState) {
            case LOSS:
                //TODO Send data to database
    
                break;
            case WIN:
                //TODO Send data to database
    
                break;
            default:
                // hopefully unreachable
                break;
        }
    }

    private void moveSnake() {
        if (segmentsToGrow > 0) {
            segmentsToGrow--;
            snakeBody.add(new Point(snakeBody.get(snakeBody.size() - 1)));
        }

        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        switch (currentDirection) {
            case RIGHT:
                moveRight();
                break;
            case LEFT:
                moveLeft();
                break;
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
        }
        
    }

    private static void generateFood() {
        Vector<Point> possible_positions = new Vector<Point>();

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                boolean food_inside_snake = false;

                for (Point snakeSegment : snakeBody) {
                    if (snakeSegment.x == x && snakeSegment.y == y) {
                        food_inside_snake = true;
                        break;
                    }
                }

                if (food_inside_snake == false) {
                    possible_positions.add(new Point(x, y));
                }
            }
        }

        if (possible_positions.isEmpty()) {
            foodPos.y = -1;
            foodPos.x = -1;

            gameState = GameState.WIN;

            return;
        }

        int random_index = (int) (Math.random() * possible_positions.size());
        
        foodPos.y = possible_positions.get(random_index).y;
        foodPos.x = possible_positions.get(random_index).x;
    }
    
    private void moveRight() {
        snakeHead.x++;
        lastDirection = RIGHT;
    }
    private void moveLeft() {
        snakeHead.x--;
        lastDirection = LEFT;
    }
    private void moveUp() {
        snakeHead.y--;
        lastDirection = UP;
    }
    private void moveDown() {
        snakeHead.y++;
        lastDirection = DOWN;
    }

    public void checkGameOver() {
        if (gameState != GameState.RUNNING) {
            return;
        }

        Point nextSnakeHead = new Point(snakeHead);

        switch (currentDirection) {
            case RIGHT:
                nextSnakeHead.x++;
                break;
            case LEFT:
                nextSnakeHead.x--;
                break;
            case UP:
                nextSnakeHead.y--;
                break;
            case DOWN:
                nextSnakeHead.y++;
                break;
        }
        
        if (nextSnakeHead.x < 0 || nextSnakeHead.y < 0 || nextSnakeHead.x >= COLUMNS || nextSnakeHead.y >= ROWS) {
            gameState = GameState.LOSS;
            return;
        }

        for (int i = 1; i < snakeBody.size(); i++) {
            if (nextSnakeHead.equals(snakeBody.get(i))) {
                gameState = GameState.LOSS;
                return;
            }
        }
    }

    private void eatFood() {
        if (snakeHead.x == foodPos.x && snakeHead.y == foodPos.y) {
            segmentsToGrow++;
            score += 1;
            generateFood();
        }
    }

    private void drawFood() {
        graphicsContext.setFill(Color.web("8F0114"));
        graphicsContext.fillRoundRect(
            foodPos.x * SQUARE_SIZE, (foodPos.y + 1) * SQUARE_SIZE,
            SQUARE_SIZE, SQUARE_SIZE,
            SQUARE_SIZE, SQUARE_SIZE
        );
    }

    private void drawSnake() {
        if (snakeBody.isEmpty() == true) {
            return;
        }

        graphicsContext.setFill(Color.web("4674E9"));
        graphicsContext.fillRoundRect(
            snakeHead.x * SQUARE_SIZE,
            (snakeHead.y + 1) * SQUARE_SIZE,
            SQUARE_SIZE - 1, SQUARE_SIZE - 1,
            35, 35
        );

        for (int i = 1; i < snakeBody.size(); i++) {
            graphicsContext.fillRoundRect(
                snakeBody.get(i).x * SQUARE_SIZE,
                (snakeBody.get(i).y + 1) * SQUARE_SIZE,
                SQUARE_SIZE - 1, SQUARE_SIZE - 1,
                20, 20
            );
        }
    }
    
    private void drawBackground() {
        for (int x = 0; x < COLUMNS; x++) {
            graphicsContext.setFill(Color.web("668130"));
            graphicsContext.fillRect(x * SQUARE_SIZE, 0, SQUARE_SIZE, SQUARE_SIZE);
        }

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                if ((y + x) % 2 == 0) {
                    graphicsContext.setFill(Color.web("AAD751"));
                } else {
                    graphicsContext.setFill(Color.web("A2D149"));
                }
                graphicsContext.fillRect(x * SQUARE_SIZE, (y + 1) * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    private void drawScore() {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("Digital-7", 35));
        graphicsContext.fillText("Score: " + score, 10, 40);
    }

    private void drawGameState() {
        graphicsContext.setFont(new Font("Digital-7", 70));
        switch (gameState) {
            case LIMBO:
                graphicsContext.setFill(Color.BLUE);
                graphicsContext.fillText("Press start", WIDTH / 3.5, HEIGHT / 2);
                break;
            case RUNNING:
                // unreachable, i hope
                break;
            case LOSS:
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText("You Lose!", WIDTH / 3.5, HEIGHT / 2);
                break;
            case WIN:
                graphicsContext.setFill(Color.GOLD);
                graphicsContext.fillText("You Win!", WIDTH / 3.5, HEIGHT / 2);
                break;
            default:
                break;
        }
    }
    
    private static class Point {
        int x, y;

        Point(Point other) {
            this.x = other.x;
            this.y = other.y;
        }

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point() {
            this.x = 0;
            this.y = 0;
        }

        boolean equals(Point other) {
            return this.x == other.x && this.y == other.y;
        }
    }

    private enum GameState {
        LIMBO,
        RUNNING,
        LOSS,
        WIN
    }

}
