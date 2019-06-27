package interfaceComponents.menuLevelSel;

import game_base.Mouse;
import game_base.Screen;
import game_logic.Data;
import game_logic.ElitedLevels;
import game_logic.LevelCompletionStatus;
import game_logic.LevelState;
import game_logic.Unlock;
import interfaceComponents.MenuButton;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import audio.SoundEffectsHandler;


public class ButtonLevelX extends MenuButton {
	private int level;
	private boolean isElited = false;
	private boolean isUnlocked = false;
	private String complete;
	private Color completeColour;
	private String elited;
	private Color elitedColour;
	private String unlocked;
	private Color unlockedColour;
	private static BufferedImage imgLocked = null;
	private static BufferedImage imgMastered = null;
	private static BufferedImage imgToPlay = null;
	
	private int xOffset = 0;

	public static void init() {
		//Load resource file
		try {
			imgLocked = ImageIO.read(new File(Data.getResourceLoc() + "textures/level locked.png"));
			imgMastered = ImageIO.read(new File(Data.getResourceLoc() + "textures/level mastered.png"));
			imgToPlay= ImageIO.read(new File(Data.getResourceLoc() + "textures/next level to play.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics2D g2) { //Draw the button to the screen
		//Core button
		g2.setStroke(new BasicStroke(borderSize));
		g2.setPaint(colourBorder);
		g2.draw(new Rectangle2D.Double(xPos,yPos,width,height));

		if (!isUnlocked) { //Render useless button if level not unlocked
			g2.setPaint(colourBody);
			g2.fill(new Rectangle2D.Double(xPos,yPos,width,height));

			g2.setFont(textFont);
			g2.setColor(colourText);
			g2.drawString(text, xPos + xTextOffset, yPos + yTextOffset);

			g2.drawImage(imgLocked, xPos, yPos, null);
		} else {
			//Render appropriate colours
			if (mouseOver) {
				g2.setPaint(colourBodyMouseOver);
			} else {
				g2.setPaint(colourBody);
			}
			g2.fill(new Rectangle2D.Double(xPos,yPos,width,height));

			//Draw text
			g2.setFont(textFont);
			if (mouseOver) {
				g2.setColor(colourTextMouseOver);
			} else {
				g2.setColor(colourText);
			}
			g2.drawString(text, xPos + xTextOffset, yPos + yTextOffset);

			//Draw 'NEW' text if next level to play
			if (Unlock.getMaxLevel() == level) {
				g2.drawImage(imgToPlay, xPos, yPos, null);
			}
			//Draw star is level elited
			if (isElited) {
				g2.drawImage(imgMastered, xPos, yPos, null);
			}
		}
	}

	public void renderMouseOver(Graphics2D g2) {
		//Draw mouse over
		if (mouseOver) {
			g2.setPaint(new Color(70, 70, 70, 220));
			g2.fill(new Rectangle2D.Double(Mouse.getX() + 10 + xOffset, Mouse.getY() + 10, 190, 70));

			g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
			
			g2.setColor(unlockedColour);
			g2.drawString(unlocked, Mouse.getX() + 20 + xOffset, Mouse.getY() + 30);
			
			g2.setColor(completeColour);
			g2.drawString(complete, Mouse.getX() + 20 + xOffset, Mouse.getY() + 50);
			
			g2.setColor(elitedColour);
			g2.drawString(elited, Mouse.getX() + 20 + xOffset, Mouse.getY() + 70);
		}
	}

	public ButtonLevelX () {
		super();

		this.setWidth(120);
		this.setHeight(70);
		this.setXOffset(45);
		this.setYOffset(50);

		this.setBorderSize(10);

		this.setColourBody(Color.GRAY);
		this.setColourBodyFiringOver(Color.BLACK);
		this.setColourBorder(Color.BLACK);
		this.setColourText(Color.BLACK);
		this.setColourTextMouseOver(Color.GRAY);

		this.setTextFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
	}

	public void setParams(int level) {
		this.level = level;

		//Order the button in a grid
		int row = (int) Math.floor((level - 1) / 4);
		int column = level - row * 4;

		this.setXPos(-120 + column * 150);
		this.setYPos(140 + 100 * row);
		this.setText(String.valueOf(level));
		
		if (column == 4) {
			xOffset = -210;
		}

		//Work out if the level is available
		if (Unlock.getMaxLevel() >= level) {
			isUnlocked = true;
		}

		if (ElitedLevels.isElite(level)) {
			isElited = true;
		}
		
		//Set string parameters
		if (isElited) {
			elitedColour = new Color(240, 240, 20);
			elited = "Level elited";
		} else {
			elitedColour = new Color(135, 115, 20);
			elited = "Level not elited";
		}
		
		if (isUnlocked) {
			unlockedColour = new Color(85, 220, 40);
			unlocked = "Level unlocked";
		} else {
			unlockedColour = new Color(230, 50, 70);
			unlocked = "Level locked";
		}
		
		if (LevelCompletionStatus.isComplete(level)) {
			completeColour = new Color(85, 220, 40);
			complete = "Level completed";
		} else {
			completeColour = new Color(230, 50, 70);
			complete = "Level not completed";
		}
		
		//Decrease XOffset if 2 digit number
		if (level > 9) {
			this.setXOffset(28);
		}
	}

	public void clickedEvent() {
		if (!isUnlocked) {
			if (playSound == 1) {
				SoundEffectsHandler.playSound("denied", -5);
			}
			return;
		}

		LevelState.setLevel(level - 1);
		LevelState.nextLevel();
		Screen.setScreen(1);
	}
}