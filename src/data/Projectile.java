package data;

import static helpers.RenderingEngine.TILE_SIZE;
import static helpers.RenderingEngine.checkCollision;
import static helpers.RenderingEngine.drawQuadTex;
import static helpers.Clock.Delta;


import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

public class Projectile implements Entity {

	private ProjectileType type;
	private Texture texture;
	private float x, y;
	private int width, height;
	private Creep target;
	private float xVelocity, yVelocity;
	private boolean alive;
	private CopyOnWriteArrayList<Creep> wave;
	
	
	//dotat arraylist wave u konstruktoru, izmenjena klasa tower
	public Projectile(ProjectileType type, Creep target, float x, float y, int w, int h, CopyOnWriteArrayList<Creep> wave) {
		texture = type.texture;
		this.target = target;
		this.type = type;
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		this.xVelocity = 0f;
		this.yVelocity = 0f;
		calcDirection();
		alive = true;
		this.wave = wave;
	}
	/**
	 * racuna smer u kom treba projektil da se krece
	 */
	private void calcDirection() {
		float totalAllowedMovement = 1.0f;
		float xDistFromTarget = Math.abs(target.getX() + TILE_SIZE / 4 - x);
		float yDistFromTarget = Math.abs(target.getY() + TILE_SIZE / 4 - y);
		float totalDist = xDistFromTarget + yDistFromTarget;
		float xPercentOfMovement = xDistFromTarget / totalDist;
		xVelocity = xPercentOfMovement;
		yVelocity = totalAllowedMovement - xPercentOfMovement;
		if (target.getX() < x) {
			xVelocity *= -1;
		}
		if (target.getY() < y) {
			yVelocity *= -1;
		}
	}

	
	//ceo wave proverava da li je pogodio metak
	public void update() {
		if (alive) {
			x += Delta() * type.speed * xVelocity;
			y += Delta() * type.speed * yVelocity;
			// calcDirection();

			for (Creep e : wave) {
				if (checkCollision(x, y, width, height, e.getX(), e.getY(), e.getWidth(),
						e.getHeight())) {
					e.damage(type.damage);
					if (type.statusEffectCode != -1){
						e.updateEffectTimer(type.statusEffectCode);
					}
					alive = false;
				}

			}
			draw();
		}
	}

	public void draw() {
		drawQuadTex(texture, x, y, texture.getImageWidth(), texture.getImageHeight());
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}
	public boolean isAlive() {
		return alive;
	}
	
	
}
