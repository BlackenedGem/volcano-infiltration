package interfaceComponents;

import game_logic.LevelState;

import interfaceComponents.menuLevelSel.ProgressCompletion;

import java.awt.Graphics2D;
import java.util.ArrayList;


public class MenuHandler {
	private static ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	
	public static void render(Graphics2D g2) { //Render the buttons
		for (int counter = 0; counter < buttons.size(); counter++) {
			buttons.get(counter).render(g2);
		}
		for (int counter = 0; counter < buttons.size(); counter++) {
			buttons.get(counter).renderMouseOver(g2);
		}
	}

	public static void tick() { //Send a game tick to the buttons
		for (int counter = 0; counter < buttons.size(); counter++) {
			buttons.get(counter).tick();
		}
	}
	//Create appropriate buttons
	public static void load_GameOver() {
		addButton("interfaceComponents.menuGameOver.ButtonRetry");
		addButton("interfaceComponents.menuGameOver.ButtonMainMenu");
	}
	public static void load_LevelComplete() {
		addButton("interfaceComponents.menuLevelComplete.ButtonNext");
		addButton("interfaceComponents.menuLevelComplete.ButtonMainMenu");
	}
	public static void load_LevelSelect() {
		ProgressCompletion.updateData();
		for (int counter = 1; counter <= LevelState.getTotLevels(); counter++) {
			addButton("interfaceComponents.menuLevelSel.ButtonLevelX");
			buttons.get(buttons.size() - 1).setParams(counter);
		}
		addButton("interfaceComponents.menuLevelSel.ButtonBack");
	}
	public static void load_MenuHelp() {
		addButton("interfaceComponents.menuLevelSel.ButtonBack");
	}
	public static void load_MenuNames () {
		addButton("interfaceComponents.menuName.ButtonHelp");
		addButton("interfaceComponents.menuName.ButtonLevelSelect");
		addButton("interfaceComponents.menuName.ButtonPlay");
	}
	public static void load_NoMoreLevels() {
		addButton("interfaceComponents.menuNoMoreLevels.ButtonMainMenu");
	}
	public static void load_Pause() {
		addButton("interfaceComponents.menuPause.ButtonMenu");
		addButton("interfaceComponents.menuPause.ButtonResume");
		addButton("interfaceComponents.menuPause.ButtonRetry");
	}
	public static void unload() { //Delete buttons
		for (int counter = 0; counter < buttons.size(); ) {
			buttons.remove(counter);
		}
	}
	public static void addButton(String className) { //Create a new button
		try {
			buttons.add((MenuButton) Class.forName(className).newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
