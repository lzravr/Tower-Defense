package data;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;
import helpers.TextureBank;

import static helpers.RenderingEngine.*;

public class MainMenu {
	
	private Texture background;
	private UI menuUI;
	public MainMenu(){
		background = TextureBank.splashScreen;
		menuUI = new UI();
		menuUI.addButton("Play", TextureBank.playButton, WIDTH / 2 - 256, (int) (HEIGHT * 0.65f));
	}
	
	private void updateButtons(){
		if (Mouse.isButtonDown(0)){
			if(menuUI.isButtonClicked("Play")){
				System.out.println("play clicked");
				StateManager.setState(GameState.GAME);
			}
		}
	}
	
	public void update(){
		drawQuadTex(background, 0, 0, 1024, 512);
		menuUI.draw();
		updateButtons();
	}
}
