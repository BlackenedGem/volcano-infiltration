package interfaceComponents.menuPause;

import game_base.Screen;
import game_logic.LevelState;
import interfaceComponents.MenuButton;

import java.awt.Color;
import java.awt.Font;

public class ButtonRetry extends MenuButton {
	public ButtonRetry () {
		super();
		
		this.setWidth(350);
		this.setHeight(70);
		this.setXPos(130);
		this.setXOffset(120);
		this.setYPos(470);
		this.setYOffset(45);
		
		this.setBorderSize(10);
		
		this.setColourBody(Color.GRAY);
		this.setColourBodyFiringOver(Color.BLACK);
		this.setColourBorder(Color.BLACK);
		this.setColourText(Color.BLACK);
		this.setColourTextMouseOver(Color.GRAY);
		
		this.setText("Retry");
		this.setTextFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
	}
	
	public void clickedEvent() {
		LevelState.retryLevel();
		Screen.setScreen(1);
	}
}

