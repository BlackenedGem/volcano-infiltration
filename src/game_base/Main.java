package game_base;

import game_logic.Data;

import interfaceComponents.menuLevelSel.ButtonLevelX;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import levelComponents.BuzzSaw;
import levelComponents.Finish;
import levelComponents.Henchman;
import levelComponents.Laptop;
import levelComponents.LaserSwitch;
import levelComponents.MediKit;
import levelComponents.SteamTrap;
import misc.BaseConfigLoader;
import player.Player;
import audio.BackgroundMusic;


public class Main extends JFrame {
	private static final long serialVersionUID = -5766541033313002362L;
	private static long lastTick;
	

	public Main() { //Called before creating display
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
		
		add(new Display());
		setTitle("Volcano Infiltration");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640, 990);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setCursor(blankCursor);
	}
	
	public static void main(String[] args) { //Entry point of game
		//Load resources
		Background.init();
		BaseConfigLoader.load();
		ButtonLevelX.init();
		BuzzSaw.init();
		Finish.init();
		Henchman.init();
		Laptop.init();
		LaserSwitch.init();
		MediKit.init();
		Mouse.init();
		Player.init();
		SteamTrap.init();

		//Load and then play sound
		Runnable music = new BackgroundMusic(Data.getResourceLoc() + "audio/background music/");
		new Thread(music).start();

		//Init display
		EventQueue.invokeLater(new Runnable() {

			public void run() {                
				JFrame windowInstance = new Main();
				windowInstance.setVisible(true);    
			}
		});
		
		//Setup display
		Screen.setScreen(5);
		
		System.out.println("Displayed initialised");
		
		lastTick = System.currentTimeMillis();
		
		while (true) { //Game tick loop
			
			
        	Tick.tick();
        	long tickCompensation = System.currentTimeMillis() - (lastTick + 25);
        	
        	if (tickCompensation < 1) {
        		tickCompensation = 0;
        	} else if (tickCompensation >= 25) {
        		tickCompensation = 24;
        		System.out.println("Tick compensation was bigger than 24ms");
        	} else {
        		//
        	}
        	
        	
        	lastTick = System.currentTimeMillis();
        	
        	try {
                Thread.sleep(25 - tickCompensation);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }

        	
            
        }

	}
}
