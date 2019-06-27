package levelComponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import audio.SoundEffectsHandler;

public class AutomaticDoor implements LevelObject {
	private int x;
	private int y;
	private int width;
	private int height;

	private int mode; //1 for closed when switch off //2 for open when switch off
	private double closeFactor; //Amount to close/open the door by
	private double openAmount; //Amount the door is currently open

	private boolean closed = true; //Is the door/wall closed or not
	private boolean useFloors = false; //Use floors as the object instead of walls
	private boolean stealthMode; //Use the same colour as walls

	private LevelObject doorTop; //Top part of door
	private LevelObject doorBottom; //Bottom part of door

	public AutomaticDoor(int door_x, int door_y, int door_width,
			int door_height, int door_mode, double door_closeF, boolean
			door_useFloors, boolean door_stealthMode) {

		//Basic data
		this.x = door_x;
		this.y = door_y;
		this.width = door_width;
		this.height = door_height;

		//Door open/close info
		this.stealthMode = door_stealthMode;
		this.useFloors = door_useFloors;
		this.mode = door_mode;
		this.closeFactor = door_closeF;
		this.openAmount = height / 2;

		if (mode == 2) { //Set door to open if on mode 2
			openAmount = 0;
			closed = !closed;
		}
		
		int openAmount_int = (int) openAmount;

		//Initate door objects
		if (useFloors) {
			doorTop = new Floor(x, y, width, openAmount_int);
			doorBottom = new Floor(x, y + (height - openAmount_int), width, openAmount_int);
			
			LevelHandler.getFloors().add((Floor) doorTop);
			LevelHandler.getFloors().add((Floor) doorBottom);
		} else {
			doorTop = new Wall(x, y, width, openAmount_int);
			doorBottom = new Wall(x, y + (height - openAmount_int), width, openAmount_int);
			
			LevelHandler.getWalls().add((Wall) doorTop);
			LevelHandler.getWalls().add((Wall) doorBottom);
		}
		
		//Set door colour to slightly darker than the other doors if not using stealth mode
		if (!stealthMode) {
			doorTop.setColour(new Color(110, 110, 110));
			doorBottom.setColour(new Color(110, 110, 110));
		}
	}

	public static void init() {
	}

	public void tick() {
		if ((openAmount > 0 & !closed) | (openAmount < height / 2 & closed)) { //If the door is in the process of opening/closing the process that

			if (closed) {
				openAmount += closeFactor;
			} else {
				openAmount -= closeFactor;
			}

			//Play open/close sound effects
			if (openAmount <= 0) {
				openAmount = 0;
				SoundEffectsHandler.playSound("door close", SoundEffectsHandler.getDistanceFactor(x + width / 2, y + height / 2, 30) + 1);
			}

			if (openAmount >= height / 2) {
				openAmount = height / 2;
				SoundEffectsHandler.playSound("door close", SoundEffectsHandler.getDistanceFactor(x + width / 2, y + height / 2, 30) + 1);
			}

			//Change values
			doorTop.setHeight((int) openAmount);
			doorBottom.setY((int) ((y + height) - openAmount));
			doorBottom.setHeight((int) openAmount);
		}

		//If the player is in the door whilst it's closed then remove health
		doorTop.collisionRemoveHealth();
		doorBottom.collisionRemoveHealth();

		//Tick
		doorTop.tick();
		doorBottom.tick();
	}

	public void render(Graphics2D g2) {
		doorTop.render(g2);
		doorBottom.render(g2);
	}

	public void changeState() {
		//Make the door start closing or opening
		closed = !closed;
		SoundEffectsHandler.playSound("hydraulics", SoundEffectsHandler.getDistanceFactor(x + width / 2, y + height / 2, 15) -1);
	}

	public ArrayList<Floor> getFloors() {
		//Return the door objects for anything that wants them
		
		ArrayList<Floor> floors = new ArrayList<Floor>();

		if (useFloors) {
			floors.add((Floor) doorTop);
			floors.add((Floor) doorBottom);
		}

		return floors;
	}

	public ArrayList<Wall> getWalls() {
		//Return the door objects for anything that wants them
		ArrayList<Wall> walls = new ArrayList<Wall>();

		if (!useFloors) {
			walls.add((Wall) doorTop);
			walls.add((Wall) doorBottom);
		}

		return walls;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	
	public void setColour(Color newColour) {
		//Void
	}

	
	public void collisionRemoveHealth() {
		//Void
	}

	
	public void hit() {
	}
}
