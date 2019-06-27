package audio;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class PlaySound implements Runnable {
	String fileLoc;
	float soundCorrection;

	Clip clip = null;
	FloatControl gainControl;

	public PlaySound (String fileLoc, float soundCorrection) throws Exception {
		this.fileLoc = fileLoc; //Save values in class
		this.soundCorrection = soundCorrection;
	}

	public void run() { //Executed after class initialises
		try {
			//Load file from disk, set sound and then play
			clip = AudioSystem.getClip();

			clip.open(AudioSystem.getAudioInputStream(new File(fileLoc)));

			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(soundCorrection);
			
			clip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
