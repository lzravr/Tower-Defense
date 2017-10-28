package helpers;

import data.Game;
import data.GameOver;
import data.MainMenu;
import data.Player;
import static helpers.Clock.*;

import org.newdawn.slick.SlickException;

public class StateManager {
	
	public static enum GameState {
		MAINMENU, GAME, GAMEOVER
	}
	
	static int map[][] = {
			{0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0}, 
			{0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,0,0},
			{0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,1,0,0,0,0,1,1,1,1,1,1,1,0,0},
			{0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0},
			{0,0,1,1,1,1,1,0,0,0,0,1,1,1,0,0,0,0,0,0},
			{0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
			{0,0,1,1,1,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0},
			{0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
	};
	
	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu mainMenu;
	public static Game game;
	public static GameOver gameOver;
	
	public static void update() {
		switch (gameState) {
		case MAINMENU:
			if (mainMenu == null)
				mainMenu = new MainMenu();
			mainMenu.update();
			break;
		case GAME:
			if (game == null)
				game = new Game(map);
			game.update();
			if(Player.Lives <= 0){
				game = null;
				gameState = GameState.GAMEOVER;
			}
			break;
		case GAMEOVER:
			if(gameOver == null)
				gameOver = new GameOver();
			gameOver.update();			
			//gameState = GameState.MAINMENU;
			break;
		default:
			break;
		}
	}
	
	public static void setState(GameState newState){
		gameState = newState;
	}
}
