package game_logic;

public class LevelCompletionStatus {
	public static boolean isComplete(int level) {
		if (Unlock.getMaxLevel() > level) {
			return true;
		} else {
			return false;
		}
	}
}
