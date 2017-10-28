package data;

import static helpers.RenderingEngine.*;
import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

public class AoeProjectile {

	private ProjectileType type;
	private Texture texture;
	private float x, y;
	private boolean alive;
	private float linger;
	private int radius, maxRange;
	private CopyOnWriteArrayList<Creep> wave;
	private float xCentar, yCentar;

	// dotat arraylist wave u konstruktoru, izmenjena klasa tower
	public AoeProjectile(ProjectileType type, float x, float y, int maxRange, float linger, CopyOnWriteArrayList<Creep> wave) {
		this.texture = type.texture;
		this.type = type;
		this.maxRange = maxRange;
		this.x = x;
		this.y = y;
		radius = 0;
		alive = true;
		this.linger = linger - maxRange/type.speed;
		this.xCentar = x + TILE_SIZE / 2;
		this.yCentar = y + TILE_SIZE / 2;
		this.wave = wave;
	}

	public void update() {
		if (alive) {
			for (Creep e : wave) {
				if (e.colisionWithCircle(xCentar, yCentar, radius)) {
					e.damage(type.damage);
					if (type.statusEffectCode != -1) {
						e.updateEffectTimer(type.statusEffectCode);
					}
				}
			}
			draw();
			if (radius > maxRange) {
				linger -= Delta();
				if(linger <= 0)
				alive = false;
			}
			else
				radius += type.speed * Delta();
		}
		
	}

	public void draw() {
		drawQuadTex(texture, x - (radius - TILE_SIZE / 2), y - (radius - TILE_SIZE / 2), radius*2, radius*2);
	}

	public int getRadius() {
		return radius;
	}

	public boolean isAlive() {
		return alive;
	}
	
	

}
