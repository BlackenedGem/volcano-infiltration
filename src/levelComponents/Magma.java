package levelComponents;

import game_logic.Camera;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Rectangle2D;

import particles.ParticleHandler;

public class Magma {
	private static double y = 930;
	private static double y_lightMax = 930;
	private static double waveHeight = 5;
	private static double riseFactor = 0.3;
	private static double waveMod = 0.7;
	
	public static void tick() {
		y -= riseFactor; //Make magma rise
		y_lightMax -= riseFactor; //Update lighting
		
		if (y <= (880 - Camera.getHeight())) { //Make sure lava doesn't go over the level height, not really needed since the player would die
			y = 880 - Camera.getHeight();
		}
		
		waveHeight -= waveMod; //Update the lava wave positioning
		
		if (waveHeight <= -5 | waveHeight >= 5) {
			waveMod *= -1;
		}
		
		//Spawn magma particles
		int particlesToSpawn = (int) (Math.random() * 4);
		
		for (int counter = 0; counter < particlesToSpawn; counter++) {
			ParticleHandler.newMagma((int) (Math.random() * Camera.getWidth()), (int) (Math.round((Magma.getLevel() - 2) + Math.random() * 5)), false); 
		}
	}
	
	public static void render(Graphics2D g2) {
		int y_lightMax_int = (int) y_lightMax;
		int y_int = (int) y;
		int y_end = y_int + Camera.getHeight();
		int waveRepeats = Math.round(Camera.getWidth() / 150) + 1;  //Amount of waves
		
		Color magmaColour = new Color(255, 160, 70, 255);
		Color magmaColourBottom = new Color(255, 200, 70, 255);
		
		//Magma glow
		GradientPaint  paint = new GradientPaint(0, (int) (y_int - 30 + Camera.getYOffSet()), new Color(255, 200, 70, 0), 0, y_int + Camera.getYOffSet(), magmaColourBottom);
	    g2.setPaint(paint);
	    g2.fill(new Rectangle2D.Double(0, y_int - 30 + Camera.getYOffSet(), Camera.getWidth(), y_int + Camera.getYOffSet()));
		
	    //Magma body
	    paint = new GradientPaint(0,y_lightMax_int + Camera.getYOffSet(),magmaColour,0, y_end + Camera.getYOffSet(),magmaColourBottom);
	    g2.setPaint(paint);
	    g2.fill(new Rectangle2D.Double(0,y_int + Camera.getYOffSet(),640,Camera.getHeight()));
	    
	    //Magma wave
	    g2.setStroke(new BasicStroke(2));
	    g2.setPaint(new Color(255, 100, 70, 180));
	    for (int counter = 0; counter < waveRepeats; counter++) {
	    	g2.draw(new CubicCurve2D.Double((counter * 150) + 0, y_int + Camera.getYOffSet(), (counter * 150) + 50, (y - waveHeight) + Camera.getYOffSet(), (counter * 150) + 100, (y + waveHeight) + Camera.getYOffSet(), (counter * 150) + 150, y_int + Camera.getYOffSet()));
	    }
	}
	
	
	public static double getLevel() {
		return y;
	}
	
	public static void setLevel(double newY) {
		y = newY;
		y_lightMax = newY;
	}
	
	public static void setRiseFactor(double newFactor) {
		riseFactor = newFactor;
	}
	
	public static void reset() {
		y = 930;
		y_lightMax = 930;
	}
}
