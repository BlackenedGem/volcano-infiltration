package levelComponents;

import game_base.Screen;
import game_logic.Camera;
import game_logic.Data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import player.Player;

public class Finish implements LevelObject {
	private int x;
	private int y;
	private int width;
	private int height;
	
	private static BufferedImage img = null;

	public Finish(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 45;
		this.height = 80;
	}
	
	public static void init() {
		//Load image file
		try {
			img = ImageIO.read(new File(Data.getResourceLoc() + "textures/Finish.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		int x_end = x + width;
		int y_end = y + height;
		
		
		if (   (Player.getX() < x_end & Player.getX() > x)   |   ((Player.getX() + 32) > x & (Player.getX() + 32) < x_end)   ) { //X coord collision detection
			if (   (Player.getY() > y & Player.getY() < y_end)   |   ((Player.getY() + 64) > y & (Player.getY() + 64) < y_end )   ) { //Y coord collision detection
				Screen.setScreen(3); //Set screen to finish screen
			}
		}
		
	}

	public void render(Graphics2D g2) {
		g2.drawImage(img, (x + Camera.getXOffSet()),y + Camera.getYOffSet(),width, height, null);
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	
	public void setColour(Color newColour) {
		//Void
	}

	
	public void collisionRemoveHealth() {
		//Void
	}

	
	public void hit() {
	}
}
