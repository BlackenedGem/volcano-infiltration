package particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class HealthParticle extends ParticleBase {
	private double xChange;
	private double yChange;

	public HealthParticle(int x, int y) {
		super(x, y, 255);
		
		//Random 'ish' direction
		yChange = Math.random() * -0.4;
		if (Math.random() > 0.5) {
			xChange = Math.random() * 0.4;
		} else {
			xChange = Math.random() * -0.4;
		}
		
		
	}

	
	public void render(Graphics2D g2) {
		g2.setPaint(new Color(250, 40, 40, life));
		g2.fill(new Rectangle2D.Double(x,y,3,3));
	}

	
	public void tick() {
		y += yChange;
		x += xChange;
		life -= 2;
		
		life--;
		
		if (life <= 0) {
			ParticleHandler.deleteRed(index);
		}
	}

}
