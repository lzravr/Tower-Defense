package data;

import static helpers.RenderingEngine.*;
import static helpers.RenderingEngine.drawQuadTexRot;
import static helpers.Clock.Delta;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import helpers.TextureBank;

public abstract class Tower implements Entity {

	protected float x, y, timeSinceLastShot, angle;
	protected int width, height;
	protected Creep target;
	protected Texture baseTexture, cannonTexture;
	protected CopyOnWriteArrayList<Creep> enemies;
	protected CopyOnWriteArrayList<Projectile> projectiles;
	private boolean targeted;
	public TowerType type;
	
	public Tower(){
		
	}
	
	public Tower(TowerType type, Tile startTile, CopyOnWriteArrayList<Creep> enemies) {
		baseTexture = type.towerBase;
		cannonTexture = type.towerCannon;
		this.type = type;
		this.angle = 0f;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = startTile.getWidth();
		this.height = startTile.getHeight();
		this.enemies = enemies;
		this.targeted = false;
		this.timeSinceLastShot = 0;
		this.projectiles = new CopyOnWriteArrayList<Projectile>();
	}

	// stavio sam da crta iz dva dela, on crta sam bbazu (izgleda)

	private Creep acquireTarget() {
		Creep closest = null;
		// proizvoljna vrednost veca od dimenzija ekrana
		float closestDistance = 10000;
		// petlja nalazi najblizeg kripa koji je u dometu tower-a
		for (Creep e : enemies) {
			if (isInRange(e) && findDistance(e) < closestDistance && e.isAlive()) {
				closest = e;
				closestDistance = findDistance(e);
			}
		}
		// ako postoji takav krip
		if (closest != null)
			targeted = true;
		return closest;
	}

	private float findDistance(Creep e) {
		// zasto ne koristi rastojanje izmedju dve tacke, nesto mi je hm hm
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		return xDistance + yDistance;
	}

	protected boolean isInRange(Creep e) {
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		if (xDistance <= type.getRange() && yDistance <= type.getRange())
			return true;
		return false;
	}

	private float calcAngle() {
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		return (float) Math.toDegrees(angleTemp) - 90;
	}

	public abstract void shoot(Creep target);

	public void updateEnemyList(CopyOnWriteArrayList<Creep> enemyList) {
		enemies = enemyList;
	}

	public void update() {
		if (!targeted) {
			for (Projectile p : projectiles) {
				if (!p.isAlive()){
					projectiles.remove(p);
				}
			}
			target = acquireTarget();
		} else {
			if (isInRange(target)) {
				angle = calcAngle() - 50;
				if (timeSinceLastShot > type.getAttackSpeed()) {
					shoot(target);
					timeSinceLastShot = 0;
				}
			}
			else targeted = false;
		}

		if (target == null || target.isAlive() == false)
			targeted = false;

		if (targeted == true) {

			timeSinceLastShot += Delta();

			for (Projectile p : projectiles) {
				p.update();
			}
			draw();
			
		}
	}

	// skracena verzija stvaranja projektila
	public void spawnProjectile(ProjectileType type) {
		projectiles.add(new Projectile(type, target, x + type.texture.getImageWidth() / 2,
				y + type.texture.getImageHeight() / 2, width, height, enemies));
	}

	public boolean isHoveredOn(){
		float mouseX = Mouse.getX();
		float mouseY = HEIGHT - Mouse.getY() + 1;
		
		if (mouseX > x && Mouse.getX() < x + TILE_SIZE && mouseY > y
				&& mouseY < y + TILE_SIZE) {
			return true;
		}
		return false;
	}
	
	public void draw() {
		drawQuadTex(baseTexture, x, y, width, height);
		drawQuadTexRot(cannonTexture, x, y, width, height, angle);
		if(isHoveredOn()){
			drawQuadTex(TextureBank.Range, x - (type.range*2-TILE_SIZE)/2, y - (type.range*2-TILE_SIZE)/2, type.range*2, type.range*2);
		}
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
