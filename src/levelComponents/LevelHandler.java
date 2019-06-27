package levelComponents;

import game_base.Background;
import game_base.Screen;
import game_logic.Camera;
import game_logic.Data;
import game_logic.Score;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import misc.VarTypes;
import player.Player;

public class LevelHandler {
	//Level components
	private static ArrayList<Wall> walls = new ArrayList<Wall>();
	private static ArrayList<Wall> wallsFront = new ArrayList<Wall>();
	private static ArrayList<Wall> wallsInvisible = new ArrayList<Wall>();
	private static ArrayList<Floor> floors = new ArrayList<Floor>();
	private static ArrayList<Floor> floorsFront = new ArrayList<Floor>();
	private static ArrayList<Finish> finish = new ArrayList<Finish>();

	//Interactable elements
	private static ArrayList<AutomaticDoor> autoDoors = new ArrayList<AutomaticDoor>();
	private static ArrayList<BuzzSaw> buzzSaws = new ArrayList<BuzzSaw>();
	private static ArrayList<SteamTrap> steam = new ArrayList<SteamTrap>();
	private static ArrayList<LaserSwitch> laserSwitches = new ArrayList<LaserSwitch>();

	//Objects
	private static ArrayList<LevelObject> collects = new ArrayList<LevelObject>();
	private static ArrayList<Henchman> henchmen = new ArrayList<Henchman>();
	private static ArrayList<MediKit> medikits = new ArrayList<MediKit>();
	
	//private static ArrayList<LevelObject> object = new ArrayList<LevelObject>();

	//Background image
	private static BufferedImage img = null;

	public static void init() {
		Finish.init();
	}

	public static void tick() { //Send a tick to level elements
		for (int counter = 0; counter < wallsFront.size(); counter++) {
			wallsFront.get(counter).tick();
		}
		for (int counter = 0; counter < floorsFront.size(); counter++) {
			floorsFront.get(counter).tick();
		}
		Magma.tick();
		for (int counter = 0; counter < walls.size(); counter++) {
			walls.get(counter).tick();
		}
		for (int counter = 0; counter < autoDoors.size(); counter++) {
			autoDoors.get(counter).tick();
		}
		for (int counter = 0; counter < wallsInvisible.size(); counter++) {
			wallsInvisible.get(counter).tick();
		}
		for (int counter = 0; counter < floors.size(); counter++) {
			floors.get(counter).tick();
		}
		for (int counter = 0; counter < finish.size(); counter++) {
			finish.get(counter).tick();
		}
		for (int counter = 0; counter < buzzSaws.size(); counter++) {
			buzzSaws.get(counter).tick();
		}
		for (int counter = 0; counter < steam.size(); counter++) {
			steam.get(counter).tick();
		}
		for (int counter = 0; counter < laserSwitches.size(); counter++) {
			laserSwitches.get(counter).tick();
		}

		//Enemies
		for (int counter = 0; counter < henchmen.size(); counter++) {
			henchmen.get(counter).tick();
		}

		//Objects
		for (int counter = 0; counter < medikits.size(); counter++) {
			medikits.get(counter).tick();
		}
		for (int counter = 0; counter < collects.size(); counter++) {
			collects.get(counter).tick();
		}
	}

	public static void preRender(Graphics2D g2) { //Render background elements
		//Set moving objects (e.g. player) x co ords to remove rubber banding
		Player.setXRender(Player.getXInt());
		Player.setYRender(Player.getYInt());

		//Draw background image
		g2.drawImage(img,0 + Camera.getXOffSet(), (880 - Camera.getHeight()) + Camera.getYOffSet(), null);

		//Draw elements to be behind other elements
		for (int counter = 0; counter < finish.size(); counter++) {
			finish.get(counter).render(g2);
		}
		for (int counter = 0; counter < steam.size(); counter++) {
			steam.get(counter).render(g2);
		}
		for (int counter = 0; counter < collects.size(); counter++) {
			collects.get(counter).render(g2);
		}
		for (int counter = 0; counter < medikits.size(); counter++) {
			medikits.get(counter).render(g2);
		}
		for (int counter = 0; counter < laserSwitches.size(); counter++) {
			laserSwitches.get(counter).render(g2);
		}
	}

