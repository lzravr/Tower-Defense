package data;

import static helpers.RenderingEngine.*;
import static helpers.RenderingEngine.TILE_SIZE;

import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;

public class Player {
	public static int Cash, Lives;
	private TileGrid grid;
	private WaveManager waveManager;
	private CopyOnWriteArrayList<Tower> towerList;
	private boolean leftMouseButton = false;
	private boolean rightMouseButton = false;
	private Tower tempTower;
	private boolean holdingTower;

	public Player(TileGrid grid, WaveManager waveManager) {
		this.holdingTower = false;
		this.tempTower = null;
		this.grid = grid;
		this.waveManager = waveManager;
		this.towerList = new CopyOnWriteArrayList<Tower>();
	}

	public void setup() {
		Cash = 25;
		Lives = 10;
	}

	public static boolean modifyCash(int amount) {
		if (Cash + amount >= 0) {
			Cash += amount;
			System.out.println(Cash);
			return true;
		}
		System.out.println(Cash);
		return false;

	}

	public static void modifyLives(int amount) {
		Lives += amount;
	}

	public void SetTile() {
		grid.SetTile((int) Math.floor(Mouse.getX() / TILE_SIZE),
				(int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE), TileType.Road);
	}

	public void Update() {
		if (holdingTower) {
			tempTower.setX(getMouseTile().getX());
			tempTower.setY(getMouseTile().getY());
			tempTower.draw();
		}

		// updejtuje sve tower-e na mapi
		for (Tower t : towerList) {
			t.update();
			t.draw();
			t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
		}
		
		

		// Mouse input
		if (Mouse.isButtonDown(0) && !leftMouseButton) {
			placeTower();
		}
		if (Mouse.isButtonDown(1) && !rightMouseButton) {
			placeTower();
		}

		leftMouseButton = Mouse.isButtonDown(0);
		rightMouseButton = Mouse.isButtonDown(1);
		// KeyBoard input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(0.2f);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(-0.2f);
			}
		}
	}

	private void placeTower() {
		Tile currentTile = getMouseTile();
		if (holdingTower)
			if (!currentTile.isOccupied() && modifyCash(-tempTower.type.getCost())){
				towerList.add(tempTower);
				holdingTower = false;
				currentTile.setOccupied(true);
				tempTower = null;
			}	

	}

	public void pickTower(Tower t) {
		tempTower = t;
		holdingTower = true;
	}

	private Tile getMouseTile() {
		return grid.getTile((int) Mouse.getX() / TILE_SIZE, (int) (HEIGHT - Mouse.getY() - 1) / TILE_SIZE);
	}

	public WaveManager getWaveManager() {
		return waveManager;
	}
	
	
}
