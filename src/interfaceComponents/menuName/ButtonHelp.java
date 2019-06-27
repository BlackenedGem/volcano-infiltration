package interfaceComponents.menuName;

import game_base.Screen;
import interfaceComponents.MenuButton;

import java.awt.Color;
import java.awt.Font;

import misc.BaseConfigLoader;


public class ButtonHelp extends MenuButton {
	public ButtonHelp () {
		super();
		
		this.setWidth(350);
		this.setHeight(70);
		this.setXPos(130);
		this.setXOffset(116);
		this.setYPos(670);
		this.setYOffset(47);
		
		this.setBorderSize(10);
		
		this.setColourBody(Color.GRAY);
		this.setColourBodyFiringOver(Color.BLACK);
		this.setColourBorder(Color.BLACK);
		this.setColourText(Color.BLACK);
		this.setColourTextMouseOver(Color.GRAY);
		
		this.setText("HELP");
		this.setTextFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
	}
	
	public void clickedEvent() {
		Screen.setScreen(8);
		BaseConfigLoader.save();
	}
}
