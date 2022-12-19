package scale_legends.gamemodes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PortalsMode extends GameMode {

	public PortalsMode(int width, int height) {
		super(width, height);
	}
	
	@Override
	protected void drawGameMode(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("Digital-7", 35));
        graphicsContext.fillText("PORTALS", WIDTH * 0.4, 40);
	}

}
