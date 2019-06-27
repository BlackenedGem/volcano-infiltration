package levelComponents;

import game_logic.Camera;
import game_logic.Data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import misc.Collisions;
import misc.VarTypes;
import particles.ParticleHandler;
import player.Health;
import player.LaserHandler;
import player.Player;
import audio.SoundEffectsHandler;

public class Henchman implements LevelObject {
	private double x;
	private double y;
	private int width = 32;
	private int height = 64;

	private int totCooldown;
	private int cooldown;
	private int health;
	private int maxHealth;

	private int detectionRange;
	private double detectionFactor = 0; //Amount that the henchman is ready to fire. When it becomes the response time the henchman will fire
	private double detectionCooldown = 0.2; //Amount to decrease the readiness of the henchman after noticing the player
	private int detectionResponseTime = 10;
	
	private int detectionX = 0;
	private int detectionY = 0;
	private int detectionYMod = 4;

	private int pathingData[][];
	/*
	 * pathingData[0][0] = Components
	 * pathingData[0][1] = Current component
	 * 
	 * pathingData[n][0] = XChange
	 * pathingData[n][1] = Duration
	 */
	private int durationElapsed = 0;

	//Image resource files
	private static BufferedImage[][] img = new BufferedImage[3][4];

	//Animation variables
	private int frame = 0;
	private int frameCounter = 5;
	private int frameType = 0;

	private boolean active = true;