	public static void render(Graphics2D g2) { //Render foreground elements
		for (int counter = 0; counter < buzzSaws.size(); counter++) {
			buzzSaws.get(counter).render(g2);
		}
		for (int counter = 0; counter < walls.size(); counter++) {
			walls.get(counter).render(g2);
		}
		for (int counter = 0; counter < autoDoors.size(); counter++) {
			autoDoors.get(counter).render(g2);
		}
		for (int counter = 0; counter < wallsInvisible.size(); counter++) {
			//wallsInvisible.get(counter).render(g2); //Don't render walls because they are invisible!
		}
		for (int counter = 0; counter < floors.size(); counter++) {
			floors.get(counter).render(g2);
		}
		for (int counter = 0; counter < henchmen.size(); counter++) {
			henchmen.get(counter).render(g2);
		}
		Magma.render(g2);
		for (int counter = 0; counter < wallsFront.size(); counter++) {
			wallsFront.get(counter).render(g2);
		}
		for (int counter = 0; counter < floorsFront.size(); counter++) {
			floorsFront.get(counter).render(g2);
		}
	}

	public static void loadLevel(int level) {
		try {
			//Display loading screen
			Screen.setRenderScreen(false);
			//Handles to files
			Properties propsAutoDoors = new Properties();
			Properties propsBuzzSaws = new Properties();
			Properties propsData = new Properties();
			Properties propsWalls = new Properties();
			Properties propsWallsInvis = new Properties();
			Properties propsFloors = new Properties();
			Properties propsFinishes = new Properties();
			Properties propsHealths = new Properties();
			Properties propsHenchmen = new Properties();
			Properties propsLaptops = new Properties();
			Properties propsLSwitch = new Properties();
			Properties propsSteams = new Properties();
			InputStream configAutoDoors = null;
			InputStream configBuzzSaws = null;
			InputStream configData = null;
			InputStream configWalls = null;
			InputStream configWallsInvis = null;
			InputStream configFloors = null;
			InputStream configFinishes = null;
			InputStream configHealths = null;
			InputStream configHenchmen = null;
			InputStream configLaptop = null;
			InputStream configLSwitch = null;
			InputStream configSteams = null;

			String levelData = Data.getResourceLoc() + "level data/level" + level + "/";
			System.out.println("Loading level data from: " + levelData);


			//Load from disk
			configAutoDoors = new FileInputStream(new File(levelData + "autoDoors.properties"));
			configBuzzSaws = new FileInputStream(new File(levelData + "buzzSaws.properties"));
			configData = new FileInputStream(new File(levelData + "data.properties"));
			configWalls = new FileInputStream(new File(levelData + "walls.properties"));
			configWallsInvis = new FileInputStream(new File(levelData + "wallsInvis.properties"));
			configFloors = new FileInputStream(new File(levelData + "floors.properties"));
			configFinishes = new FileInputStream(new File(levelData + "finishes.properties"));
			configHenchmen = new FileInputStream(new File(levelData + "henchmen.properties"));
			configHealths = new FileInputStream(new File(levelData + "healths.properties"));
			configLaptop = new FileInputStream(new File(levelData + "laptop.properties"));
			configLSwitch = new FileInputStream(new File(levelData + "laserSwitch.properties"));
			configSteams = new FileInputStream(new File(levelData + "steams.properties"));
			propsAutoDoors.load(configAutoDoors);
			propsBuzzSaws.load(configBuzzSaws);
			propsData.load(configData);
			propsWalls.load(configWalls);
			propsWallsInvis.load(configWallsInvis);
			propsFloors.load(configFloors);
			propsFinishes.load(configFinishes);
			propsHealths.load(configHealths);
			propsHenchmen.load(configHenchmen);
			propsLaptops.load(configLaptop);
			propsLSwitch.load(configLSwitch);
			propsSteams.load(configSteams);

			//Read general level data
			int width = VarTypes.stringToInt(propsData.getProperty("Width"));
			int height = VarTypes.stringToInt(propsData.getProperty("Height"));
			int scoreMod = VarTypes.stringToInt(propsData.getProperty("ScoreModifier"));
			int playerX = VarTypes.stringToInt(propsData.getProperty("PlayerX"));
			int playerY = VarTypes.stringToInt(propsData.getProperty("PlayerY"));
			double magmaSpeed = Double.parseDouble(propsData.getProperty("MagmaSpeed"));
			double magmaStart = Double.parseDouble(propsData.getProperty("MagmaStart"));

			//Set factors
			Camera.setWidth(width);
			Camera.setHeight(height);
			Magma.setRiseFactor(magmaSpeed);
			Magma.setLevel(magmaStart);
			Player.setX(playerX);
			Player.setY(playerY);
			Score.setScoreMod(scoreMod);
			//Debug output
			System.out.println("Width: " + width);
			System.out.println("Height: " + height);
			System.out.println("Player X: " + playerX);
			System.out.println("Player Y: " + playerY);
			System.out.println("Magma rise factor: " + magmaSpeed);
			System.out.println("Magma initial level: " + magmaStart);


			//Object data
			int totAutoDoors = VarTypes.stringToInt(propsData.getProperty("AutoDoors"));
			int totBuzzSaws = VarTypes.stringToInt(propsData.getProperty("BuzzSaws"));
			int totWalls = VarTypes.stringToInt(propsData.getProperty("Walls"));
			int totWallsInvis = VarTypes.stringToInt(propsData.getProperty("WallsInvis"));
			int totFloors = VarTypes.stringToInt(propsData.getProperty("Floors"));
			int totFinishes = VarTypes.stringToInt(propsData.getProperty("Finishes"));
			int totHenchmen = VarTypes.stringToInt(propsData.getProperty("Henchmen"));
			int totHealths = VarTypes.stringToInt(propsData.getProperty("Healths"));
			int totLaptops = VarTypes.stringToInt(propsData.getProperty("Collects"));
			int totLSwitches = VarTypes.stringToInt(propsData.getProperty("LaserSwitches"));
			int totSteams = VarTypes.stringToInt(propsData.getProperty("Steams"));

			//AutoDoors
			System.out.println("Loading Automatic door data");
			for (int counter = 1; counter <= totAutoDoors; counter++) {
				System.out.println("Automatic door #" + counter);
				int door_x = VarTypes.stringToInt(propsAutoDoors.getProperty("Door" + counter + "_X"));
				int door_y = VarTypes.stringToInt(propsAutoDoors.getProperty("Door" + counter + "_Y"));
				int door_width = VarTypes.stringToInt(propsAutoDoors.getProperty("Door" + counter + "_Width"));
				int door_height = VarTypes.stringToInt(propsAutoDoors.getProperty("Door" + counter + "_Height"));
				int door_mode = VarTypes.stringToInt(propsAutoDoors.getProperty("Door" + counter + "_Mode"));
				double door_closeF = VarTypes.stringToDouble(propsAutoDoors.getProperty("Door" + counter + "_CloseFactor"));
				boolean door_useFloors = VarTypes.stringToBool(propsAutoDoors.getProperty("Door" + counter + "_UseFloors"));
				boolean door_stealthMode = VarTypes.stringToBool(propsAutoDoors.getProperty("Door" + counter + "_StealthMode"));

				autoDoors.add(new AutomaticDoor(door_x, door_y, door_width, door_height, door_mode, door_closeF, door_useFloors, door_stealthMode));
			}

			System.out.println("Loading Buzz Saw data");
			for (int counter = 1; counter <= totBuzzSaws; counter++) {
				System.out.println("Buzz saw #" + counter);
				int saw_x = VarTypes.stringToInt(propsBuzzSaws.getProperty("Saw" + counter + "_X"));
				int saw_y = VarTypes.stringToInt(propsBuzzSaws.getProperty("Saw" + counter + "_Y"));
				String saw_pathing = propsBuzzSaws.getProperty("Saw" + counter + "_Pathing");

				buzzSaws.add(new BuzzSaw(saw_x, saw_y, saw_pathing));
			}

			//Finishes
			System.out.println("Loading finish data");
			for (int counter = 1; counter <= totFinishes; counter++) {
				System.out.println("Finish #" + counter);
				int finish_x = VarTypes.stringToInt(propsFinishes.getProperty("Finish" + counter + "_X"));
				int finish_y = VarTypes.stringToInt(propsFinishes.getProperty("Finish" + counter + "_Y"));

				finish.add(new Finish(finish_x, finish_y));
			}

			//Floors
			System.out.println("Loading floor data");
			for (int counter = 1; counter <= totFloors; counter++) {
				System.out.println("Floor #" + counter);
				int floor_x = VarTypes.stringToInt(propsFloors.getProperty("Floor" + counter + "_X"));
				int floors_y = VarTypes.stringToInt(propsFloors.getProperty("Floor" + counter + "_Y"));
				int floors_width = VarTypes.stringToInt(propsFloors.getProperty("Floor" + counter + "_Width"));
				int floors_height = VarTypes.stringToInt(propsFloors.getProperty("Floor" + counter + "_Height"));

				floors.add(new Floor(floor_x, floors_y, floors_width, floors_height));
			}

			//Healths
			System.out.println("Loading health data");
			for (int counter = 1; counter <= totHealths; counter++) {
				System.out.println("Health #" + counter);
				int health_x = VarTypes.stringToInt(propsHealths.getProperty("Health" + counter + "_X"));
				int health_y = VarTypes.stringToInt(propsHealths.getProperty("Health" + counter + "_Y"));
				int health_regen = VarTypes.stringToInt(propsHealths.getProperty("Health" + counter + "_Regen"));

				medikits.add(new MediKit(health_x, health_y, health_regen));
			}

			//Henchmen
			System.out.println("Loading henchmen data");
			for (int counter = 1; counter <= totHenchmen; counter++) {
				System.out.println("Henchman #" + counter);
				int henchman_x = VarTypes.stringToInt(propsHenchmen.getProperty("Henchman" + counter + "_X"));
				int henchman_y = VarTypes.stringToInt(propsHenchmen.getProperty("Henchman" + counter + "_Y"));
				int henchman_health = VarTypes.stringToInt(propsHenchmen.getProperty("Henchman" + counter + "_Health"));
				int henchman_cooldown = VarTypes.stringToInt(propsHenchmen.getProperty("Henchman" + counter + "_Cooldown"));
				int henchman_detection = VarTypes.stringToInt(propsHenchmen.getProperty("Henchman" + counter + "_DetectionRange"));
				double henchman_detectionCool = VarTypes.stringToDouble(propsHenchmen.getProperty("Henchman" + counter + "_DetectionCooldown"));
				int henchman_response = VarTypes.stringToInt(propsHenchmen.getProperty("Henchman" + counter + "_DetectionResponse"));
				String henchman_pathing = propsHenchmen.getProperty("Henchman" + counter + "_Pathing");

				henchmen.add(new Henchman(henchman_x, henchman_y, henchman_health, henchman_cooldown, henchman_detection, henchman_detectionCool, henchman_response, henchman_pathing));
			}
			
			//Laptops
			System.out.println("Loading laptop data");
			for (int counter = 1; counter <= totLaptops; counter++) {
				System.out.println("Laptop #" + counter);
				int laptop_x = VarTypes.stringToInt(propsLaptops.getProperty("Laptop" + counter + "_X"));
				int laptop_y = VarTypes.stringToInt(propsLaptops.getProperty("Laptop" + counter + "_Y"));
				double laptop_bonus = VarTypes.stringToDouble(propsLaptops.getProperty("Laptop" + counter + "_Bonus"));
				
				collects.add(new Laptop(laptop_x, laptop_y, laptop_bonus));
			}

			//Laser switches
			System.out.println("Loading laser switch data");
			for (int counter = 1; counter <= totLSwitches; counter++) {
				System.out.println("Laser switch #" + counter);
				int lSwitch_x = VarTypes.stringToInt(propsLSwitch.getProperty("Switch" + counter + "_X"));
				int lSwitch_y = VarTypes.stringToInt(propsLSwitch.getProperty("Switch" + counter + "_Y"));
				boolean lSwitch_toggle = VarTypes.stringToBool(propsLSwitch.getProperty("Switch" + counter + "_Toggleable"));
				int lSwitch_Or = VarTypes.stringToInt(propsLSwitch.getProperty("Switch" + counter + "_Orientation"));
				
				int lSwitch_IDs = VarTypes.stringToInt(propsLSwitch.getProperty("Switch" + counter + "_DoorIDs"));
				int lSwitch_ID[] = new int[lSwitch_IDs];
				
				for (int counter2 = 0; counter2 < lSwitch_IDs; counter2++) {
					int id = counter2 + 1;
					lSwitch_ID[counter2] = VarTypes.stringToInt(propsLSwitch.getProperty("Switch" + counter + "_DoorID" + id)) - 1;
				}

				laserSwitches.add(new LaserSwitch(lSwitch_x, lSwitch_y, lSwitch_toggle, lSwitch_ID, lSwitch_Or));
			}

			//Steams
			System.out.println("Loading steam data");
			for (int counter = 1; counter <= totSteams; counter++) {
				System.out.println("Steam #" + counter);
				int steam_x = VarTypes.stringToInt(propsSteams.getProperty("Steam" + counter + "_X"));
				int steam_y = VarTypes.stringToInt(propsSteams.getProperty("Steam" + counter + "_Y"));
				int steam_freq = VarTypes.stringToInt(propsSteams.getProperty("Steam" + counter + "_Frequency"));
				int steam_dur = VarTypes.stringToInt(propsSteams.getProperty("Steam" + counter + "_Duration"));
				int steam_intensity = VarTypes.stringToInt(propsSteams.getProperty("Steam" + counter + "_Intensity"));
				int steam_startWait = VarTypes.stringToInt(propsSteams.getProperty("Steam" + counter + "_StartWait"));

				steam.add(new SteamTrap(steam_x, steam_y, steam_freq, steam_dur, steam_intensity, steam_startWait));
			}

			//Walls
			System.out.println("Loading wall data");
			for (int counter = 1; counter <= totWalls; counter++) {
				System.out.println("Wall #" + counter);
				int wall_x = VarTypes.stringToInt(propsWalls.getProperty("Wall" + counter + "_X"));
				int wall_y = VarTypes.stringToInt(propsWalls.getProperty("Wall" + counter + "_Y"));
				int wall_width = VarTypes.stringToInt(propsWalls.getProperty("Wall" + counter + "_Width"));
				int wall_height = VarTypes.stringToInt(propsWalls.getProperty("Wall" + counter + "_Height"));

				walls.add(new Wall(wall_x, wall_y, wall_width, wall_height));
			}

			//WallsInvis
			System.out.println("Loading wall invisible data");
			for (int counter = 1; counter <= totWallsInvis; counter++) {
				System.out.println("Wall Invis #" + counter);
				int wallInvis_x = VarTypes.stringToInt(propsWallsInvis.getProperty("WallInvis" + counter + "_X"));
				int wallInvis_y = VarTypes.stringToInt(propsWallsInvis.getProperty("WallInvis" + counter + "_Y"));
				int wallInvis_width = VarTypes.stringToInt(propsWallsInvis.getProperty("WallInvis" + counter + "_Width"));
				int wallInvis_height = VarTypes.stringToInt(propsWallsInvis.getProperty("WallInvis" + counter + "_Height"));

				wallsInvisible.add(new Wall(wallInvis_x, wallInvis_y, wallInvis_width, wallInvis_height));
			}

			Background.loadGameBK(levelData + "background.png");

			//Make Boundaries
			wallsFront.add(new Wall(0, (880 - height), 15, height));
			wallsFront.add(new Wall((width - 20), (880 - height),15, height));
			floors.add(new Floor(0,(880 - height),width,10));
			floors.add(new Floor(30, 850, (width - 30), 30));


			Screen.setRenderScreen(true);
			System.out.println("Level loaded");

		} catch (Exception e) {
			System.err.println("An error occured while loading level");
			e.printStackTrace();
			System.exit(1);
		} 
	}

	public static void reset() { //Delete the level elements
		autoDoors.clear();
		buzzSaws.clear();
		walls.clear();
		wallsFront.clear();
		wallsInvisible.clear();
		floors.clear();
		floorsFront.clear();
		finish.clear();
		collects.clear();
		medikits.clear();
		steam.clear();
		henchmen.clear();
		laserSwitches.clear();
	}

	//Getters for collision
	public static ArrayList<AutomaticDoor> getAutoDoors() {
		return autoDoors;
	}

	public static ArrayList<Wall> getWalls() {
		return walls;
	}

	public static ArrayList<Wall> getWallsFront() {
		return wallsFront;
	}

	public static ArrayList<Wall> getWallsInvisible() {
		return wallsInvisible;
	}

	public static ArrayList<Floor> getFloors() {
		return floors;
	}

	public static ArrayList<Floor> getFloorsFront() {
		return floorsFront;
	}

	public static ArrayList<Finish> getFinish() {
		return finish;
	}

	public static ArrayList<Henchman> getHenchmen() {
		return henchmen;
	}

	public static ArrayList<LaserSwitch> getLaserSwitch() {
		return laserSwitches;
	}
}
