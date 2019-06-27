package player;

import java.awt.Graphics2D;
import java.util.ArrayList;

import particles.ParticleHandler;

public class LaserHandler {
	private static ArrayList<Laser> lasers = new ArrayList<Laser>(); //Variable holding laser objects
	
	public static void render(Graphics2D g2) {
		for (int counter = 0; counter < lasers.size(); counter++) {
			lasers.get(counter).render(g2);
		}
	}
	public static void tick() {
		for (int counter = 0; counter < lasers.size(); counter++) {
			lasers.get(counter).tick();
		}
	}
	public static void newLaser(int x, int y, int firingX, int firingY, String owner) { //Create a new laser at x/y co-ords with the specified owner. Create particles around point of firing
		lasers.add(new Laser(x, y, firingX, firingY, owner, lasers.size()));
		
		int particles = (int) ((10 * Math.random()) + 5);
		
		for (int counter = 0; counter < particles; counter++) {
			ParticleHandler.newLaser((int) x, (int) y, 10, 3);
		}
	}
	
	public static int getParticles() {
		return lasers.size();
	}
	
	public static void removeParticle(int index) {
		if (lasers.size() == 0) {
			System.err.println("Tried to delete laser particle with 0 sized array");
			return;
		}
		
		lasers.remove(index);
		for (int counter = 0; counter < lasers.size(); counter++) {
			lasers.get(counter).setIndex(counter);
		}
	}
	
	public static void deleteAll() {
		for (int counter = 0; counter < lasers.size(); ) {
			lasers.remove(counter);
		}
	}
}
