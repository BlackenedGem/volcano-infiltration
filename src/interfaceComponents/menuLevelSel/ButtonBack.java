package interfaceComponents.menuLevelSel;

import game_base.Screen;
import interfaceComponents.MenuButton;

import java.awt.Color;
import java.awt.Font;


public class ButtonBack extends MenuButton {
	int level;
	
	public ButtonBack () {
		super();
		
		this.setWidth(210);
		this.setHeight(70);
		this.setXPos(20);
		this.setXOffset(45);
		this.setYPos(870);
		this.setYOffset(50);
		
		this.setBorderSize(10);
		
		this.setColourBody(Color.GRAY);
		this.setColourBodyFiringOver(Color.BLACK);
		this.setColourBorder(Color.BLACK);
		this.setColourText(Color.BLACK);
		this.setColourTextMouseOver(Color.GRAY);
		
		this.setText("Back");
		this.setTextFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
	}
	
	public void clickedEvent() {
		Screen.setScreen(5);
	}
}