package player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import levelComponents.Magma;

public class Flash {
	/*
	 * Responsible for the colour overlays
	 * White when firing
	 * Red when taking damage
	 * Green when healing
	 * Yellow/Orange when the lava is close
	 */
	
	//Cooldown factors and intensity of flash
	private static double blueFlash = 0;
	private static double blueFlashCooldown = 2.5;
	private static double greenFlash = 0;
	private static double greenFlashCooldown = 1.5;
	private static double redFlash = 0;
	private static double redFlashCooldown = 0.5;
	private static double whiteFlash = 0;
	private static double whiteFlashCooldown = 10;

	public static void render(Graphics2D g2) {
		//Magma glow is a yellow overlay based on the distance from the player to the lava
		int magmaGlow = 10;
		int magmaCloseness = (int) (Player.getYInt() - (Magma.getLevel() - 150));

		if (magmaCloseness > 0) {
			magmaGlow += magmaCloseness;

			if (magmaGlow > 170) {
				magmaGlow = 170;
			}
		}

		try {
			g2.setPaint(new Color(255, 200, 70, (int) magmaGlow));
			g2.fill(new Rectangle2D.Double(0, 0, 640, 880));
			
			g2.setPaint(new Color(0,0,255, (int) blueFlash));
			g2.fill(new Rectangle2D.Double(0, 0, 640, 880));

			g2.setPaint(new Color(0,255,0, (int) greenFlash));
			g2.fill(new Rectangle2D.Double(0, 0, 640, 880));

			g2.setPaint(new Color(255,0,0, (int) redFlash));
			g2.fill(new Rectangle2D.Double(0, 0, 640, 880));

			g2.setPaint(new Color(255,255,255, (int) whiteFlash));
			g2.fill(new Rectangle2D.Double(0, 0, 640, 880));
		} catch (Exception e) {
			System.err.println("Error applying flash effect");
			System.err.println("Blue flash alpha: " + blueFlash);
			System.err.println("Green flash alpha: " + greenFlash);
			System.err.println("Magma glow alpha: " + magmaGlow);
			System.err.println("Red flash alpha: " + redFlash);
			System.err.println("White flash alpha: " + whiteFlash);
			System.err.println("Stack trace:");
			e.printStackTrace();
		}
	}

	public static void tick() { //Update flash intensities
		blueFlash -= blueFlashCooldown;

		if (blueFlash <= 0) {
			blueFlash = 0;
		}
		
		greenFlash -= greenFlashCooldown;

		if (greenFlash <= 0) {
			greenFlash = 0;
		}

		redFlash -= redFlashCooldown;

		if (redFlash <= 0) {
			redFlash = 0;
		}

		whiteFlash -= whiteFlashCooldown;

		if (whiteFlash <= 0) {
			whiteFlash = 0;
		}
	}

	public static void reset() { //Clear flash effects
		whiteFlash = 0;
		redFlash = 0;
		greenFlash = 0;
	}

	//Setters and getters
	public static double getBlueFlash() {
		return blueFlash;
	}

	public static void setBlueFlash(double blueFlash) {
		Flash.blueFlash = blueFlash;
	}

	public static double getBlueFlashCooldown() {
		return blueFlashCooldown;
	}

	public static void setBlueFlashCooldown(double blueFlashCooldown) {
		Flash.blueFlashCooldown = blueFlashCooldown;
	}
	
	public static double getGreenFlash() {
		return greenFlash;
	}

	public static void setGreenFlash(double greenFlash) {
		Flash.greenFlash = greenFlash;
	}

	public static double getGreenFlashCooldown() {
		return greenFlashCooldown;
	}

	public static void setGreenFlashCooldown(double greenFlashCooldown) {
		Flash.greenFlashCooldown = greenFlashCooldown;
	}

	public static double getRedFlash() {
		return redFlash;
	}

	public static void setRedFlash(double newRedFlash) {
		redFlash = newRedFlash;

		if (redFlash >= 170) {
			redFlash = 170;
		}
	}

	public static double getRedFlashCooldown() {
		return redFlashCooldown;
	}

	public static void setRedFlashCooldown(double redFlashCooldown) {
		Flash.redFlashCooldown = redFlashCooldown;
	}
	
	public static double getWhiteFlash() {
		return whiteFlash;
	}

	public static void setWhiteFlash(double newFlash) {
		Flash.whiteFlash = newFlash;

		if (whiteFlash >= 255) {
			whiteFlash = 255;
		}
	}

	public static double getWhiteFlashCooldown() {
		return whiteFlashCooldown;
	}

	public static void setWhiteFlashCooldown(double flashCooldown) {
		Flash.whiteFlashCooldown = flashCooldown;
	}
}
