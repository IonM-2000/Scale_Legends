package scale_legends.gamemodes;

import java.util.Vector;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ObstaclesMode extends GameMode {
	private final int MAX_DISTANCE_FROM_HEAD = 5;

	Vector<Point> obstacles = new Vector<Point>();

	public ObstaclesMode(int width, int height) {
		super(width, height);
	}

	@Override
	public void reset() {
		super.reset();

		if (obstacles != null)
			obstacles.clear();
	}

	@Override
	protected void eatFood() {
        if (snakeHead.x == foodPos.x && snakeHead.y == foodPos.y) {
            segmentsToGrow++;
            score += 1;
            generateFood();
			generateObstacle();
        }
    }

	private void generateObstacle() {
        Vector<Point> possible_positions = new Vector<Point>();

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
				Point new_obsPoint = new Point(x, y);
				
                if (snakeBody.contains(new_obsPoint) == false &&
					manhattanDistance(new_obsPoint, snakeHead) >= MAX_DISTANCE_FROM_HEAD &&
					new_obsPoint.equals(foodPos) == false &&
					obstacles.contains(new_obsPoint) == false)
				{
					possible_positions.add(new_obsPoint);
				}
            }
        }
		
        if (possible_positions.isEmpty() == false) {
			int random_index = (int) (Math.random() * possible_positions.size());
			
			Point new_obstacle = new Point(
				possible_positions.get(random_index).x,
				possible_positions.get(random_index).y
			);
			
			obstacles.add(new_obstacle);
        }
	}

	@Override
    protected void generateFood() {
        Vector<Point> possible_positions = new Vector<Point>();

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
				Point newFood = new Point(x, y);

                if (snakeBody.contains(newFood) == false && obstacles.contains(newFood) == false) {
                    possible_positions.add(newFood);
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

	@Override
    protected void checkGameOver() {
		super.checkGameOver();
		
		if (obstacles.contains(snakeHead)) {
			gameStateChange(GameState.LOSS);
		}
    }

	@Override
	public void drawGamePlay(GraphicsContext graphicsContext) {
		super.drawGamePlay(graphicsContext);
		
		drawObstacles(graphicsContext);
	}

	protected void drawObstacles(GraphicsContext graphicsContext) {
		for (Point obstacle: obstacles) {
            graphicsContext.setFill(Color.GRAY);
            graphicsContext.fillRect(obstacle.x * SQUARE_SIZE, (obstacle.y + 1) * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
		}
	}
	
	@Override
	protected void drawGameMode(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("Digital-7", 35));
        graphicsContext.fillText("OBSTACLES", WIDTH * 0.4, 40);
	}

	private int manhattanDistance(Point a, Point b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}

}
