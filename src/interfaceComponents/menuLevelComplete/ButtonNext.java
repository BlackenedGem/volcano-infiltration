package interfaceComponents.menuLevelComplete;

import game_logic.LevelState;
import interfaceComponents.MenuButton;

import java.awt.Color;
import java.awt.Font;

public class ButtonNext extends MenuButton {
	public ButtonNext () {
		super();
		
		this.setWidth(350);
		this.setHeight(70);
		this.setXPos(130);
		this.setXOffset(90);
		this.setYPos(740);
		this.setYOffset(45);
		
		this.setBorderSize(10);
		
		this.setColourBody(Color.GRAY);
		this.setColourBodyFiringOver(Color.BLACK);
		this.setColourBorder(Color.BLACK);
		this.setColourText(Color.BLACK);
		this.setColourTextMouseOver(Color.GRAY);
		
		this.setText("Continue");
		this.setTextFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
	}

	
	public void clickedEvent() {
		LevelState.nextLevel();
	}

}
