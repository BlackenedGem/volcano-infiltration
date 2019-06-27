package interfaceComponents.menuName;

import game_base.Screen;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class InputName {
	public static String name = "";
	public static int blink = 20;
	public static int blinkMod = -2;

	public static void tick() {
		//Simple bit of code so that the blink variable goes from 20 to -20 and back over and over
		blink += blinkMod;

		if (blink > 20 | blink < -20) {
			blinkMod *= -1;
		}
	}

	public static void render(Graphics2D g2) {
		String blinkChar = "";

		if (blink > 0) {
			blinkChar = "_"; //Add character to blink
		}

		g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		g2.setColor(new Color(80, 80, 80));

		g2.drawString(name + blinkChar, 55, 420);

		//Prompt
		g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		g2.setColor(new Color(50, 50, 50));
		g2.drawString("Enter Name:", 180, 350);

		//Text box
		g2.setStroke(new BasicStroke(4));
		g2.setPaint(Color.BLACK);
		g2.draw(new Rectangle2D.Double(50,380,510,50));
	}

	public static void keyPressed(char key, KeyEvent k) {
		if (Screen.getScreen() != 5) { //Only handle input if the screen is the name sel screen
			return;
		}

		if (key == KeyEvent.VK_BACK_SPACE) { //Process backspace key
			removeEndChar();
			return;
		}

		if (!Character.isLetterOrDigit(key) & !Character.isSpaceChar(key)) { //If keys are invalid then don't process them
			return;
		}


		Canvas c = new Canvas();
		name = name.concat(Character.toString(key));

		FontMetrics metrics = c.getFontMetrics(new Font(Font.SANS_SERIF, Font.BOLD, 50)); //If width is bigger than the box
		int width = metrics.stringWidth(name + "_");

		if (width > 640) {
			removeEndChar();
		}
	}

	private static void removeEndChar() { //Remove the last character from the name
		if (name.length() > 0) {
			name = name.substring(0, name.length() - 1);
		}

	}
	//Getters and setters
	public static String getName() {
		return name;
	}

	public static void setName(String newName) {
		name = newName;
	}
}