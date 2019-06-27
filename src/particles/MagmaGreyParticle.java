package particles;

import game_logic.Camera;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import levelComponents.Magma;

public class MagmaGreyParticle extends ParticleBase {
	private double xChange;
	private double yChange;

	public MagmaGreyParticle(int x, int y) {
		super(x, y, 200);
		
		//Random 'ish' direction
		xChange = Math.random();
		if (Math.random() > 0.5) {
			xChange = xChange * -1;
		}
		
		yChange = Math.random() * -5;
	}

	
	public void render(Graphics2D g2) {
		 g2.setPaint(new Color(150, 100, 0, life));
		 g2.fill(new Rectangle2D.Double(x + Camera.getXOffSet(),y + Camera.getYOffSet(),3,3));
	}

	
	public void tick() {
		x += xChange;
		y += yChange;
		
		yChange += 0.2;
		
		life--;
		
		if (life <= 0 | y > (Magma.getLevel() + 40)) {
			ParticleHandler.deleteMagma(index);
		}
	}

}
