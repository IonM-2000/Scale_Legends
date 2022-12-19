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

}
