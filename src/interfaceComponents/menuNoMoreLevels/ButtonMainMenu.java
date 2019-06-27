package interfaceComponents.menuNoMoreLevels;

import game_base.Screen;
import game_logic.LevelState;
import interfaceComponents.MenuButton;

import java.awt.Color;
import java.awt.Font;

public class ButtonMainMenu extends MenuButton {
	
	public ButtonMainMenu() {
		super();

		this.setWidth(250);
		this.setHeight(70);
		this.setXPos(190);
		this.setXOffset(20);
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
