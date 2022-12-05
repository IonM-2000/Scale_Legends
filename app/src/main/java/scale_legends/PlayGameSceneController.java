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

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int SQUARE_SIZE = 50;
    private static final int ROWS = HEIGHT / SQUARE_SIZE;
    private static final int COLUMNS = WIDTH / SQUARE_SIZE;

    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private List<Point> snakeBody;
    private Point snakeHead;
    private Point foodPos;
    private boolean gameOver;
    private static int currentDirection;
    private static int lastDirection;
    private int score;
    private int segmentsToGrow;
    
    private Timeline timeline;
    private static final int frameTickRatio = 5;
    private static final double inputAcceptancePercent = 1.0;
    private static int frameCount;

    public void initialize() {
        graphicsContext = cnvCanvas.getGraphicsContext2D();
        timeline = new Timeline(new KeyFrame(Duration.millis(50), f -> { run(); }));
        timeline.setCycleCount(Animation.INDEFINITE);

        foodPos = new Point();
        snakeBody = new ArrayList<Point>();
        
        reset();
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
                    }
                }
            }
        });
    }
    
    private void reset() {
        frameCount = 0;
        gameOver = false;
        currentDirection = RIGHT;
        lastDirection = LEFT;
        score = 0;

        snakeBody.clear();
        snakeBody.add(new Point(COLUMNS / 2 - 1, ROWS / 2 - 1));
        snakeHead = snakeBody.get(0);

        segmentsToGrow = 2;
        generateFood();
        
        timeline.play();

    }

    private void run() {
        if (frameCount % frameTickRatio == 0) {
            if (gameOver) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.setFont(new Font("Digital-7", 70));
                graphicsContext.fillText("Game Over", WIDTH / 3.5, HEIGHT / 2);
    
                reset();
                return;
            }

            moveSnake();
            eatFood();
            checkGameOver();
            
            drawBackground();
            drawFood();
            drawSnake();
            //drawScore();

        }

        frameCount++;
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

    private void generateFood() {
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
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x >= COLUMNS || snakeHead.y >= ROWS) {
            gameOver = true;
            return;
        }

        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.equals(snakeBody.get(i))) {
                gameOver = true;
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
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(foodPos.x * SQUARE_SIZE, foodPos.y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void drawSnake() {
        graphicsContext.setFill(Color.web("4674E9"));
        graphicsContext.fillRoundRect(
            snakeHead.x * SQUARE_SIZE,
            snakeHead.y * SQUARE_SIZE,
            SQUARE_SIZE - 1, SQUARE_SIZE - 1,
            35, 35
        );

        for (int i = 1; i < snakeBody.size(); i++) {
            graphicsContext.fillRoundRect(
                snakeBody.get(i).x * SQUARE_SIZE,
                snakeBody.get(i).y * SQUARE_SIZE,
                SQUARE_SIZE - 1, SQUARE_SIZE - 1,
                20, 20
            );
        }
    }
    
    private void drawBackground() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                if ((y + x) % 2 == 0) {
                    graphicsContext.setFill(Color.web("AAD751"));
                } else {
                    graphicsContext.setFill(Color.web("A2D149"));
                }
                graphicsContext.fillRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    private void drawScore() {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("Digital-7", 35));
        graphicsContext.fillText("Score: " + score, 10, 35);
    }
    
    class Point {
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

}
