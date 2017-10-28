package data;

import org.newdawn.slick.opengl.Texture;

import helpers.TextureBank;

import static helpers.RenderingEngine.*;
import static helpers.Clock.*;
import java.util.ArrayList;

/**
 * @author Dzoni
 *
 */
public class Creep implements Entity {

	private int width, height, currentCheckPoint;
	private float x, y, currentHealth;
	private Texture healthBar;
	private float[] effectsTimer;
	public Tile startTile;
	private TileGrid grid;
	private CreepType creepType;
	private ArrayList<CheckPoint> checkPoints;
	private boolean first, alive;
	private int[] directions;

	public Creep(CreepType creepType, Tile startTile, TileGrid grid) {
		this.first = true;
		this.alive = true;
		this.creepType = creepType;
		this.healthBar = TextureBank.healthBarGreen;
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = creepType.getTexture().getImageWidth();
		this.height = creepType.getTexture().getImageHeight();
		this.grid = grid;
		this.currentHealth = creepType.getMaxHealth();
		this.effectsTimer = new float[CSEType.numberOfTypes];
		for (int i = 0; i < CSEType.numberOfTypes; i++) {
			this.effectsTimer[i] = 0;
		}
		this.checkPoints = new ArrayList<CheckPoint>();
		this.directions = new int[2];
		// x direction
		this.directions[0] = 0;
		// y direction
		this.directions[1] = 0;
		directions = findNextD(startTile);
		this.currentCheckPoint = 0;
		populateCheckPointList();
	}

	/**
	 * @return Da li je krip stigao na sledeci checkPoint
	 */
	private boolean checkPointReached() {
		boolean reached = false;

		Tile t = checkPoints.get(currentCheckPoint).getTile();

		// +- 3 je dodato kao margina da krip ne bi slucajno preskocio
		// checkpoint pri velikim brzinama
		if (x > t.getX() - 3 && x < t.getX() + 3 && y > t.getY() - 3 && y < t.getY() + 3) {
			reached = true;

			// centrira kripa na tile ukoliko postoji odstupanje
			x = t.getX();
			y = t.getY();
		}
		return reached;
	}

	/**
	 * @return nista
	 * @opis skenira mapu i stvara arrayList checkPoint-ova
	 */
	private void populateCheckPointList() {
		//
		checkPoints.add(findNextC(startTile, directions = findNextD(startTile)));

		int counter = 0;
		boolean cont = true;

		while (cont) {

			// trenutni smer
			int[] currentD = findNextD(checkPoints.get(counter).getTile());

			// proverava da li postoji smer, 2 je znak da ne postoji i da je
			// metoda zavrsena
			if (currentD[0] == 2) {
				cont = false;
			} else {

				// iterativno trazi checkPoint-ove
				directions = findNextD(checkPoints.get(counter).getTile());
				checkPoints.add(findNextC(checkPoints.get(counter).getTile(), directions));
			}
			counter++;
		}
	}

	public void die() {
		alive = false;
	}

	public void draw() {
		float healthPercentage = currentHealth / creepType.getMaxHealth();
		drawQuadTex(creepType.getTexture(), x, y, width, height);

		// ovo sam eksperimentalno utvrdio da izgleda "dobro"

		// odredjuje boju healthBar-a
		if (healthPercentage > 0.6) {
			healthBar = TextureBank.healthBarGreen;
		} else if (healthPercentage > 0.3 && healthPercentage <= 0.6) {
			healthBar = TextureBank.healthBarYellow;
		} else {
			healthBar = TextureBank.healthBarRed;
		}
		drawQuadTex(healthBar, x + width * 0.05f, y, width * 0.9f * healthPercentage, 4);
	}

	public void update() {

		// pita da li je prvi put pozvana metoda
		if (first)
			first = false;
		else {
			if (checkPointReached()) {
				// stigao do poslednjeg checkPoint-a
				if (currentCheckPoint + 1 == checkPoints.size()) {
					endReached();
				} else
					currentCheckPoint++;
			}
			if (effectsTimer[CSEType.stun] > 0) {
				effectsTimer[CSEType.stun] -= Delta();
				x += Delta() * checkPoints.get(currentCheckPoint).getxDirection()
						* (creepType.getSpeed() * (1 + CSEType.STUN.getSpeedModifier()));
				y += Delta() * checkPoints.get(currentCheckPoint).getyDirection()
						* (creepType.getSpeed() * (1 + CSEType.STUN.getSpeedModifier()));
				if (effectsTimer[CSEType.stun] < 0)
					effectsTimer[CSEType.stun] = 0;
			} else if (effectsTimer[CSEType.slowlvl2] > 0) {
				effectsTimer[CSEType.slowlvl2] -= Delta();
				x += Delta() * checkPoints.get(currentCheckPoint).getxDirection()
						* (creepType.getSpeed() * (1 + CSEType.SLOWLVL2.getSpeedModifier()));
				y += Delta() * checkPoints.get(currentCheckPoint).getyDirection()
						* (creepType.getSpeed() * (1 + CSEType.SLOWLVL2.getSpeedModifier()));
				if (effectsTimer[CSEType.slowlvl2] < 0)
					effectsTimer[CSEType.slowlvl2] = 0;
			} else if (effectsTimer[CSEType.slowlvl1] > 0) {
				effectsTimer[CSEType.slowlvl1] -= Delta();
				x += Delta() * checkPoints.get(currentCheckPoint).getxDirection()
						* (creepType.getSpeed() * (1 + CSEType.SLOWLVL1.getSpeedModifier()));
				y += Delta() * checkPoints.get(currentCheckPoint).getyDirection()
						* (creepType.getSpeed() * (1 + CSEType.SLOWLVL1.getSpeedModifier()));
				if (effectsTimer[CSEType.slowlvl1] < 0)
					effectsTimer[CSEType.slowlvl1] = 0;
			} else {
				// kretanje

				x += Delta() * checkPoints.get(currentCheckPoint).getxDirection() * creepType.getSpeed();
				y += Delta() * checkPoints.get(currentCheckPoint).getyDirection() * creepType.getSpeed();

			}
		}

	}

