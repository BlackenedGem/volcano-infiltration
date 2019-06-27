package game_base;

import game_logic.Camera;
import game_logic.LevelState;
import game_logic.Score;
import interfaceComponents.MenuHandler;
import particles.ParticleHandler;
import audio.SoundEffectsHandler;

public class Screen {
	private static int screen = 5;
	private static boolean renderScreen = true; //Render content of game rather than loading screen
	/*
	 * Screen 1 = In game
	 * Screen 2 = In game paused
	 * Screen 3 = Level Complete
	 * Screen 4 = Game Over
	 * Screen 5 = Name selection screen
	 * Screen 6 = No levels :(
	 * Screen 7 = Level selection screen
	 * Screen 8 = Help/instruction
	 */
	
	public static boolean renderScreen() { //Return if the screen is on loading mode
		return renderScreen;
	}

	public static void setRenderScreen(boolean renderScreen) {
		Screen.renderScreen = renderScreen;
	}

	public static void setScreen(int newScreen) { //Change the screen and update game as appropriate
		MenuHandler.unload();
		
		setRenderScreen(false);
		screen = newScreen;
		
		//Set mouse down to false to make user have to click twice
		Mouse.setIsDown(false);
		Mouse.reset();
		
		switch (screen) {
		case 1:
			break;
		case 2:
			MenuHandler.load_Pause();
			break;
		case 3:
			ParticleHandler.deleteAll();
			Score.calcLevelComplete();
			MenuHandler.load_LevelComplete();
			SoundEffectsHandler.playSound("you win", 0); //Play sound
			Camera.reset();
			LevelState.cleanup();
			break;
		case 4:
			ParticleHandler.deleteAll();
			Score.calcGameOver();
			MenuHandler.load_GameOver();
			SoundEffectsHandler.playSound("you lose", 0); //Play sound
			Camera.reset();
			LevelState.cleanup();
			break;
		case 5:
			ParticleHandler.deleteAll();
			MenuHandler.load_MenuNames();
			Camera.reset();
			break;
		case 6:
			ParticleHandler.deleteAll();
			MenuHandler.load_NoMoreLevels();
			Camera.reset();
			break;
		case 7:
			ParticleHandler.deleteAll();
			MenuHandler.load_LevelSelect();
			Camera.reset();
			break;
		case 8:
			ParticleHandler.deleteAll();
			MenuHandler.load_MenuHelp();
			break;
		}
		
		setRenderScreen(true);
	}
	
	public static int getScreen() {
		return screen;
	}
}

