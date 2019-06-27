package particles;

import java.awt.Graphics2D;
import java.util.ArrayList;

import player.LaserHandler;

public class ParticleHandler {
	//Variables to store particle objects
	private static ArrayList<ParticleBase> laser = new ArrayList<ParticleBase>();
	private static ArrayList<ParticleBase> magma = new ArrayList<ParticleBase>();
	private static ArrayList<ParticleBase> red = new ArrayList<ParticleBase>();
	private static ArrayList<ParticleBase> steam = new ArrayList<ParticleBase>();

	public static void renderNormal(Graphics2D g2) { //Render particles
		//long start = System.currentTimeMillis();
		
		for (int counter = 0; counter < laser.size(); counter++) {
			laser.get(counter).render(g2);
		}
		for (int counter = 0; counter < steam.size(); counter++) {
			steam.get(counter).render(g2);
		}
		for (int counter = 0; counter < magma.size(); counter++) {
			magma.get(counter).render(g2);
		}
		
		//System.out.println(System.currentTimeMillis() - start);
	}
	public static void renderHigh(Graphics2D g2) { //Render foreground particles (Obsolete)
	}
	public static void renderTop(Graphics2D g2) { //Render top level particles
		for (int counter = 0; counter < red.size(); counter++) {
			red.get(counter).render(g2);
		}
	}
	public static void tick() { //Update particles
		for (int counter = 0; counter < laser.size(); counter++) {
			laser.get(counter).tick();
		}
		for (int counter = 0; counter < red.size(); counter++) {
			red.get(counter).tick();
		}
		for (int counter = 0; counter < magma.size(); counter++) {
			magma.get(counter).tick();
		}
		for (int counter = 0; counter < steam.size(); counter++) {
			steam.get(counter).tick();
		}
	}

	public static void newLaser(int x, int y, int lifeMod, double movementConst) { //Create new laser particle
		laser.add(new LaserParticle(x, y, lifeMod, movementConst));
	}
	public static void newRed(int x, int y, int lifeMod, double movementConst) { //Create a new red particle
		red.add(new RedParticle(x, y, lifeMod, movementConst));
	}
	public static void newMagma(int x, int y, boolean grey) { //Create a new magma particle
		if (!grey) { //Grey factor is used when the lava goes through a wall
			magma.add(new MagmaParticle(x,y));
		} else {
			magma.add(new MagmaGreyParticle(x, y));
		}
	}
	public static void newSteam(int x, int y, double xConst, double yConst) { //Create a new steam particle
		steam.add(new SteamParticle(x, y, xConst, yConst));
	}
	//Delete particle methods
	public static void deleteLaser(int index) {
		laser.remove(index);
		for (int counter = 0; counter < laser.size(); counter++) {
			laser.get(counter).setIndex(counter);
		}
	}
	public static void deleteRed(int index) {
		red.remove(index);
		//Re index the array list
		for (int counter = 0; counter < red.size(); counter++) {
			red.get(counter).setIndex(counter);
		}
	}
	public static void deleteMagma(int index) {
		magma.remove(index);
		//Re index the array list
		for (int counter = 0; counter < magma.size(); counter++) {
			magma.get(counter).setIndex(counter);
		}
	}
	public static void deleteSteam(int index) {
		steam.remove(index);
		for (int counter = 0; counter < steam.size(); counter++) {
			steam.get(counter).setIndex(counter);
		}
	}
	public static void deleteAll() {
		LaserHandler.deleteAll();
		laser.clear();
		magma.clear();
		red.clear();
		steam.clear();
	}

	public static int getParticles() { //Return the amount of particles
		int particles = laser.size() + red.size() + magma.size() + steam.size();
		particles += LaserHandler.getParticles();

		return particles;
	}
}
