package game_base;

import game_logic.Camera;
import game_logic.DebugInfo;
import game_logic.Hacking;
import game_logic.Score;
import interfaceComponents.MenuHandler;
import interfaceComponents.menuLevelSel.ProgressCompletion;
import interfaceComponents.menuName.InputName;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import levelComponents.LevelHandler;
import particles.ParticleHandler;
import player.Flash;
import player.Health;
import player.LaserHandler;
import player.LaserSight;
import player.Player;
import player.WeaponHeat;

public class RenderEngine {

	public static void render(Graphics2D g2) {

		if (Screen.renderScreen() != true) { //If the game is doing something blocking (such as loading level data) then show the loading screen
			Background.renderLoading(g2);
			return;
		}

		Background.render(g2); //Render background

		switch (Screen.getScreen()) { //Call sub method for each screen ID
		case 1:
			renderGame(g2);
			break;
		case 2:
			renderGamePaused(g2);
			break;
		case 3:
			renderLevelComplete(g2);
			break;
		case 4:
			renderGameOver(g2);
			break;
		case 5:
			renderMenu_NameSel(g2);
			break;
		case 6:
			renderNoMoreLevels(g2);
			break;
		case 7:
			renderMenu_LevelSel(g2);
			break;
		case 8:
			renderMenu_Help(g2);
			break;
		}

		//Draw debug info and mouse
		DebugInfo.update();
		DebugInfo.render(g2);
		Mouse.render(g2);
	}
	//Render code for each screen below
	private static void renderGame(Graphics2D g2) {	
		//Calculate offsets
		Camera.preRender();

		//Pre-render
		LevelHandler.preRender(g2);
		LaserSight.render(g2);

		//Super Secret Spy
		LaserHandler.render(g2);
		Player.render(g2);

		ParticleHandler.renderNormal(g2);

		//Level components
		LevelHandler.render(g2);

		ParticleHandler.renderHigh(g2);

		//Status bar at bottom
		g2.setPaint(Color.DARK_GRAY);
		g2.fill(new Rectangle2D.Double(0,880,640,85));

		Score.render(g2);
		Hacking.render(g2);
		Health.render(g2);
		WeaponHeat.render(g2);

		ParticleHandler.renderTop(g2);
		Flash.render(g2);
	}

	private static void renderGamePaused(Graphics2D g2) {
		renderGame(g2);

		//Faded black box
		g2.setPaint(new Color(0,0,0, 150));
		g2.fill(new Rectangle2D.Double(0,0,640,960)); 

		MenuHandler.render(g2);
	}

	private static void renderLevelComplete(Graphics2D g2) {
		MenuHandler.render(g2);
		Score.render(g2);
		ParticleHandler.renderNormal(g2);
	}

	private static void renderGameOver(Graphics2D g2) {
		MenuHandler.render(g2);
		Score.render(g2);
		ParticleHandler.renderNormal(g2);
	}

	private static void renderMenu_Help(Graphics2D g2) {
		MenuHandler.render(g2);
		ParticleHandler.renderNormal(g2);
	}
	
	private static void renderMenu_LevelSel(Graphics2D g2) {
		MenuHandler.render(g2);
		ProgressCompletion.render(g2);
		ParticleHandler.renderNormal(g2);
	}

	private static void renderMenu_NameSel(Graphics2D g2) {
		//Buttons and inputs
		InputName.render(g2);
		MenuHandler.render(g2);
		
		ParticleHandler.renderNormal(g2);
	}

	private static void renderNoMoreLevels(Graphics2D g2) {
		MenuHandler.render(g2);
		ParticleHandler.renderNormal(g2);
	}
}
