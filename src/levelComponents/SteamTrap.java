package levelComponents;

import game_logic.Camera;
import game_logic.Data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import particles.ParticleHandler;
import audio.SoundEffectsHandler;

public class SteamTrap implements LevelObject {
	private int x;
	private int y;
	private int width = 8;
	private int height = 16;
	
	private int frequency;
	private int duration;
	private int intensity;
	private int startWait;
	
	private int timeElapsed = 0;
	private int mode = 3;
	/*Mode 1: not active
	 * Mode 2: active
	 * Mode 3: Init wait period
	 */
	
	
	private static BufferedImage img = null;

	public static void init() {
		//Load image file from disk
		try {
			img = ImageIO.read(new File(Data.getResourceLoc() + "textures/Steam Trap.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SteamTrap (int xPos, int yPos, int freq, int dur, int intense, int startWait) {
		x = xPos;
		y = yPos;
		frequency = freq;
		duration = dur;
		intensity = intense; //Amount of steam particles to release per tick
		this.startWait = startWait; //Delay before starting on/off cycle
	}

	public void render(Graphics2D g2) {
		g2.drawImage(img, (x + Camera.getXOffSet()),y + Camera.getYOffSet(), null);
	}

	public void tick() {
		if (y > Magma.getLevel()) { //Become inactive once the magma goes above the output valve
			return;
		}
		
		timeElapsed++;
		
		// Once mode has reached it's end switch to next mode and update accordingly
		switch (mode) {
		case 1:
			if (timeElapsed >= frequency) {
				mode = 2;
				timeElapsed = 0;
				SoundEffectsHandler.playSound("steam hiss", SoundEffectsHandler.getDistanceFactor(x, y, 18) + 3);
			}
			break;
		case 2:
			if (timeElapsed >= duration) {
				mode = 1;
				timeElapsed = 0;
			}
			break;
		case 3:
			if (timeElapsed >= startWait) {
				mode = 1;
				timeElapsed = 0;
			}
			break;
		}
		
		if (mode == 1) { //If inactive then don't create particles
			return;
		}
		
		//Create steam particle
		int particles = (int) (Math.random() * intensity / 2 + intensity / 2);
		
		for (int counter = 0; counter <= particles; counter++) {
			ParticleHandler.newSteam((int) (x + (Math.random() * 8) - 1), y + 16, 0, 11);
		}
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