	public static void init() {
		try {
			for (int counter = 0; counter <= 2; counter++) {
				for (int counter2 = 0; counter2 <= 3; counter2++) {
					img[counter][counter2] = ImageIO.read(new File(Data.getResourceLoc() + "textures/Henchman/" + counter + " - " + counter2 + ".png"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Henchman(int xPos, int yPos, int totHealth, int weaponCooldown, int detection, double detectionCool, int detectionResponse, String pathing) throws FileNotFoundException, IOException {
		x = xPos;
		y = yPos;
		health = totHealth;
		maxHealth = totHealth;
		totCooldown = weaponCooldown;
		detectionRange = detection;
		detectionCooldown = detectionCool;
		detectionResponseTime = detectionResponse;

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

	public void tick() {
		//Collision box
		int xPos = (int) (x - 2);
		int yPos = (int) (y - 2);
		int x_end = (int) (x + width) + 4;
		int y_end = (int) (y + height) + 4;

		if (!active) {
			return;
		}

		//animation handling	
		frameCounter--;

		if (frameCounter <= 0) {
			frameCounter = 5;
			frame++;
			if (frame > 2) {
				frame = 0;
			}
		}

		cooldown --; //Decrease the amount of time before the henchman can fire again

		//Below lava level
		if (y + height > Magma.getLevel()) {
			this.changeHealth(-8);

			int particlesToSpawn = (int) (Math.random() * 10);

			for (int counter = 0; counter < particlesToSpawn; counter++) {
				ParticleHandler.newMagma((int) (x + Math.round(Math.random() * 32)), (int) (Math.round((Magma.getLevel() - 2) + Math.random() * 5)), false); 
			}
		}

		//Brawling detection
		if (   (Player.getX() < x_end & Player.getX() > xPos)   |   ((Player.getX() + 32) > xPos & (Player.getX() + 32) < x_end)   ) { //X coord collision detection
			if (   (Player.getY() > yPos & Player.getY() < y_end)   |   ((Player.getY() + 64) > yPos & (Player.getY() + 64) < y_end )   ) { //Y coord collision detection
				changeHealth(-1);
				Health.setHealth(Health.getHealth() - 1);
			}
		}

		//Notices player
		if (detectPlayer()) {
			detectionFactor++; //Start to notice player

			if (detectionFactor > detectionResponseTime) {
				detectionFactor = detectionResponseTime;
			}

			if (cooldown <= 0 & detectionFactor == detectionResponseTime) { //If ready to fire and weapon cooled then fire
				cooldown = totCooldown;
				LaserHandler.newLaser((int) x + width / 2, (int) y + height / 2, Player.getXInt() + detectionX, Player.getYInt() + detectionY, "henchman");
			}
		} else {
			if (detectionFactor <= 0) {
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


			} else {
				detectionFactor -= detectionCooldown; 
			}
		}
		
		//Update detection optimisation
		detectionY += detectionYMod;
		
		if (detectionY >= 64 | detectionY <= 0) {
			detectionYMod *= -1;
		}
	}

	public boolean detectPlayer() {
		detectionX = (int) ((Math.random() * 20) + 6);
		
		double xChange;
		double yChange;
		int xPos = (int) (x + 16);
		int yPos = (int) (y + 32);
		int xPlayer = (int) Player.getX() + detectionX;
		int yPlayer = (int) Player.getY() + 4 + detectionY;

		if (Math.abs(xPlayer - xPos) > Math.abs(yPlayer - yPos)) {
			xChange = xPlayer - xPos;
			yChange = (yPlayer - yPos) / Math.abs(xChange);
			xChange = xChange / Math.abs(xChange);
		} else {
			yChange = yPlayer - yPos;
			xChange = (xPlayer - xPos) / Math.abs(yChange);
			yChange = yChange / Math.abs(yChange);
		}

		int counter = 0;

		while (true) {
			Collisions coll = new Collisions((int) (xPos + (counter * xChange)), (int) (yPos + (counter * yChange)), "henchman");
			
			if (coll.getColl() != 0) {
				if (coll.getColl() == 2) {
					return true;
				}
				return false;
			}

			counter++;
			
			//Sqrt(counter * yChange)^2 + (counter * xChange)^2 = Radius currently checked
			
			if (Math.sqrt(Math.pow(counter * xChange, 2) + Math.pow(counter * yChange, 2)) > detectionRange) { //If reached end of detection radius
				return false;
			}
		}
	}

	public void render(Graphics2D g2) {
		if (!active) {
			return;
		}

		g2.drawImage(getImg(),(int) (x + Camera.getXOffSet()),(int) (y + Camera.getYOffSet()),32, 64, null);

		int greenFill = (health * width) / maxHealth;
		int redFill = width - greenFill;

		g2.setPaint(new Color(210, 20, 30));
		g2.fill(new Rectangle2D.Double(x + Camera.getXOffSet() + greenFill, y - 7 + Camera.getYOffSet(), redFill, 5)); //Red section

		g2.setPaint(new Color(30, 180, 50));
		g2.fill(new Rectangle2D.Double(x + Camera.getXOffSet(), y - 7 + Camera.getYOffSet(), greenFill, 5)); //Green section
		
		/*
		double xChange;
		double yChange;
		int xPos = (int) (x + 16);
		int yPos = (int) (y + 32);
		int xFiring = (int) Player.getX();
		int yFiring = (int) Player.getY() + 56;

		if (xFiring > xPos) {
			xPos += 10;
		} else {
			xPos -= 10;
		}

		if (Math.abs(xFiring - xPos) > Math.abs(yFiring - yPos)) {
			xChange = xFiring - xPos;
			yChange = (yFiring - yPos) / Math.abs(xChange);
			xChange = xChange / Math.abs(xChange);
		} else {
			yChange = yFiring - yPos;
			xChange = (xFiring - xPos) / Math.abs(yChange);
			yChange = yChange / Math.abs(yChange);
		}

		int counter = 0;

		while (counter < 10000) {
			Collisions coll = new Collisions((int) (xPos + (counter * xChange)), (int) (yPos + (counter * yChange)), "henchman");
			
			if (coll.getColl() != 0) {
				break;
			}

			counter++;
		}

		g2.setStroke(new BasicStroke(2));
		g2.setColor(new Color(255,0,0,120));
		g2.drawLine((int) (xPos + counter * xChange) + Camera.getXOffSet(), (int) (yPos + counter * yChange) + Camera.getYOffSet(), xPos + Camera.getXOffSet(), yPos + Camera.getYOffSet());
		*/
	}

	public BufferedImage getImg() {
		return img[frameType][frame];
	}

	public void changeHealth(int diff) {
		health += diff;

		if (health <= 0) {
			active = false;
		}

		if (diff <= -100) {
			SoundEffectsHandler.playSound("grunt", SoundEffectsHandler.getDistanceFactor((int) x, (int) y, 50));
		}
	}

	public boolean getActive() {
		return active;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
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
		this.changeHealth(-100);
	}
}
