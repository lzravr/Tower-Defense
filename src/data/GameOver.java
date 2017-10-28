package data;

import static helpers.RenderingEngine.*;
import static helpers.Clock.*;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.TextureBank;
import helpers.StateManager.GameState;

public class GameOver {

	private Texture background;
	private float elapsed;

	public GameOver() {
		background = TextureBank.splashScreenEnd;
		elapsed = 0;
	}

	public void update() {
		drawQuadTex(background, 0, 0, 1024, 512);
		elapsed += Delta();
		if (elapsed > 10f) {
			
			StateManager.setState(GameState.MAINMENU);
		}

	}

}
