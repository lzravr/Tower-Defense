package UI;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.*;
import static helpers.RenderingEngine.*;

import java.awt.Font;
public class Button {
	
	private String name, caption;
	private Texture texture;
	private int x, y, width, height;
	private TrueTypeFont font;
	private Font awtFont;
	
	public Button(){
		awtFont = new Font("Arial", Font.BOLD, 12);
		font = new TrueTypeFont(awtFont, false);
	}
	
	public Button(String name, String caption, Texture texture, int x, int y, int width, int height) {
		this();
		this.caption = caption;
		this.name = name;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Button(String name, Texture texture, int x, int y) {
		this();
		this.name = name;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.width = texture.getImageWidth();
		this.height = texture.getImageHeight();
	}
	
	

	public Button(String name, String caption, Texture texture, int x, int y) {
		this();
		this.name = name;
		this.width = texture.getImageWidth();
		this.height = texture.getImageHeight();
		this.caption = caption;
		this.texture = texture;
		this.x = x;
		this.y = y;
	}
	
	public void drawString(int x, int y, String text){
		if(caption == null) return;
		font.drawString(x, y, text);
	}
	
	public void draw(){
		drawQuadTex(texture, x, y, width, height);
		drawString(x, y, caption);
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
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

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	
	
	
	
}
