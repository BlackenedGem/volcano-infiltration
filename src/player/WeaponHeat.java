package player;


import game_base.Mouse;
import game_logic.Camera;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class WeaponHeat {
	private static double heat = 0;
	private static double heatChange = 0;
	private static double heatChangeFactor = 95;
	private static double cooldownMod = -1.8;
	private static boolean hasFired = true;
	private static boolean isOverheating = false;
	private static boolean allowFire = true;

	private static int blink = 20;
	private static int blinkMod = -5;


	public static void render(Graphics2D g2) {
		//Get Red and Green value for colour indicator to get a colour representing the heat of the weapon (It becomes redder the hotter it is)
		int R = (int) (55 + heat);
		int G = (int) (455 - heat);

		if (G < 55) {
			G = 55;
		}
		if (G > 255) {
			G = 255;
		}
		if (R < 55 ) {
			R = 55;
		}
		if (R > 255) {
			R = 255;
		}

		Color heatColour = new Color(R, G, 50);

		if (!(isOverheating & blink > 0)) {
			g2.setPaint(heatColour);
			g2.fill(new Rectangle2D.Double(419,894,(heat / 2), 23));	
		}

		if (isOverheating) {
			g2.setColor(Color.BLACK);
			g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
			g2.drawString("Overheated", 480, 910);
		}
		
		g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 23));
		g2.setColor(new Color(255, 160, 70));
		g2.drawString("Weapon heat:", 255, 912);

		g2.setStroke(new BasicStroke(4));
		g2.setPaint(Color.BLACK);
		g2.draw(new Rectangle2D.Double(417,892,205,26));
	}

	public static void tick() {
		//Increment heat
		heat += heatChange;
		heatChange --;

		if (heatChange < cooldownMod) { //Cap the amount that the heat can change by per tick to the cooldownMod
			heatChange = cooldownMod;
		}

		//Reset heat if it is below or above values
		if (heat < 0) {
			isOverheating = false;
			heat = 0;
			cooldownMod = -1.8;
			allowFire = true;
		}

		if (heat > 400) { //Weapon overheats if heat rises above 400
			isOverheating = true;
			heat = 400;
			cooldownMod = -1.3;
			allowFire = false;
		}

		if (Mouse.isDown() & !hasFired & !isOverheating & allowFire) { //If all the requirements of firing are met then fire
			hasFired = true;

			fireLaser();
		}

		if (!Mouse.isDown()) {
			hasFired = false;
		}

		//blink calcs
		blink += blinkMod;

		if (blink > 20 | blink < -20) {
			blinkMod *= -1;
		}
	}

	public static double getHeat() {
		return heat;
	}

	public static void setHeat(int newHeat) {
		heat = newHeat;
	}

	public static void fireLaser() {
		heatChange = getHeatChangeAfterFiring(heatChange); //New heat change value.

		//Get mouse and player positions
		int xPos = Player.getXInt() + 16;
		int yPos = Player.getYInt() + 25;
		int xFiring = (Mouse.getX() + Mouse.getWobbleX()) - Camera.getXOffSet();
		int yFiring = (Mouse.getY() + Mouse.getWobbleY()) - Camera.getYOffSet();

		//Create a new laser to the left/right of player
		if (xFiring > xPos) {
			LaserHandler.newLaser(xPos + 10, yPos, xFiring, yFiring, "player");
		} else {
			LaserHandler.newLaser(xPos - 10, yPos, xFiring, yFiring, "player");
		}

		double predictedHeat = heat + ((heatChange) * (heatChange + 1)) / 2; //Work out if the laser will overheat and if it will then stop the player from firing again
		if (predictedHeat > 400) {
			allowFire = false;
		}

		Flash.setWhiteFlash(Flash.getWhiteFlash() + 150); //Create white flash
	}

	public static void reset() { //Reset the heat and firing states
		heat = 0;
		cooldownMod = -1.8;
		hasFired = true;
		isOverheating = false;
	}

	private static double getHeatChangeAfterFiring(double curValue) {
		//Work out how much more heat will be added before heat is lost n(n + 1) / 2
		double endHeatChange = curValue * (curValue + 1) / 2;
		//Add 91 to this to factor in the extra shot fired
		double endHeatChangeFinish = endHeatChange += heatChangeFactor;

		//0 = n^2 + n - 2 x newEndHeatChange
		//ax^2 + bx + c = 0

		//Use the quadratic formula to figure out how much heat to add per tick (heatChange)
		double ret;
		ret = -1;
		ret += Math.sqrt(1 - 4 * (endHeatChangeFinish * -2));
		ret /= 2;

		return ret;
	}
}
