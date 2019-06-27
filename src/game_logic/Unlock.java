package game_logic;

import misc.BaseConfigLoader;

public class Unlock {
	private static int maxLevel = 1;
	
	public static void setMaxLevel(int newLevel) {
		if (newLevel < maxLevel) {
			return;
		}
		
		maxLevel = newLevel;
		BaseConfigLoader.save();
	}
	
	public static int getMaxLevel() {
		return maxLevel;
	}
}
