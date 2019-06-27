package game_logic;

import game_base.Screen;
import interfaceComponents.menuName.InputName;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import misc.VarTypes;
import player.Health;

public class Score {
	static int timeElapsed = 1; //Amount of time taken to complete the level (in game ticks)
	
	static int levelScore = 0; //Level score
	static int scoreMod; //Score modifier for the level
	
	static long timeStarted = System.currentTimeMillis(); //When the game was started
	static String timeDiff;

	//High score data
	static boolean isHighScore = false;
	static int oldHighScore;
	static String highScoreName;

	public static void tick() {
		timeElapsed++; //Increment counter
	}

	public static void render(Graphics2D g2) {
		//Draw data to the screen depending on the screen
		
		switch (Screen.getScreen()) {
		case 1:
			g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
			g2.setColor(Color.WHITE);
			g2.drawString("Time taken: " + timeTaken(), 10, 900);
			g2.drawString("Current score: " + ((scoreMod * Health.getHealth()) / timeElapsed) , 10, 915);
			break;
		case 2:
			g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
			g2.setColor(Color.WHITE);
			g2.drawString("Time taken: " + timeTaken(), 10, 900);
			g2.drawString("Current score: " + ((scoreMod * Health.getHealth()) / timeElapsed) , 10, 915);
			break;
		case 3:
			g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
			g2.setColor(Color.GRAY);

			g2.drawString("Time taken: " + timeTaken(), 130, 370);
			g2.drawString("Health left: " + ((100 * Health.getHealth()) / 400) + "%", 130, 400);
			g2.drawString("Level score: " + levelScore, 130, 430);

			if (isHighScore) {
				g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
				g2.setColor(Color.BLACK);
				g2.drawString("NEW HIGH SCORE", 125, 560);
			} else {
				g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
				g2.setColor(Color.BLACK);
				g2.drawString("High Score", 130, 530);
				
				g2.setStroke(new BasicStroke(2));
				g2.drawLine(125, 535, 280, 535);
				
				g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
				g2.setColor(Color.GRAY);
				g2.drawString("Name: " + highScoreName, 130, 565);
				g2.drawString("Score: " + oldHighScore, 130, 595);
			}
			break;
		case 4:
			g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
			g2.setColor(Color.GRAY);

			g2.drawString("Total play time: " + timeDiff, 100, 400);
			break;
		}
	}

	public static String timeTaken() { //Turn game ticks into words
		long timeTaken = 1000 * ( timeElapsed / 40 );

		SimpleDateFormat sdf = new SimpleDateFormat("mm' Mins 'ss' Secs'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String time = sdf.format(new Date(timeTaken));

		return time;
	}

	public static void calcGameOver() { //Work out the time that the game has been played for (since started)
		long timeDiff2 = System.currentTimeMillis() - timeStarted;

		SimpleDateFormat sdf = new SimpleDateFormat("mm' Minutes 'ss' Seconds'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		timeDiff = sdf.format(new Date(timeDiff2));

		if (timeDiff2 >= 3600000) {
			timeDiff = "Over an hour";
		}
	}

	public static void calcLevelComplete () {
		//Work out the level score and add it total score
		levelScore = (scoreMod * Health.getHealth()) / timeElapsed;
		//Unlock the level
		Unlock.setMaxLevel(LevelState.getLevel() + 1);

		try {
			//Check the level high score. If it is a new high score then save it. Else save the data (to be displayed)
			Properties propsHighScore = new Properties();
			InputStream configHighScore = null;
			String levelData = Data.getResourceLoc() + "level data/level" + LevelState.getLevel() + "/";

			//Load general level data
			configHighScore = new FileInputStream(new File(levelData + "high score.properties"));
			propsHighScore.load(configHighScore);

			oldHighScore = VarTypes.stringToInt(propsHighScore.getProperty("score"));
			highScoreName = propsHighScore.getProperty("name");

			if (levelScore > oldHighScore) { //New high score has been set
				isHighScore = true;
				
				ElitedLevels.setElite(LevelState.getLevel());
				
				configHighScore.close();
				File file = new File(levelData + "high score.properties");
				file.delete();
				file.createNewFile();

				propsHighScore.load(new FileInputStream(file));
				
				propsHighScore.setProperty("score", String.valueOf(levelScore));
				propsHighScore.setProperty("name", InputName.getName());
				
				OutputStream out = new FileOutputStream(file);
				propsHighScore.store(out, "High score for the level");
			} else {
				isHighScore = false;
			}
		} catch (Exception e) {
			System.err.println("Error processing high scores");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static int getScoreMod() {
		return scoreMod;
	}
	
	public static void setScoreMod(int newScoreMod) {
		scoreMod = newScoreMod;
	}

	public static void reset() { //Reset the level score and time taken
		levelScore = 0;
		timeElapsed = 1;
	}
}
