package scale_legends.gamemodes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ClassicMode extends GameMode {

	public ClassicMode(int width, int height) {
		super(width, height);
	}
	
	@Override
	protected void drawGameMode(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("Digital-7", 35));
        graphicsContext.fillText("CLASSIC", WIDTH * 0.3, 40);
	}

}
