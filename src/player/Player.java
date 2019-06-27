package player;

import game_logic.Camera;
import game_logic.Data;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import levelComponents.Magma;
import particles.ParticleHandler;

public class Player {
	//Movement vars
	private static double x = 50;
	private static int xRender; //Used to reduce rubber banding
	private static double xVel = 0;
	private static double y = 800;
	private static int yRender; //Used to reduce rubber banding
	private static double yVel = 0;

	//Direction of movement
	private static boolean movementLeft = false;
	private static boolean movementRight = false;
	private static boolean movementUp = false;
	private static double movementXCoEf = 1;
	private static double movementYCoEf = 1;
	private static boolean inAir = true;

	//Collision detection
	private static boolean above = true;
	private static boolean below = true;
	private static boolean left = true;
	private static boolean right = true;

	private static boolean submersedInMagma = false;

	//Image resource files
	private static BufferedImage[][] img = new BufferedImage[3][4];
	private static int frame = 0;
	private static int frameCounter = 5;
	private static int frameType = 0;

	//Initialisation
	public static void init() {
		//Load image files
		try {
			for (int counter = 0; counter <= 2; counter++) {
				for (int counter2 = 0; counter2 <= 3; counter2++) {
					img[counter][counter2] = ImageIO.read(new File(Data.getResourceLoc() + "textures/Player/" + counter + " - " + counter2 + ".png"));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Render
	public static void render(Graphics2D g2) {
		//Render
		g2.drawImage(Player.getImg(), xRender + Camera.getXOffSet(), yRender + Camera.getYOffSet(), null);
	}
	//Ticks
	public static void tick() {
		//Animation handling
		frameCounter--;

		if (frameCounter <= 0) {
			frameCounter = 5;
			frame++;
			if (frame > 2) {
				frame = 0;
			}
		}

		//Magma detection
		if (Magma.getLevel() <= ( y + 5) ) {
			submersedInMagma = true;
		} else {
			submersedInMagma = false;
		}

		if (Magma.getLevel() <= (y + 64)) {
			Health.setHealth(Health.getHealth() - 10);

			int particlesToSpawn = (int) (Math.random() * 10);

			for (int counter = 0; counter < particlesToSpawn; counter++) {
				ParticleHandler.newMagma((int) (x + Math.round(Math.random() * 32)), (int) (Math.round((Magma.getLevel() - 2) + Math.random() * 5)), false); 
			}
		}

		//Figure out magma movement co-efficient (Although I doubt you'll survive long in magma anyway)
		double submersedFactor = (int) ((y + 64) - Magma.getLevel());
		if (submersedFactor > 64) {
			submersedFactor = 64;
		} else if (submersedFactor < 0) {
			submersedFactor = 0;
		}

		submersedFactor = (submersedFactor/64);

		movementXCoEf = 1 - (0.5 * submersedFactor); //If fully submerged then halve speed
		movementYCoEf = movementXCoEf * movementXCoEf;

		inAir = above;

		//X movement
		xVel = 0;
		if (movementLeft) { //If user has pressed left key
			if (left) {  //and is allowed to move left
				xVel -= 5 * movementXCoEf; //then change xVel for left movement
			}
		}
		if (movementRight) { //If user has pressed right key
			if (right) { //and is allowed to move right
				xVel += 5 * movementXCoEf; //then change xVel for right movement
			}
		}
		x += xVel;

		//Y movement (positive yVel is falling)
		if (submersedInMagma) { //Character behaves differently in lava
			//Set Y vel to 0 if player not in y motion (standing on floor)
			if (!below) { 
				yVel = 0;
				inAir = true;
			} else if (!above) {
				yVel = 0;
			}

			//Jumping. Set yVel to a negative value so the character rises upwards
			if (movementUp & below) {
				if (!above) {
					yVel = -1.5;
				} else {
					yVel -= 0.2;
				}

			} else {
				yVel += 0.05; //Make player accelerate downwards
			}
		} else {
			if (!below) { //Bump off ceiling
				yVel *= -0.5;
				inAir = true;
			} else if (movementUp & !above) { //Jump
				yVel = -8;
				inAir = true;
			} else { //Make player fall
				yVel +=0.3;
			}

		}
		//Slow down player if in magma
		if (submersedFactor > 0) {
			if (yVel > (12 * movementYCoEf)) {
				yVel -= (yVel / 5);
			}
			if (yVel < (-12 * movementYCoEf)) {
				yVel -= (yVel / 5);
			}
		}

		if (inAir | submersedInMagma) {
			y += yVel;
		} else {
			yVel = 0;
		}

		if (y > 870) { //Check if player falls out of world
			y = 0;
		}

		//Set frame type
		if (!movementLeft & !movementRight) {
			frameType = 0;
		} else if (movementLeft) {
			frameType = 1;
		} else {
			frameType = 2;
		}

	}
	public static void preTick() {
		//Reset collision data
		left = true;
		right = true;
		above = true;
		below = true;
	}

	//Rendering image output
	public static BufferedImage getImg() {
		return img[frameType][frame];
	}
	//Calculate collisions
	public static void collisionLeft() {
		left = false;
	}
	public static void collisionRight() {
		right = false;
	}
	public static void collisionAbove() {
		above = false;
	}
	public static void collisionBelow() {
		below = false;
	}
	//Get attributes
	public static boolean getSubmersedInMagma() {
		return submersedInMagma;
	}
	public static double getX() {
		return x;
	}
	public static int getXInt() {
		int x_int = (int) x;
		return x_int;
	}
	public static double getXVel() {
		return xVel;
	}
	public static double getY() {
		return y;
	}
	public static int getYInt() {
		int y_int = (int) y;
		return y_int;
	}
	public static double getYVel() {
		return yVel;
	}
	//Set attributes
	public static void setX(double newX) {
		x = newX;
	}
	public static void setY(double newY) {
		y = newY;
	}
	public static void setXRender(int newX) {
		xRender = newX;
	}
	public static void setYRender(int newY) {
		yRender = newY;
	}
	//Keyboard input
	public static void moveUp() {
		if (!above | submersedInMagma) {
			movementUp = true;
		}
	}
	public static void moveLeft() {
		movementLeft = true;
	}
	public static void moveRight() {
		movementRight = true;
	}
	public static void stopLeft() {
		movementLeft = false;
	}
	public static void stopRight() {
		movementRight = false;
	}
	public static void stopUp() {
		movementUp = false;
	}
	//Non game methods
	public static void reset() {
		x = 0;
		xVel = 0;
		y = 0;
		yVel = 0;
		movementLeft = false;
		movementRight = false;
		movementUp = false;
		movementXCoEf = 1;
		inAir = true;
		above = true;
		below = true;
		left = true;
		right = true;
		submersedInMagma = false;
	}
	public static void levelComplete() {
	}
}
