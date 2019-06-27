package levelComponents;

import game_logic.Camera;
import game_logic.Data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import player.Health;
import player.Player;

public class MediKit implements LevelObject {
	private int x;
	private int y;
	private int width;
	private int height;
	private int regen;

	private boolean active = true;

	private static BufferedImage img = null;

	public MediKit(int x, int y, int regen) {
		this.x = x;
		this.y = y;
		this.width = 16;
		this.height = 16;
		this.regen = regen; //Amount of health to regen
	}

	public static void init() {
		//Load image file from disk
		try {
			img = ImageIO.read(new File(Data.getResourceLoc() + "textures/Medikit.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		if (!active) {
			return;
		}

		int x_end = x + width;
		int y_end = y + height + 2;

		//Collision detection
		if (   (Player.getX() < x_end & Player.getX() > x)   |   ((Player.getX() + 32) > x & (Player.getX() + 32) < x_end)   ) { //X coord collision detection
			if (   (Player.getY() > y & Player.getY() < y_end)   |   ((Player.getY() + 64) > y & (Player.getY() + 64) < y_end )   ) { //Y coord collision detection
				if (Health.getHealth() < 400) { //Only operate if player has lost health
					active = false;
					Health.setHealth(Health.getHealth() + regen); //Restore health
				}
			}
		}

	}

	public void render(Graphics2D g2) {
		if (!active) { //Don't draw medikit if it has been consumed
			return;
		}

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
