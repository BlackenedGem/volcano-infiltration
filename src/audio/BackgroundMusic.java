package audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class BackgroundMusic implements Runnable {
	static int noTracks = 5;
	
	static float volume = -10.0f;
	private String fileDir;
	
	static private Clip clip;

	/*
	 * Audio possible musics:
	 * Chase sort of thing: https://www.youtube.com/watch?v=w39ri44E-7w
	 * Age of War: https://www.youtube.com/watch?v=a2wyBcCHK5g
	 * https://www.youtube.com/watch?v=3FQi4c8g21A&feature=player_detailpage
	 * Boss music? https://www.youtube.com/watch?v=9wMjq58Fjvo
	 */

	public BackgroundMusic(final String fileLoc) {
		fileDir = fileLoc;
	}

	public void run() {   
		while (true) {
			playTrack(fileDir + getRandomTrack());
		}
	}
	
	private String getRandomTrack() {
		int trackID = (int) Math.round(1 + Math.random() * (noTracks - 1));
		String ret = "track " + trackID + ".wav";
		
		System.out.println("Playing track: " + ret);
		return ret;
	}
	
	private void playTrack(String fileName) {
		// Note: use .wav files    
		try {
			//Load file from disk, set volume, loop until exit
			clip = AudioSystem.getClip();

			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fileName));

			clip.open(inputStream);

			FloatControl gainControl = 
					(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume);

			clip.start();
			
			Thread.sleep(10000);
			
			while (clip.isRunning()) {
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			System.out.println("play sound error: " + e.getMessage() + " for " + fileName);

		}
	}
}
