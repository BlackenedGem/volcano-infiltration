package game_logic;

import game_base.Screen;
import levelComponents.LevelHandler;
import levelComponents.Magma;
import particles.ParticleHandler;
import player.Flash;
import player.Health;
import player.Player;
import player.WeaponHeat;

public class LevelState {
	private static int initLevel = 1; //Level to start at (Debug purposes, kind of not needed with level selection implemented)
	private static int lastLoaded = initLevel - 1; //Last level loaded
	private static int totLevels = 6; //Amount of levels in the game

	public static void reset() { //Set the next level to load to 0, effectively resetting the game
		lastLoaded = initLevel - 1;

		clear();
		Score.reset();
	}

	public static void retryLevel() { //Retry the level
		clear();
		LevelHandler.loadLevel(lastLoaded);
	}

	public static void nextLevel() { //Load the next level
		clear();
		lastLoaded++;
		
		if (lastLoaded <= totLevels) {
			LevelHandler.loadLevel(lastLoaded);
			Screen.setScreen(1);
		} else {
			Screen.setScreen(6);
		}
	}

	public static void clear() { //Prepare the code for the next level (e.g. restore health, weapon cooldown)
		Health.setHealth(400);
		Score.reset();
		
		cleanup();
		
	}
	
	public static void cleanup() { //Remove unwanted level data, but not stuff such as score data
		WeaponHeat.reset();
		LevelHandler.reset();
		Flash.reset();
		Hacking.reset();
		Player.reset();
		Magma.reset();
		ParticleHandler.deleteAll();
	}
	
	public static void setLevel(int newValue) {
		lastLoaded = newValue;
	}

	public static int getLevel() {
		return lastLoaded;
	}
	
	public static int getTotLevels() {
		return totLevels;
	}
}
