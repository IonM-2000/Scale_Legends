package scale_legends.gamemodes;

import java.util.Vector;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PortalsMode extends GameMode {

	private Point alternateFood;

	public PortalsMode(int width, int height) {
		super(width, height);

		alternateFood = new Point(-1, -1);
	}

	@Override
    protected void eatFood() {
        if (snakeHead.equals(foodPos)) {
            segmentsToGrow++;
            score += 1;

			snakeHead.x = alternateFood.x;
			snakeHead.y = alternateFood.y;
			alternateFood = new Point(-1, -1);
            generateFood();
        } else if (snakeHead.equals(alternateFood)) {
            segmentsToGrow++;
            score += 1;

			snakeHead.x = foodPos.x;
			snakeHead.y = foodPos.y;
			alternateFood = new Point(-1, -1);
            generateFood();
		}
    }

	@Override
	public void generateFood() {
		super.generateFood();

		if (isRunning() == false) {
			return;
		}

        Vector<Point> possible_positions = new Vector<Point>();

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
				Point newFood = new Point(x, y);

                if (snakeBody.contains(newFood) == false) {
                    possible_positions.add(newFood);
                }
            }
        }

		possible_positions.remove(foodPos);

        if (possible_positions.isEmpty()) {
            foodPos.y = -1;
            foodPos.x = -1;
            alternateFood.y = -1;
            alternateFood.x = -1;

			gameStateChange(GameState.WIN);
            return;
        }

        int random_index = (int) (Math.random() * possible_positions.size());
        
        alternateFood.x = possible_positions.get(random_index).x;
        alternateFood.y = possible_positions.get(random_index).y;
	}
	
	@Override
	protected void drawFood(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.web("6700A3"));
        graphicsContext.fillRoundRect(
            alternateFood.x * SQUARE_SIZE, (alternateFood.y + 1) * SQUARE_SIZE,
            SQUARE_SIZE, SQUARE_SIZE,
            SQUARE_SIZE, SQUARE_SIZE
        );
        graphicsContext.fillRoundRect(
            foodPos.x * SQUARE_SIZE, (foodPos.y + 1) * SQUARE_SIZE,
            SQUARE_SIZE, SQUARE_SIZE,
            SQUARE_SIZE, SQUARE_SIZE
        );
	}

	@Override
	protected void drawGameMode(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("Digital-7", 35));
        graphicsContext.fillText("PORTALS", WIDTH * 0.4, 40);
	}

}
