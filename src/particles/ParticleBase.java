package particles;

import java.awt.Graphics2D;

public abstract class ParticleBase { //Base for particles to extend upon. Use tick method to update positioning and render to draw to screen
	protected int index;
	protected double x;
	protected double y;
	protected int life;
	
	abstract public void render(Graphics2D g2);
	abstract public void tick();
	
	public ParticleBase(int x, int y, int life) {
		this.x = x;
		this.y = y;
		this.life = life;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int newIndex) {
		this.index = newIndex;
	}
	
	public int getLife() {
		return life;
	}
	
	public void setLife(int newLife) {
		life = newLife;
	}
}
