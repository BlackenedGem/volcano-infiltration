package levelComponents;

import game_logic.Camera;
import game_logic.Data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import audio.SoundEffectsHandler;

public class LaserSwitch implements LevelObject {
	private int x;
	private int y;
	private int width;
	private int height;
	private int doorIDs[]; //Internal ids of the doors to change on laser collision

	private boolean toggle;
	private boolean active = false;
	private int mode;
	/*
	 * 1 = Down
	 * 2 = Left
	 * 3 = Up
	 * 4 = Right 
	 */

	private static BufferedImage[][] img = new BufferedImage[4][2];

	public LaserSwitch(int x, int y, boolean toggleable, int ID[], int mode) {
		this.x = x;
		this.y = y;
		this.toggle = toggleable;

		this.active = false;
		this.doorIDs = ID;

		//Set width and height depending on mode
		this.mode = mode - 1;

		if (mode == 1 | mode == 3) {
			this.width = 50;
			this.height = 20;
		} else {
			this.width = 20;
			this.height = 50;
		}
	}

	public static void init() {
		//Load image files
		try {
			for (int counter = 0; counter < 4; counter++) {
				int mode = counter + 1;

				img[counter][0] = ImageIO.read(new File(Data.getResourceLoc() + "textures/Laser Switch/" + mode + "/laserSwitch_Off.png"));
				img[counter][1] = ImageIO.read(new File(Data.getResourceLoc() + "textures/Laser Switch/" + mode + "/laserSwitch_On.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
	}

	public void render(Graphics2D g2) { //Render to screen
		int isOn;

		if (active == true) {
			isOn = 1;
		} else {
			isOn = 0;
		}

		g2.drawImage(img[mode][isOn], (x + Camera.getXOffSet()),y + Camera.getYOffSet(), null);
	}

	public void hit() {
		if (!toggle & active) { //Don't do anything if not toggleable and already turned on
			return;
		}

		active = !active;

		for (int counter = 0; counter < doorIDs.length; counter++) {
			LevelHandler.getAutoDoors().get(doorIDs[counter]).changeState(); //Update the doors
		}


		SoundEffectsHandler.playSound("switch activate", SoundEffectsHandler.getDistanceFactor(x + width / 2, y + height / 2, 30) + 1); //Play sound
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
}
