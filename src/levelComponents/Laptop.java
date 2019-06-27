package levelComponents;

import game_logic.Camera;
import game_logic.Data;
import game_logic.Hacking;
import game_logic.Score;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import audio.SoundEffectsHandler;

import player.Flash;
import player.Player;

public class Laptop implements LevelObject {
	private int x;
	private int y;
	private int width = 24;
	private int height = 16;

	private double bonus;
	
	private boolean active = true;
	
	private boolean showNotice = false;
	private int noticeCooldown = 120;
	private double riseFactor = 0.5;

	static BufferedImage img = null;

	public static void init() {
		//Load resource file
		try {
			img = ImageIO.read(new File(Data.getResourceLoc() + "textures/laptop.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Laptop(int x, int y, double bonus) {
		this.x = x;
		this.y = y;
		this.bonus = bonus;
	}

	public void tick() {
		if (showNotice) {
			noticeCooldown -= 2;
			
			if (noticeCooldown <= 0) {
				showNotice = false;
			}
		}
		
		if (!active) {
			return;
		}
		
		int x_start = x - 5;
		int x_end = x + width + 5;
		int y_end = y + height + 2;
		
		//Collision detection
		if (   (Player.getX() < x_end & Player.getX() > x_start)   |   ((Player.getX() + 32) > x_start & (Player.getX() + 32) < x_end)   ) { //X coord collision detection
			if (   (Player.getY() > y & Player.getY() < y_end)   |   ((Player.getY() + 64) > y & (Player.getY() + 64) < y_end )   ) { //Y coord collision detection
				Hacking.setCompletion(Hacking.getCompletion() + 2);
				
				if (Hacking.getCompletion() >= 100) {
					active = false;
					Flash.setBlueFlash(150);
					Score.setScoreMod((int) (Score.getScoreMod() * bonus));
					SoundEffectsHandler.playSound("computer", 0);
					showNotice = true;
					
					Hacking.reset();
				}
				
				return;
			}
		}
	}

	public void render(Graphics2D g2) {
		if (showNotice) {
			g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
			g2.setColor(new Color(0, 0, 0, 5 + 2 * noticeCooldown));
			
			if (Math.round(bonus) == bonus) {
				g2.drawString("x" + Math.round(bonus), x - 10 + Camera.getXOffSet(), (int) (y - riseFactor * (100 - noticeCooldown)) + Camera.getYOffSet());
			} else {
				g2.drawString("x" + bonus, x - 10 + Camera.getXOffSet(), (int) (y - riseFactor * (100 - noticeCooldown)) + Camera.getYOffSet());
			}
			
		}
		
		if (!active) {
			return;
		}
		g2.drawImage(img, x + Camera.getXOffSet(), y + Camera.getYOffSet(), null);
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

	public void hit() {
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
	}

	public void collisionRemoveHealth() {
	}
}
