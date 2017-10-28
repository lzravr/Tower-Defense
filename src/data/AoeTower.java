package data;

import static helpers.RenderingEngine.HEIGHT;
import static helpers.RenderingEngine.TILE_SIZE;
import static helpers.RenderingEngine.drawQuadTex;
import static helpers.RenderingEngine.drawQuadTexRot;
import static helpers.Clock.Delta;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import helpers.TextureBank;

public abstract class AoeTower extends Tower {

	protected CopyOnWriteArrayList<AoeProjectile> projectiles;
	
	public AoeTower(TowerType type, Tile startTile, CopyOnWriteArrayList<Creep> enemies) {
		baseTexture = type.towerBase;
		cannonTexture = type.towerCannon;
		this.type = type;
		this.angle = 0f;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = startTile.getWidth();
		this.height = startTile.getHeight();
		this.enemies = enemies;
		this.timeSinceLastShot = 0;
		this.projectiles = new CopyOnWriteArrayList<AoeProjectile>();
	}

	protected boolean isInRange(CopyOnWriteArrayList<Creep> e) {
		for (Creep creep : e) {
			if (creep.colisionWithCircle(x + TILE_SIZE / 2, y + TILE_SIZE / 2, type.range))
				return true;
		}
		return false;
	}

	@Override
	public boolean isHoveredOn() {
		float mouseX = Mouse.getX();
		float mouseY = HEIGHT - Mouse.getY() + 1;

		if (mouseX > x && Mouse.getX() < x + TILE_SIZE && mouseY > y && mouseY < y + TILE_SIZE) {
			return true;
		}
		return false;
	}

	@Override
	public void draw() {
		drawQuadTex(baseTexture, x, y, width, height);
		drawQuadTexRot(cannonTexture, x, y, width, height, angle += 3);
		if (isHoveredOn()) {
			drawQuadTex(TextureBank.Range, x - (type.range * 2 - TILE_SIZE) / 2, y - (type.range * 2 - TILE_SIZE) / 2,
					type.range * 2, type.range * 2);
		}
	}

	public abstract void shoot(Creep target);

	@Override
	public void spawnProjectile(ProjectileType type) {
			projectiles.add(new AoeProjectile(type, x, y, this.type.range, (float) this.type.attackSpeed, enemies));
	}

	@Override
	public void update() {

		if (isInRange(enemies)) {
			if (timeSinceLastShot > type.getAttackSpeed()) {
				shoot(null);
				timeSinceLastShot = 0;
			}
		}else{
			for (AoeProjectile p : projectiles) {
				if (!p.isAlive()){
					projectiles.remove(p);
				}
			}
		}

		timeSinceLastShot += Delta();
		for (AoeProjectile p : projectiles) {
			p.update();
		}
		draw();
	}

	public void updateEnemyList(CopyOnWriteArrayList<Creep> enemyList) {
		enemies = enemyList;
	}
}
