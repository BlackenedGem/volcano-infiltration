package levelComponents;

import java.awt.Color;
import java.awt.Graphics2D;

public interface LevelObject { //Interface for LevelObject so different objects can be stored in one variable
	public void tick();
	public void render(Graphics2D g2);
	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();
	public void hit();
	public void setX(int x);
	public void setY(int y);
	public void setWidth(int width);
	public void setHeight(int height);
	public void setColour(Color newColour);
	public void collisionRemoveHealth();
}
