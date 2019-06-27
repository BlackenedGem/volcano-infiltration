package game_logic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import particles.ParticleHandler;

public class DebugInfo {
	//Debug variables
	private static long lastTick = System.currentTimeMillis();
	private static long lastFrame = System.currentTimeMillis();
	private static int curFrames = 0;
	private static int curTicks = 0;
	private static int tps = 50;
	private static int fps = 50;
	//Time between each update
	private static final int reportTime = 250;
	//Show debug screen
	private static boolean show = false;

	public static void tick() {
		try {
			long timeSinceLastChange = System.currentTimeMillis() - lastTick;
			
			if (timeSinceLastChange >= reportTime) {
				tps = (int) (curTicks / (timeSinceLastChange / 1000));
				
				curTicks = 0;
				lastTick = System.currentTimeMillis();
			}
		}
		catch (Exception e) {

		}
		
		curTicks++;
	}

	public static void render(Graphics2D g2) {
		//Render debug info at top left
		if (!show) { 
			return; 
		}
		
		g2.setPaint(new Color(100, 100, 100, 100));
		g2.fill(new Rectangle2D.Double(10, 5, 120, 50));

		g2.setColor(Color.WHITE);
		g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		g2.drawString("TPS: " + tps + "/40", 15, 20);
		g2.drawString("FPS: " + fps, 15, 35);
		g2.drawString("Particles: " + ParticleHandler.getParticles(), 15, 50);
		
		
	}
	
	public static void update() {
		try {
			long timeSinceLastChange = System.currentTimeMillis() - lastFrame;
			
			if (timeSinceLastChange >= reportTime) {
				fps = (int) (curFrames / (timeSinceLastChange / 1000));
				
				curFrames = 0;
				lastFrame = System.currentTimeMillis();
			}
		}
		catch (Exception e) {

		}
		
		curFrames++;
	}
	
	public static void keyPressed () {
		show = !show; //Toggle visibility
	}
	
	public static int getFPS() {
		return fps;
	}
	
	public static int getTPS() {
		return tps;
	}
}
