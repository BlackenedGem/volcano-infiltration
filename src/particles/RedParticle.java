package particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class RedParticle extends ParticleBase {
	private double xChange;
	private double yChange;
	private int lifeMod;

	public RedParticle(int x, int y, int lifeMod, double movementConst) {
		super(x, y, 255);

		//Random 'ish' direction
		if (Math.random() > 0.5) {
			yChange = Math.random() * 0.4;
		} else {
			yChange = Math.random() * -0.4;
		}
		if (Math.random() > 0.5) {
			xChange = Math.random() * 0.4;
		} else {
			xChange = Math.random() * -0.4;
		}

		xChange *= movementConst;
		yChange *= movementConst;

		this.lifeMod = lifeMod;
	}

	
	public void render(Graphics2D g2) {
		g2.setPaint(new Color(250, 40, 40, life));
		g2.fill(new Rectangle2D.Double(x,y,3,3));
	}

	
	public void tick() {
		y += yChange;
		x += xChange;
		life -= lifeMod;

		if (life <= 0) {
			life = 0;
			ParticleHandler.deleteRed(index);
		}
	}

}
