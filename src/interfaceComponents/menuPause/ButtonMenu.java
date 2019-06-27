package interfaceComponents.menuPause;

import game_base.Screen;
import game_logic.LevelState;
import interfaceComponents.MenuButton;

import java.awt.Color;
import java.awt.Font;

public class ButtonMenu extends MenuButton {
	
	public ButtonMenu () {
		super();
		
		this.setWidth(350);
		this.setHeight(70);
		this.setXPos(130);
		this.setXOffset(80);
		this.setYPos(570);
		this.setYOffset(45);
		
		this.setBorderSize(10);
		
		this.setColourBody(Color.GRAY);
		this.setColourBodyFiringOver(Color.BLACK);
		this.setColourBorder(Color.BLACK);
		this.setColourText(Color.BLACK);
		this.setColourTextMouseOver(Color.GRAY);
		
		this.setText("Main Menu");
		this.setTextFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
	}

	
	public void clickedEvent() {
		LevelState.reset();
		Screen.setScreen(5);
	}

}
