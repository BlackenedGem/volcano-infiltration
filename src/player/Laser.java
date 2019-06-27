package player;

import game_logic.Camera;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import levelComponents.Magma;
import misc.Collisions;
import particles.ParticleHandler;
import audio.SoundEffectsHandler;

public class Laser {
	private double x;
	private double y;
	private double xVel;
	private double yVel;
	private int index;

	private String owner;

	private int chargeMod = 10;

	public Laser(int xPos, int yPos, int xFiring, int yFiring, String origin, int index) {
		double xChange;
		double yChange;

		x = xPos;
		y = yPos;
		owner = origin;
		this.index = index;


		//Adapted from a previous project
		//Calculate x and y velocities from mouse co-ords
		if (Math.abs(xFiring - xPos) > Math.abs(yFiring - yPos)) {
			xChange = xFiring - xPos;
			yChange = (yFiring - yPos) / Math.abs(xChange);
			xChange = xChange / Math.abs(xChange);
		} else {
			yChange = yFiring - yPos;
			xChange = (xFiring - xPos) / Math.abs(yChange);
			yChange = yChange / Math.abs(yChange);
		}

		xChange *= chargeMod;
		yChange *= chargeMod;

		xVel = xChange;
		yVel = yChange;

		SoundEffectsHandler.playSound("laser blast", SoundEffectsHandler.getDistanceFactor((int) x, (int) y, 40) - 8);
	}

	public void tick() {
		x += xVel;
		y += yVel;

		//Create red particles on the laser
		if (Math.random() > 0.6) {
			ParticleHandler.newLaser((int) x, (int) y, 10, 2);
		}

		//Below lava
		if (y > Magma.getLevel()) {
			int particlesToSpawn = (int) (Math.random() * 50 + 20);
			for (int counter = 0; counter < particlesToSpawn; counter++) {
				ParticleHandler.newMagma((int) x, (int) (Math.round((Magma.getLevel() - 2) + Math.random() * 5)), false); 
			}

			LaserHandler.removeParticle(index);
		}

		Collisions collisions = new Collisions((int) x, (int) y, owner); //Get a value that tells whether or not a collision has occured. If it does then it will be processed
		if (collisions.getColl() != 0) {
			switch (collisions.getColl()) {
			case 1: //If collided with wall/floor then play sound effect
				SoundEffectsHandler.playSound("laser surface hit", SoundEffectsHandler.getDistanceFactor((int) x, (int) y, 40) - 10);
				break;
			case 2: //Decrease player healeth
				Health.setHealth(Health.getHealth() - 100);
				break;
			case 3:
				collisions.getObj().hit();
				break;
			case 4:
				collisions.getObj().hit();
				break;
			}

			deleteSelf(); //Destroy the particle
		}

		//Code to despawn the particle if it gets outside the game. This should never happen unless the walls/floors are too thin or the laser too fast
		if (x < - 50 | x > (Camera.getWidth() + 50)) {
			System.out.println("Laser particle " + index + " noclipped");
			System.out.println("X co-ord: " + x);
			LaserHandler.removeParticle(index);
		}

		if (y > 1000 | y < ((Camera.getHeight() * -1) + 500)) {
			System.out.println("Laser particle " + index + " noclipped");
			System.out.println("Y co-ord: " + y);
			LaserHandler.removeParticle(index);
		}
	}

	public void render(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(3));
		g2.draw(new Line2D.Double(x + Camera.getXOffSet(), y + Camera.getYOffSet(), x - (xVel * 0.5) + Camera.getXOffSet(), y - (yVel * 0.5) + Camera.getYOffSet()));
	}

	public void deleteSelf() {
		int particlesToSpawn = (int) (Math.random() * 30 + 15);
		for (int counter = 0; counter < particlesToSpawn; counter++) {
			ParticleHandler.newLaser((int) x, (int) y, 10, 5);
		}

		LaserHandler.removeParticle(index);
	}

	public void setIndex(int newIndex) {
		index = newIndex;
	}
}
