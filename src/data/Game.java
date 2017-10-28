package data;

import static helpers.RenderingEngine.HEIGHT;
import static helpers.RenderingEngine.TILE_SIZE;
import static helpers.RenderingEngine.drawQuadTex;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.Button;
import UI.UI;
import helpers.TextureBank;

public class Game {

	public static TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	private UI gameUI;
	
	// temp 
	
	
	public Game(int[][] map) {
		grid = new TileGrid(map);

		waveManager = new WaveManager(10);
		
		
		
		player = new Player(grid, waveManager);
		player.setup();
		setupUI();
	}

	private void updateUI() {
		gameUI.draw();
		int waveNumber = player.getWaveManager().getWaveNumber();
		gameUI.getMenu("Info").getMenuButton("Gold").setCaption(""+Player.Cash);
		gameUI.getMenu("Info").getMenuButton("Wave").setCaption(""+ waveNumber);
		gameUI.getMenu("Info").getMenuButton("Lives").setCaption(""+Player.Lives);
		
		if(player.getWaveManager().getCurrentWave().isCompleted()){
		int nextWaveIn = (int) (player.getWaveManager().getTimeBetweenWaves() - player.getWaveManager().getTimeSinceLastWave());
			CreepType[] creepTypes = player.getWaveManager().getCreepTypes();
			Texture temp = creepTypes[waveNumber % creepTypes.length].getTexture();	
			gameUI.getMenu("Info").getMenuButton("Wave").setTexture(temp);
			gameUI.drawString(650, 240,"" + nextWaveIn);
		}
		
		
		
		if (Mouse.next()) {
			boolean mouseClicked = Mouse.isButtonDown(0);
			if (mouseClicked) {
				if (gameUI.getMenu("TowerPicker").isButtonClicked("Orange Tower")) {
					player.pickTower(new OrangeTower(TowerType.orangeTower, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
				if (gameUI.getMenu("TowerPicker").isButtonClicked("Green Tower")) {
					player.pickTower(new GreenTower(TowerType.greenTower, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
				if (gameUI.getMenu("TowerPicker").isButtonClicked("Purple Tower")) {
					player.pickTower(new PurpleTower(TowerType.purpleTower, grid.getTile(2, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
				if (gameUI.getMenu("TowerPicker").isButtonClicked("Blue Tower")) {
					player.pickTower(new BlueTower(TowerType.blueTower, grid.getTile(2, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
				if (gameUI.getMenu("TowerPicker").isButtonClicked("Red Tower")) {
					player.pickTower(new RedTower(TowerType.redTower, grid.getTile(2, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
				if (gameUI.getMenu("Info").isButtonClicked("Wave") && player.getWaveManager().getCurrentWave().isCompleted()) {
					player.getWaveManager().spawnNextWave();
				}
			}
		}
	}

	private void setupUI() {
		gameUI = new UI();
		
		gameUI.createMenu("TowerPicker", 640, TILE_SIZE, TILE_SIZE, HEIGHT, 1,  0);
		gameUI.getMenu("TowerPicker").addButton(new Button("Orange Tower","" + TowerType.orangeTower.getCost(), TextureBank.orangeTowerFull, 0, 0));
		gameUI.getMenu("TowerPicker").addButton(new Button("Green Tower","" + TowerType.greenTower.getCost() , TextureBank.greenTowerFull, 0, 0));
		gameUI.getMenu("TowerPicker").addButton(new Button("Purple Tower","" + TowerType.purpleTower.getCost() , TextureBank.purpleTowerFull, 0, 0));
		gameUI.getMenu("TowerPicker").addButton(new Button("Blue Tower","" + TowerType.blueTower.getCost() , TextureBank.blueTowerFull, 0, 0));
		gameUI.getMenu("TowerPicker").addButton(new Button("Red Tower","" + TowerType.redTower.getCost() , TextureBank.redTowerFull, 0, 0));
		gameUI.createMenu("Info", 640, 7*TILE_SIZE, TILE_SIZE, TILE_SIZE, 1, 0);
		
		
		gameUI.getMenu("Info").addButton(new Button("Wave","" + player.getWaveManager().getWaveNumber(), TextureBank.creepRose, 0, 0));
		gameUI.getMenu("Info").addButton(new Button("Gold","" + Player.Cash, TextureBank.coin, 0, 0));
		gameUI.getMenu("Info").addButton(new Button("Lives","" + Player.Lives, TextureBank.heart, 0, 0));
	}

	public void update() {
		drawQuadTex(TextureBank.towerPicker, 640, 0, TILE_SIZE, HEIGHT + 32);
		grid.draw();
		waveManager.update();
		player.Update();
		gameUI.draw();
		updateUI();
	}

	public Player getPlayer() {
		return player;
	}	
	
	
	
}
