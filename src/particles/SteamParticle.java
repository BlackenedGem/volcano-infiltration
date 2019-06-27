package particles;

import game_logic.Camera;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import levelComponents.Magma;
import player.Health;
import player.Player;

public class SteamParticle extends ParticleBase {
	private double xVel;
	private double yVel;
	
	public SteamParticle(int xPos, int yPos, double xConst, double yConst) {
		super(xPos, yPos, 100);
		
		//Random 'ish' direction
		xVel = 0.2 + Math.random() * 0.2;
		yVel = 0.2 + Math.random() * 0.2;
		
		if (xConst == 0) {
			if (Math.random() > 0.5) {
				xVel *= -1;
			}
		} else {
			xVel *= xConst;
		}
		
		if (yConst == 0) {
			if (Math.random() > 0.5) {
				yVel *= -1;
			}
		} else {
			yVel *= yConst;
		}
	}

	
	public void render(Graphics2D g2) {
		g2.setPaint(new Color(100, 100, 100, life));
		g2.fill(new Rectangle2D.Double(x + Camera.getXOffSet(),y + Camera.getYOffSet(),3,3));
	}

	
	public void tick() {
		x += xVel;
		y += yVel;
		
		life -= 5;
		
		if (life <= 0) {
			ParticleHandler.deleteSteam(index);
		}
		
		//If touching the player then decrease the health of the player
		if (x < Player.getX() + 32 & x > Player.getX()) {
			if (y > Player.getY() & y < Player.getY() + 64) {
				if (Math.random() < 0.1) {
					Health.setHealth(Health.getHealth() - 1);
				}
			}
		}
		
		//If touching the lava then spawn in a few lava particles (The steam chucks up the lava)
		if (y > Magma.getLevel()) {
			int particlesToSpawn = (int) (Math.random() * 2 + 1);
			for (int counter = 0; counter < particlesToSpawn; counter++) {
				ParticleHandler.newMagma((int) x, (int) (Math.round((Magma.getLevel() - 2) + Math.random() * 5)), false); 
			}
			
			ParticleHandler.deleteSteam(index);
		}
	}
}
