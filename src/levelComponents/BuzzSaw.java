package levelComponents;

import game_logic.Camera;
import game_logic.Data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import misc.VarTypes;
import particles.ParticleHandler;
import player.Health;
import player.Player;

public class BuzzSaw implements LevelObject {
	private int x;
	private int y;
	private int width;
	private int height;
	private int rot = 0; //Rotation of saw

	private int ticksElapsed = 0; //Counter used to control how much to damage the player

	private int pathingData[][];
	/*
	 * pathingData[0][0] = Components
	 * pathingData[0][1] = Current component
	 * 
	 * pathingData[n][0] = XChange
	 * pathingData[n][1] = Duration
	 */
	private int durationElapsed = 0; //Amount of ticks spend on one cycle of the pathing

	static private BufferedImage img = null; //58 x 58

	public BuzzSaw(int xPos, int yPos, String pathing) throws FileNotFoundException, IOException {
		x = xPos;
		y = yPos;
		width = 58;
		height = 58;

		//Load pathing data
		String pathingLocation = Data.getResourceLoc() + "level data/enemy pathing/" + pathing + ".properties";
		System.out.println("Loading " + pathingLocation);

		Properties pathingProps = new Properties();
		pathingProps.load(new FileInputStream(new File(pathingLocation)));

		int components = VarTypes.stringToInt(pathingProps.getProperty("Components"));
		pathingData = new int[components + 1][2];
		pathingData[0][0] = components;
		pathingData[0][1] = 1;

		for (int counter = 1; counter <= pathingData[0][0]; counter++) {
			pathingData[counter][0] = VarTypes.stringToInt(pathingProps.getProperty("C" + counter + "_XChange"));
			pathingData[counter][1] = VarTypes.stringToInt(pathingProps.getProperty("C" + counter + "_Duration"));
		}
	}

	public static void init() {
		//Load resource file
		try {
			img = ImageIO.read(new File(Data.getResourceLoc() + "textures/Buzz saw.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		//Deep below lava then return
		if (y > Magma.getLevel() + 5) {
			return;
		}

		ticksElapsed++;

		//Collision box
		int xPos = x + 3;
		int x_end = xPos + width;
		int yPos = y + 3;
		int y_end = yPos + height;

		//player collision
		if (   (Player.getX() < x_end & Player.getX() > xPos)   |   ((Player.getX() + 32) > xPos & (Player.getX() + 32) < x_end)   ) { //X coord collision detection
			if (   (Player.getY() > yPos & Player.getY() < y_end)   |   ((Player.getY() + 64) > yPos & (Player.getY() + 64) < y_end )   ) { //Y coord collision detection

				if (ticksElapsed >= 10) {
					Health.setHealth(Health.getHealth() - 25);
					ticksElapsed = 0;
				} else {
					Health.setHealth(Health.getHealth() - 20);
				}
			}
		}

		//rotate wheel
		rot += 10;

		if (rot > 360) {
			rot -= 360;
		}

		//pathing
		int curComp = pathingData[0][1];

		x += pathingData[curComp][0];
		durationElapsed ++;

		if (durationElapsed > pathingData[curComp][1]) {
			pathingData[0][1]++;
			durationElapsed = 0;

			if (pathingData[0][1] > pathingData[0][0]) {
				pathingData[0][1] = 1;
			}
		}

		//Below lava particle effects
		if (y + (height - 3) > Magma.getLevel()) {
			int particlesToSpawn = (int) (Math.random() * 20);
			
			for (int counter = 0; counter < particlesToSpawn; counter++) {
				ParticleHandler.newMagma((int) (x + Math.random() * width), (int) (Math.round((Magma.getLevel() - 2) + Math.random() * 5)), false); 
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void render(Graphics2D g2) {
		int xRender = x;
		int yRender = y;

		double rotation = Math.toRadians(rot);

		g2.rotate(rotation, xRender + Camera.getXOffSet() + 29, yRender + Camera.getYOffSet() + 29);
		g2.drawImage(img, xRender + Camera.getXOffSet() - 3,yRender + Camera.getYOffSet() - 3, null);
		g2.rotate(-rotation, xRender + Camera.getXOffSet() + 29, yRender + Camera.getYOffSet() + 29);
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