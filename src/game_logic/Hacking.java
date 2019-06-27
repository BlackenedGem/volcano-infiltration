package game_logic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Hacking {
	private static int completion = 0;
	private static int pulsate = 0;
	private static int pulsateMod = 0;
	
	public static int getCompletion() {
		return completion;
	}
	
	public static void setCompletion(int newCompletion) {
		completion = newCompletion;
	}
	
	public static void reset() {
		completion = 0;
	}
	
	public static void tick() {
		completion--;
		
		if (completion < 0) {
			completion = 0;
		}
		
		pulsate += pulsateMod;
		
		if (pulsate > 60 | pulsate <= 0) {
			pulsateMod *= -1;
		}
	}
	
	public static void render(Graphics2D g2) {
		if (completion > 0) {
			String msg = "Accessing";
			
			if (completion > 75) {
				msg = "Uploading";
			} else if (completion > 50) {
				msg = "Processing";
			} else if (completion > 25) {
				msg = "Extracting";
			}
			
			g2.setPaint(new Color(0, 100 - pulsate, 250 - pulsate));
			g2.fill(new Rectangle2D.Double(20,849,completion * 6,27));	
			
			g2.setStroke(new BasicStroke(4));
			g2.setPaint(Color.BLACK);
			g2.draw(new Rectangle2D.Double(20,848,600,26));
			
			g2.setColor(Color.BLACK);
			g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
			g2.drawString(msg, 280, 866);
		}
	}
}
