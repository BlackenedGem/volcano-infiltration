package levelComponents;

import game_logic.Camera;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import particles.ParticleHandler;
import player.Health;
import player.Player;


public class Floor implements LevelObject {
	private int x;
	private int y;
	private int width;
	private int height;
	
	Color colour = Color.GRAY;

	public Floor(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void tick() {
		int x_end = x + width;
		int y_end = y + height;
		
		
		int playerXLeft = Player.getXInt() + 1;
		int playerXRight = playerXLeft + 30;
		int playerY = Player.getYInt();
		int playerYBottom = playerY + 64;
		
		//Collisions
		//touching top of floor
		if (playerXLeft < x_end & playerXRight > x & playerYBottom > y & playerYBottom < y_end) {
			Player.collisionAbove();
			Player.setY((y - 63));
		}
		//touching bottom of floor
		if (playerXLeft < x_end & playerXRight > x & playerY > y & playerY < y_end) {
			Player.collisionBelow();
			Player.setY(y_end);
		}
		
		//Magma collisions
		if (y < Magma.getLevel() & y_end > Magma.getLevel()) {
			int particlesToSpawn = (int) (Math.random() * width / 10);
			
			for (int counter = 0; counter < particlesToSpawn; counter++) {
				if (Math.random() > 0.5) {
					ParticleHandler.newMagma((int) (x + Math.round(Math.random() * width)), (int) (Math.round((Magma.getLevel() - 2) + Math.random() * 5)), false);
				} else {
					ParticleHandler.newMagma((int) (x + Math.round(Math.random() * width)), (int) (Math.round((Magma.getLevel() - 2) + Math.random() * 5)), true);
				}
			}
		}
		
	}

	public void render(Graphics2D g2) {
		g2.setPaint(colour);
		g2.fill(new Rectangle2D.Double(x + Camera.getXOffSet(),y + Camera.getYOffSet(),width, height)); 
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
		colour = newColour;
	}
	
	public void collisionRemoveHealth() { //Remove health if player is inside wall
		int xPos = x + 7;
		int x_end = xPos + width - 14;
		int yPos = y + 7;
		int y_end = yPos + height - 14;
		
		//player collision
		if (   (Player.getX() < x_end & Player.getX() > xPos)   |   ((Player.getX() + 32) > xPos & (Player.getX() + 32) < x_end) | ((Player.getX() + 32 > x_end & Player.getX() < x))) { //X coord collision detection
			if (   (Player.getY() > yPos & Player.getY() < y_end)   |   ((Player.getY() + 64) > yPos & (Player.getY() + 64) < y_end )   ) { //Y coord collision detection
				Health.setHealth(Health.getHealth() - 4);
			}
		}
	}

	
	public void hit() {
	}
}