	/**
	 * 
	 * @param s
	 *            Trenutni Tile na kome se krip nalazi
	 * @param dir
	 *            jedinicni vektor smera u kom se trenutno krece
	 * @return
	 */
	private CheckPoint findNextC(Tile s, int[] dir) {
		Tile next = null;
		CheckPoint c = null;
		// Boolean vrednost koja odredjuje da li je nadjen sledeci checkPoint
		boolean found = false;

		int counter = 1;

		while (!found) {

			int potentialCPXCoord = s.getXPlace() + dir[0] * counter;
			int potentialCPYCoord = s.getYPlace() + dir[1] * counter;

			// ako sledeci tile u tom smeru u kom se krip krece se razlikuje od
			// predhodnog tile-a u tom smeru
			if ((potentialCPXCoord == grid.getTilesWide() || potentialCPYCoord == grid.getTilesHigh())
					|| s.getType() != grid.getTile(potentialCPXCoord, potentialCPYCoord).getType()) {
				found = true;
				counter -= 1;
				next = grid.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter);
			}

			counter++;
		}
		c = new CheckPoint(next, dir[0], dir[1]);
		return c;
	}

	/**
	 * Oduzima zivot igracu i krip umire
	 */
	private void endReached() {
		Player.modifyLives(-1);
		die();
	}

	/**
	 * 
	 * @param Tile
	 *            na kome se krip trenutno nalazi
	 * @return dvodimenzionalni vektor smera
	 */
	private int[] findNextD(Tile s) {
		int[] dir = new int[2];
		// gornja susedna
		// desna susedna
		// donja susedna
		// leva susedna

		// null pointer ne moze da se desi jer grid.GetTile() metoda ima proveru
		// i vraca NULL TileType ako se taj uslov ispuni
		Tile u = grid.getTile(s.getXPlace(), s.getYPlace() - 1);
		Tile r = grid.getTile(s.getXPlace() + 1, s.getYPlace());
		Tile d = grid.getTile(s.getXPlace(), s.getYPlace() + 1);
		Tile l = grid.getTile(s.getXPlace() - 1, s.getYPlace());

		if (u != null && s.getType() == u.getType() && directions[1] != 1) {
			dir[0] = 0;
			dir[1] = -1;
		} else if (r != null && s.getType() == r.getType() && directions[0] != -1) {
			dir[0] = 1;
			dir[1] = 0;
		} else if (l != null && s.getType() == l.getType() && directions[0] != 1) {
			dir[0] = -1;
			dir[1] = 0;
		} else if (d != null && s.getType() == d.getType() && directions[1] != -1) {
			dir[0] = 0;
			dir[1] = 1;
		} else { // ako ne moze da nadje sledeci smer
			dir[0] = 2;
			dir[1] = 2;
		}

		return dir;
	}

	/**
	 * 
	 * @param damage
	 *            prima damage, ako umre daje igracu pare
	 */
	public void damage(float damage) {
		if(effectsTimer[CSEType.dmg_amp] > 0)
			damage *= 1+CSEType.DMG_AMP.getDamageModifier();
		currentHealth -= damage;
		if (currentHealth <= 0) {
			die();
			Player.modifyCash(creepType.getBounty());
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getHealth() {
		return currentHealth;
	}

	public void setHealth(float health) {
		this.currentHealth = health;
	}

	public boolean colisionWithCircle(float xCenter, float yCenter, int radius) {
		float myXCenter = x + width / 2;
		float myYCenter = y + height / 2;
		float dist = (float) Math
				.sqrt((myXCenter - xCenter) * (myXCenter - xCenter) + (myYCenter - yCenter) * (myYCenter - yCenter));
		if (dist < radius) {
			return true;
		}
		return false;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Tile getStartTile() {
		return startTile;
	}

	public void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public TileGrid getGrid() {
		return grid;
	}

	public void setGrid(TileGrid grid) {
		this.grid = grid;
	}

	public boolean isAlive() {
		return alive;
	}

	public void updateEffectTimer(int effectCode) {
		if (effectCode != -1) {
			effectsTimer[effectCode] = CSEType.EFFECTS[effectCode].getDuration();
		}
	}

}
