package player;

//EXPERIMENTAL

import game_base.Mouse;
import game_logic.Camera;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class LaserSight {
	public static void render(Graphics2D g2) {
		//Create a line from player showing laser direction
		/*
		double xChange;
		double yChange;
		int xPos = Player.getXInt() + 16; //xPos
		int yPos = Player.getYInt() + 25;
		int xFiring = (Mouse.getX() + Mouse.getWobbleX()) - Camera.getXOffSet(); //xFiring
		int yFiring = (Mouse.getY() + Mouse.getWobbleY()) - Camera.getYOffSet();

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
			if (Collisions.isCollided(xPos + (counter * xChange), yPos + (counter * yChange), "player") != 0) {
				break;
			}

			counter++;
		}

		g2.setStroke(new BasicStroke(2));
		g2.setColor(new Color(255,0,0,120));
		g2.drawLine((int) (xPos + counter * xChange) + Camera.getXOffSet(), (int) (yPos + counter * yChange) + Camera.getYOffSet(), xPos + Camera.getXOffSet(), yPos + Camera.getYOffSet());
		*/
	}
}
