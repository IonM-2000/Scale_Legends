package scale_legends.gamemodes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class FreedomMode extends GameMode {

	public FreedomMode(int width, int height) {
		super(width, height);
	}
	
	@Override
	protected void drawGameMode(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("Digital-7", 35));
        graphicsContext.fillText("FREEDOM", WIDTH * 0.4, 40);
	}

	@Override
	protected void checkGameOver() {
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
        
        for (int i = 1; i < snakeBody.size(); i++) {
            if (nextSnakeHead.equals(snakeBody.get(i))) {
				gameStateChange(GameState.LOSS);
                return;
            }
        }
	}
	
	@Override
	protected void moveSnake() {
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

		snakeHead.x = (snakeHead.x + COLUMNS) % COLUMNS;
		snakeHead.y = (snakeHead.y + ROWS) % ROWS;
    }

}
