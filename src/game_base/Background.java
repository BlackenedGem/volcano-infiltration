package game_base;

import game_logic.Camera;
import game_logic.Data;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
	/*
	 * Screen 1 = In game
	 * Screen 1 = In game paused
	 * Screen 3 = Level Complete
	 * Screen 4 = Game Over
	 * Screen 5 = Name selection screen
	 * Screen 6 = No more levels
	 * Screen 7 = Level selection screen
	 * Screen 8 = Help screen
	 */

	//Image files to be stored in memory
	static BufferedImage bkGame;
	static BufferedImage bkGameOver;
	static BufferedImage bkHelp;
	static BufferedImage bkLevelComplete;
	static BufferedImage bkLevelSel;
	static BufferedImage bkLoading;
	static BufferedImage bkNameSel;
	static BufferedImage bkNoMoreLevels;

	public static void init() { //Load image files from disk
		try {
			bkGameOver = ImageIO.read(new File(Data.getResourceLoc() + "textures/backgrounds/GameOver.png"));
			bkHelp = ImageIO.read(new File(Data.getResourceLoc() + "textures/backgrounds/Help.png"));
			bkLevelComplete = ImageIO.read(new File(Data.getResourceLoc() + "textures/backgrounds/LevelComplete.png"));
			bkLevelSel = ImageIO.read(new File(Data.getResourceLoc() + "textures/backgrounds/LevelSel.png"));
			bkLoading = ImageIO.read(new File(Data.getResourceLoc() + "textures/backgrounds/Loading.png"));
			bkNameSel = ImageIO.read(new File(Data.getResourceLoc() + "textures/backgrounds/NameSel.png"));
			bkNoMoreLevels = ImageIO.read(new File(Data.getResourceLoc() + "textures/backgrounds/NoMoreLevels.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void render(Graphics2D g2) { //Render appropriate background to screen
		switch (Screen.getScreen()) {
		case 1:
			g2.drawImage(bkGame, Camera.getXOffSet(), (880 - Camera.getHeight()) + Camera.getYOffSet(), null);
			break;
		case 2:
			g2.drawImage(bkGame, Camera.getXOffSet(), (880 - Camera.getHeight()) + Camera.getYOffSet(), null);
			break;
		case 3:
			g2.drawImage(bkLevelComplete, 0, 0, null);
			break;
		case 4:
			g2.drawImage(bkGameOver, 0, 0, null);
			break;
		case 5:
			g2.drawImage(bkNameSel, 0, 0, null);
			break;
		case 6:
			g2.drawImage(bkNoMoreLevels, 0, 0, null);
			break;
		case 7:
			g2.drawImage(bkLevelSel, 0, 0, null);
			break;
		case 8:
			g2.drawImage(bkHelp, 0, 0, null);
			break;
		}
	}

	public static void renderLoading(Graphics2D g2) { //Render loading screen to screen
		g2.drawImage(bkLoading, 0, 0, null);
	}

	public static void loadGameBK(String fileLoc) { //Load background image for specific level
		//Load background image
		try {
			bkGame = ImageIO.read(new File(fileLoc));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
