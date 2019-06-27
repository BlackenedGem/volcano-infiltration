package interfaceComponents;

import game_base.Mouse;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import audio.SoundEffectsHandler;

public abstract class MenuButton {
	//Template for button because I don't want to type this out over and over again
	
	protected int width = 200; //width
	protected int height = 100; //height
	protected int xPos = 0; //X co-ord on screen
	protected int xTextOffset = 0; //Amount to shift the text to the right
	protected int yPos = 0; //Y co-ord on the screen
	protected int yTextOffset = 0; //Amount to shift the text down
	
	protected int borderSize = 5; //Size of the border around the button
	
	protected String text = "missing no"; //Text of the button
	protected Font textFont = new Font(Font.SANS_SERIF, Font.BOLD, 15); //Font style
	
	protected Color colourBody = Color.black; //Colour of the inside of the button
	protected Color colourBodyMouseOver = Color.white; //Colour of the inside of the button when the mouse is over
	protected Color colourBorder = Color.blue;
	protected Color colourText = Color.white;
	protected Color colourTextMouseOver = Color.black;
	
	protected boolean mouseOver = false;
	protected int playSound = 0;
	
	abstract public void clickedEvent();
	
	public MenuButton() {
	}
	
	public void tick() {
		int mouseX = Mouse.getX();
		int mouseY = Mouse.getY();
		int x_end = xPos + width;
		int y_end = yPos + height;
		
		if ((mouseX < x_end & mouseX > xPos) & (mouseY < y_end & mouseY > yPos)) { //Check if the mouse is over the button
			mouseOver = true;
		} else {
			mouseOver = false;
		}
		
		if (mouseOver & Mouse.isDown()) { //Process event if the mouse clicks the button
			playSound++;
			
			if (playSound == 1) {
				SoundEffectsHandler.playSound("click", -5);
			}
			this.clickedEvent();
		} else {
			playSound = 0;
		}
	}
	
	public void render(Graphics2D g2) { //Draw the button to the screen
		g2.setStroke(new BasicStroke(borderSize));
		g2.setPaint(colourBorder);
		g2.draw(new Rectangle2D.Double(xPos,yPos,width,height));
		
		if (mouseOver) {
			g2.setPaint(colourBodyMouseOver);
		} else {
			g2.setPaint(colourBody);
		}
	    g2.fill(new Rectangle2D.Double(xPos,yPos,width,height));
	    
	    g2.setFont(textFont);
	    if (mouseOver) {
	    	g2.setColor(colourTextMouseOver);
	    } else {
	    	g2.setColor(colourText);
	    }
		g2.drawString(text, xPos + xTextOffset, yPos + yTextOffset);
	}
	
	public void renderMouseOver(Graphics2D g2) {
		//Only implemented if needed
	}
	
	//Setters and getters
	
	public void setBorderSize(int borderSize) {
		this.borderSize = borderSize;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	public void setXOffset(int xOffset) {
		this.xTextOffset = xOffset;
	}

	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public void setYOffset(int yOffset) {
		this.yTextOffset = yOffset;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTextFont(Font textFont) {
		this.textFont = textFont;
	}

	public void setColourBody(Color colourBody) {
		this.colourBody = colourBody;
	}

	public void setColourBodyFiringOver(Color colourBodyFiringOver) {
		this.colourBodyMouseOver = colourBodyFiringOver;
	}

	public void setColourBorder(Color colourBorder) {
		this.colourBorder = colourBorder;
	}

	public void setColourText(Color colourText) {
		this.colourText = colourText;
	}

	public void setColourTextMouseOver(Color colourTextMouseOver) {
		this.colourTextMouseOver = colourTextMouseOver;
	}
	
	public void setParams(int param1) {
		//void
	}
}
