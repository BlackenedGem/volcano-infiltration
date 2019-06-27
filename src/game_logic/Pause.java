package game_logic;

import game_base.Screen;

public class Pause {
	private static boolean isDown = false;
	private static int mouseTimer = 0;
	private static boolean mouseOnScreen = true;
	
	public static void tick() {
		//If P pressed then pause the game
		if (isDown) {
			isDown = false;
			if (Screen.getScreen() == 1) {
				Screen.setScreen(2);
			} else {
				Screen.setScreen(1);
			}
		}
		
		if (!mouseOnScreen) { //If the mouse is off the screen then increment a counter
			mouseTimer++;
		}
		
		if (mouseTimer > 100) { //If the mouse is off screen for 2.5 seconds then pause the game
			if (Screen.getScreen() == 1) {
				Screen.setScreen(2);
			}
		}
	}
	
	public static void pressed() {
		isDown = true;
	}
	
	public static void released() {
		isDown = false;
	}
	
	public static void mouseLeftScreen() {
		mouseOnScreen = false;
	}
	
	public static void mouseJoinedScreen() {
		mouseOnScreen = true;
		mouseTimer = 0;
	}
}
