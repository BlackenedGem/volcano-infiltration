package interfaceComponents.menuLevelSel;

import game_logic.ElitedLevels;
import game_logic.LevelState;
import game_logic.Unlock;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class ProgressCompletion {
	private static int progress = 50;
	
	/*
	 * 3 progress point for completing each level
	 * 2 points for eliting the level
	 */
	
	public static void updateData() {
		int progressPoints = 0;
		int totLevels = LevelState.getTotLevels();
		int levelsUnlocked = Unlock.getMaxLevel();
		
		progressPoints += 3 * (levelsUnlocked - 1); //Completing each level
		
		for (int counter = 1; counter <= totLevels; counter++) { //Eliting each level
			if (ElitedLevels.isElite(counter)) {
				progressPoints += 2;
			}
		}
		
		progress = (progressPoints * 100) / (totLevels * 5);
	}
	
	public static void render(Graphics2D g2) {
		g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 23));
		g2.setColor(Color.BLACK);
		g2.drawString(progress + "% Complete", 255, 890);
		
		g2.setColor(Color.GRAY);
		g2.fill(new Rectangle2D.Double(252, 907, 360, 35));
		
		g2.setColor(new Color(85, 220, 40));
		g2.fill(new Rectangle2D.Double(252, 907, (360 * progress) / 100, 35));

		g2.setStroke(new BasicStroke(4));
		g2.setPaint(Color.BLACK);
		g2.draw(new Rectangle2D.Double(250,905,360,37));
	}
}
