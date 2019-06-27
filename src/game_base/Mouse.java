package game_base;

import game_logic.Data;

import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import particles.ParticleHandler;
import player.Player;

public class Mouse {
	private static int relativeXPos;
	private static int relativeYPos;
	private static int wobbleX;
	private static int wobbleY;
	private static boolean isDown = false;

	private static BufferedImage cursorNormal_Down;
	private static BufferedImage cursorNormal_Up;
	private static BufferedImage cursorGunReticle;

	public static void init() { //Load mouse image files
		try {
			cursorNormal_Down = ImageIO.read(new File(Data.getResourceLoc() + "textures/mouse/cursor down.png"));
			cursorNormal_Up = ImageIO.read(new File(Data.getResourceLoc() + "textures/mouse/cursor.png"));
			cursorGunReticle = ImageIO.read(new File(Data.getResourceLoc() + "textures/mouse/gunReticle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void render(Graphics2D g2) { //Draw the mouse to the screen
		BufferedImage cursor = null;

		if (Screen.getScreen() == 1) {
			cursor = cursorGunReticle;
		} else {
			if (isDown) {
				cursor = cursorNormal_Down;
			} else {
				cursor = cursorNormal_Up;
			}
		}

		g2.drawImage(cursor, (relativeXPos - 16) + wobbleX, (relativeYPos - 16) + wobbleY, 32, 32, null);
	}

	public static void setInfo(Point screenPos) { //Update where the mouse is on the screen
		int relativeXPosTemp;
		int relativeYPosTemp;
		Point mousePos = MouseInfo.getPointerInfo().getLocation();

		relativeXPosTemp = (int) (mousePos.getX() - screenPos.getX());
		relativeYPosTemp = (int) (mousePos.getY() - screenPos.getY());

		if (relativeXPosTemp < 0) {
			relativeXPosTemp = 0;
		} else if (relativeXPosTemp > 640) {
			relativeXPosTemp = 640;
		}

		if (relativeYPosTemp < 0) {
			relativeYPosTemp = 0;
		} else if (relativeYPosTemp > 990) {
			relativeYPosTemp = 990;
		}

		relativeXPos = relativeXPosTemp;
		relativeYPos = relativeYPosTemp;
	}
	public static void tick() {
		if (Screen.getScreen() > 2) {
			if (Math.random() > 0.7) {
				ParticleHandler.newMagma(Mouse.getX(), Mouse.getY(), true);
			}
			if (isDown) {
				for (int counter = 1; counter < 10; counter++) {
					ParticleHandler.newMagma(Mouse.getX(), Mouse.getY(), false);
				}
			}
		}
		
		

		//Calculate mouse wobble (experimental feature)
		if (Math.random() > 0.3 | Screen.getScreen() > 2) {
			return;
		}

		double xChange = Player.getXVel();
		double yChange = Player.getYVel();
		double speed = Math.sqrt(xChange * xChange + yChange * yChange);

		wobbleX = (int) (Math.random() * speed * 3);
		wobbleY = (int) (Math.random() * speed * 3);

		//comment to enable mouse wobble
		wobbleX = 0;
		wobbleY = 0;
	}
	public static void reset() { //Reset mouse wobble (experimental feature)
		wobbleX = 0;
		wobbleY = 0;
	}
	public static int getX() {
		return relativeXPos;
	}
	public static int getY() {
		return relativeYPos;
	}
	public static int getWobbleX() {
		return wobbleX;
	}
	public static int getWobbleY() {
		return wobbleY;
	}
	public static boolean isDown() {
		return isDown;
	}
	public static void setIsDown(boolean newMode) {
		isDown = newMode;
	}
	public static void Pressed(MouseEvent e) { //Called when the mouse is pressed
		isDown = true;
	}
	public static void Released(MouseEvent e) { //Called when the mouse is released
		isDown = false;
	}
}
