package player;

import game_base.Screen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import particles.ParticleHandler;
import audio.SoundEffectsHandler;

public class Health {
	private static int health = 400;
	
	public static void render(Graphics2D g2) { //Render to screen
		Color healthColour = new Color(200, 50, 30);
		
		g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 23));
		g2.setColor(healthColour);
		g2.drawString("Health:", 330, 948);
		
		g2.setPaint(healthColour);
	    g2.fill(new Rectangle2D.Double(419,929,((health + 4) / 2),23));	
		
		g2.setStroke(new BasicStroke(4));
		g2.setPaint(Color.BLACK);
		g2.draw(new Rectangle2D.Double(417,927,205,26));
	}
	
	public static void tick() {
		if (health <= 0) { //If the player loses all health then switch to game over screen
			health = 0;
			Screen.setScreen(4);
		}
	}

	public static int getHealth() {
		return health;
	}

	public static void setHealth(int newHealth) { //Change the health to a new value
		int healthChange = -(health - newHealth); //Amount that the health has changed by
		double flashChange;
		health = newHealth;
		
		if (healthChange == 0) {
			return;
		}

		if (health > 400) { //If the requested value is above the max then decrease the change so the change goes to the maximum
			healthChange -= (health - 400);
			health = 400;
		}
		
		if (healthChange > 0) { //Gain health. Add green effect and spawn in particles on health bar
			for (int counter = 0; counter <= healthChange; counter++) {
				int particlesToSpawn = (int) (Math.random() * 5);
				
				for (int counter2 = 0; counter2 < particlesToSpawn; counter2++) {
					ParticleHandler.newRed((int) (418 + ((health - counter) / 2)), (int) (920 + Math.random() * 30), 2, 1);
				}
			}
			
			if (healthChange > 250) { //Limit the green effect
				healthChange = 250;
			}
			
			flashChange = healthChange / 1.8;
			Flash.setGreenFlash(Flash.getGreenFlash() + flashChange);
			
			return;
		}
		
		//Lose health
		if (healthChange <= -25) {
			SoundEffectsHandler.playSound("grunt", 0); //Play sound effect if the health change is large
		}
		
		for (int counter = 0; counter >= healthChange; counter--) { //Spawn particles on healeth bar
			int particlesToSpawn = (int) (Math.random() * 5);
			
			for (int counter2 = 0; counter2 < particlesToSpawn; counter2++) {
				ParticleHandler.newRed((int) (418 + ((health - counter) / 2)), (int) (920 + Math.random() * 30), 2, 1);
			}
		}
		
		flashChange = (healthChange / -2.0);
		Flash.setRedFlash(Flash.getRedFlash() + flashChange); //Make screen go red
	}
	
	
}
