package audio;

import game_logic.Data;
import player.Player;

public class SoundEffectsHandler {
	
	/**
	 * Play a sound from disk
	 * 
	 * @param clipName
	 * @param modifier
	 */

	public static void playSound(String clipName, double modifier) {
		String fileLoc = null;
		float soundCorrection = 0;

		try {	
			fileLoc = Data.getResourceLoc() + "audio/" + clipName + ".wav";
			soundCorrection += modifier;
			
			if (soundCorrection < -80) {
				return;
			}

			Runnable r = new PlaySound(fileLoc, soundCorrection);
			new Thread(r).start();


		} catch (Exception e) {
			System.out.println("play sound error: " + e.getMessage() + " for " + clipName);
		}
	}
	
	/**
	 * Returns a sound modifier based on the distance of the sound from the player
	 * @param x pos of sound
	 * @param y pos of sound
	 * @param modifier to adjust sound loss
	 * @return sound factor
	 */
	
	public static double getDistanceFactor(int x, int y, int modifier) {
		int playerX = Player.getXInt() + 16;
		int playerY = Player.getYInt() + 32;
		
		int xDiff = Math.abs(x - playerX);
		int yDiff = Math.abs(y - playerY);
		
		double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
		distance /= modifier;
		
		return -distance;
	}
}
