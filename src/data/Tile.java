package data;

import org.newdawn.slick.opengl.Texture;

import helpers.TextureBank;

import static helpers.RenderingEngine.*;

public class Tile {
	
	private float x, y;
	private int width, height;
	private Texture texture;
	private TileType type;
	private boolean occupied;
	
	public Tile(float x, float y, int w, int h, TileType t) {
		this.occupied = false;
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		type = t;
		if(type.buildable)
			occupied = false;
		else
			occupied = true;
		if (type == TileType.Land)
			texture = TextureBank.land;
		else
			texture = TextureBank.road;
	}
	
	
	public int getXPlace(){
		return (int) x/TILE_SIZE; 
	}
	
	public int getYPlace(){
		return (int) y/TILE_SIZE;
	}
	
	public void draw() {
		drawQuadTex(texture, x, y, width, height);
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

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}


	public boolean isOccupied() {
		return occupied;
	}


	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	
	
}
