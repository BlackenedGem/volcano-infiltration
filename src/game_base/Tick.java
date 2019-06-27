package game_base;

import game_logic.DebugInfo;
import game_logic.Hacking;
import game_logic.Pause;
import game_logic.Score;
import interfaceComponents.MenuHandler;
import interfaceComponents.menuName.InputName;
import levelComponents.LevelHandler;
import particles.ParticleHandler;
import player.Flash;
import player.Health;
import player.LaserHandler;
import player.Player;
import player.WeaponHeat;


public class Tick {

	public static void tick() {
		Mouse.tick();

		switch (Screen.getScreen()) { //Call sub methods depending on screen
		case 1:
			tickGame();
			break;
		case 2:
			tickGamePaused();
			break;
		case 3:
			tickLevelComplete();
			break;
		case 4:
			tickGameOver();
			break;
		case 5:
			tickNameSelScreen();
			break;
		case 6:
			tickNoMoreLevels();
			break;
		case 7:
			tickLevelSelect();
			break;
		case 8:
			tickHelp();
			break;
		}

		DebugInfo.tick();
	}

	private static void tickGame() {
		//Pre tick to remove collision values
		Player.preTick();

		ParticleHandler.tick();
		LevelHandler.tick();

		Player.tick();
		LaserHandler.tick();
		Health.tick();
		WeaponHeat.tick();
		Hacking.tick();
		Flash.tick();

		Pause.tick();
		Score.tick();
	}

	private static void tickGamePaused() {
		Pause.tick();
		MenuHandler.tick();
	}

	private static void tickHelp() {
		MenuHandler.tick();
		ParticleHandler.tick();
	}

	private static void tickLevelComplete() {
		MenuHandler.tick();
		ParticleHandler.tick();
	}

	private static void tickLevelSelect() {
		MenuHandler.tick();
		ParticleHandler.tick();
	}

	private static void tickGameOver() {
		MenuHandler.tick();
		ParticleHandler.tick();
	}

	private static void tickNameSelScreen() {
		InputName.tick();
		MenuHandler.tick();
		ParticleHandler.tick();

		int particlesToSpawn = (int) (Math.random() * 5);

		for (int counter = 1; counter < particlesToSpawn; counter++) {
			ParticleHandler.newMagma((int) (Math.random() * 640), 955, false);
			ParticleHandler.newMagma((int) (Math.random() * 640), 955, true);
		}
	}

	private static void tickNoMoreLevels() {
		MenuHandler.tick();
		ParticleHandler.tick();
	}

}
