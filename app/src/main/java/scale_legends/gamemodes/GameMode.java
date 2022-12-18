package scale_legends.gamemodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class GameMode {
    private final int SQUARE_SIZE = 50;
    private int WIDTH;
    private int HEIGHT;
    private int ROWS;
    private int COLUMNS;

    private GameState gameState;
	private GameState prevGameState;

    private List<Point> snakeBody;
    private Point snakeHead;
    private Point foodPos;
    private Direction currentDirection;
    private Direction lastDirection;
    private int score;
    private int segmentsToGrow;

	public GameMode(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		
        ROWS = HEIGHT / SQUARE_SIZE - 1;
        COLUMNS = WIDTH / SQUARE_SIZE;

        foodPos = new Point(-1, -1);
        snakeBody = new ArrayList<Point>();
        
		prevGameState = GameState.LIMBO;
        gameState = GameState.LIMBO;
        reset();
	}
    
    public void reset() {
        currentDirection = Direction.RIGHT;
        lastDirection = Direction.LEFT;
        score = 0;
    }

    public void startGameplay() {
		gameStateChange(GameState.RUNNING);

        snakeBody.clear();
        snakeBody.add(new Point(COLUMNS / 2 - 1, ROWS / 2 - 1));
        snakeHead = snakeBody.get(0);
        segmentsToGrow = 2;

        generateFood();
    }

    public void run() {
		if (isRunning()) {
			checkGameOver();
			moveSnake();
			eatFood();
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

        if (possible_positions.isEmpty()) {
            foodPos.y = -1;
            foodPos.x = -1;

			gameStateChange(GameState.WIN);

            return;
        }

        int random_index = (int) (Math.random() * possible_positions.size());
        
        foodPos.y = possible_positions.get(random_index).y;
        foodPos.x = possible_positions.get(random_index).x;
    }

	public boolean handleInput(KeyCode code) {
		if (code == KeyCode.RIGHT || code == KeyCode.D) {
			if (lastDirection != Direction.LEFT) {
				currentDirection = Direction.RIGHT;
				return true;
			}
		}
		if (code == KeyCode.LEFT || code == KeyCode.A) {
			if (lastDirection != Direction.RIGHT) {
				currentDirection = Direction.LEFT;
				return true;
			}
		}
		if (code == KeyCode.UP || code == KeyCode.W) {
			if (lastDirection != Direction.DOWN) {
				currentDirection = Direction.UP;
				return true;
			}
		}
		if (code == KeyCode.DOWN || code == KeyCode.S) {
			if (lastDirection != Direction.UP) {
				currentDirection = Direction.DOWN;
				return true;
			}
		}
		return false;
	}
    
    private void moveRight() {
        snakeHead.x++;
        lastDirection = Direction.RIGHT;
    }
    private void moveLeft() {
        snakeHead.x--;
        lastDirection = Direction.LEFT;
    }
    private void moveUp() {
        snakeHead.y--;
        lastDirection = Direction.UP;
    }
    private void moveDown() {
        snakeHead.y++;
        lastDirection = Direction.DOWN;
    }

    public void checkGameOver() {
        if (isRunning() == false) {
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
			gameStateChange(GameState.LOSS);
            return;
        }

        for (int i = 1; i < snakeBody.size(); i++) {
            if (nextSnakeHead.equals(snakeBody.get(i))) {
				gameStateChange(GameState.LOSS);
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

	public void draw(GraphicsContext graphicsContext) {
		drawBackground(graphicsContext);
		drawScore(graphicsContext);
		drawFood(graphicsContext);
		drawSnake(graphicsContext);

		drawGameState(graphicsContext);
	}

    private void drawGameState(GraphicsContext graphicsContext) {
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

	private void drawSnake(GraphicsContext graphicsContext) {
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

	private void drawFood(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.web("8F0114"));
        graphicsContext.fillRoundRect(
            foodPos.x * SQUARE_SIZE, (foodPos.y + 1) * SQUARE_SIZE,
            SQUARE_SIZE, SQUARE_SIZE,
            SQUARE_SIZE, SQUARE_SIZE
        );
	}

	private void drawScore(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("Digital-7", 35));
        graphicsContext.fillText("Score: " + score, 10, 40);
	}

	private void drawBackground(GraphicsContext graphicsContext) {
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

	public boolean isRunning() {
		return gameState == GameState.RUNNING;
	}

    private class Point {
        int x, y;

        Point(Point other) {
            this.x = other.x;
            this.y = other.y;
        }

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        boolean equals(Point other) {
            return this.x == other.x && this.y == other.y;
        }
    }

	private enum Direction {
		RIGHT, LEFT,
		UP, DOWN
	}

    public enum GameState {
        LIMBO,
        RUNNING,
        LOSS,
        WIN
    }

	public GameState getGameState() {
		return gameState;
	}

	private void gameStateChange(GameState gameState) {
		prevGameState = this.gameState;
		this.gameState = gameState;
	}

	public boolean isGameStateChanged() {
		boolean answer = gameState != prevGameState;

		prevGameState = gameState;

		return answer;
	}

}
