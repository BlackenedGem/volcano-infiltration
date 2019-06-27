package misc;

import java.util.ArrayList;

import player.Player;
import levelComponents.Floor;
import levelComponents.Henchman;
import levelComponents.LaserSwitch;
import levelComponents.LevelHandler;
import levelComponents.LevelObject;
import levelComponents.Wall;

public class Collisions {
	private int mode = 0;
	private LevelObject object = null;
	
	public Collisions(int x, int y, String owner) {

		ArrayList<Wall> walls = LevelHandler.getWalls();
		ArrayList<Wall> wallsFront = LevelHandler.getWallsFront();
		ArrayList<Wall> wallsInvisible = LevelHandler.getWallsInvisible();
		ArrayList<Floor> floors = LevelHandler.getFloors();
		ArrayList<Floor> floorsFront = LevelHandler.getFloorsFront();
		ArrayList<Henchman> henchmen = LevelHandler.getHenchmen();
		ArrayList<LaserSwitch> laserSwitches = LevelHandler.getLaserSwitch();

		//Walls
		for (int counter = 0; counter < walls.size(); counter++) {
			Wall obj = walls.get(counter);

			if (CollisionDetection.isCollided(x, y, obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight())) {
				mode = 1;
				return;
			}
		}
		//Walls Front
		for (int counter = 0; counter < wallsFront.size(); counter++) {
			Wall obj = wallsFront.get(counter);

			if (CollisionDetection.isCollided(x, y, obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight())) {
				mode = 1;
				return;
			}
		}
		//Walls Invisible
		for (int counter = 0; counter < wallsInvisible.size(); counter++) {
			Wall obj = wallsInvisible.get(counter);

			if (CollisionDetection.isCollided(x, y, obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight())) {
				mode = 1;
				return;
			}
		}
		//Floors
		for (int counter = 0; counter < floors.size(); counter++) {
			Floor obj = floors.get(counter);

			if (CollisionDetection.isCollided(x, y, obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight())) {
				mode = 1;
				return;
			}
		}
		//Floors Front
		for (int counter = 0; counter < floorsFront.size(); counter++) {
			Floor obj = floorsFront.get(counter);

			if (CollisionDetection.isCollided(x, y, obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight())) {
				mode = 1;
				return;
			}
		}
		//Henchmen
		for (int counter = 0; counter < henchmen.size(); counter++) {
			if (owner.equals("henchman")) {
				continue;
			}

			Henchman obj = henchmen.get(counter);

			if (!obj.getActive()) {
				continue;
			}

			if (CollisionDetection.isCollided(x, y, obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight())) {
				object = obj;
				mode = 3;
				return;
			}
		}
		//Laser Switches
		for (int counter = 0; counter < laserSwitches.size(); counter++) {
			LaserSwitch obj = laserSwitches.get(counter);

			if (CollisionDetection.isCollided(x, y, obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight())) {
				object = obj;
				mode = 4;
				return;
			}
		}

		if (!owner.equals("player")) {
			if (CollisionDetection.isCollided(x, y, Player.getXInt(), Player.getYInt(), 32, 64)) {
				mode = 2;
				return;
			}
		}
	}
	
	/**
	 * Returns 0 for no collisions
	 * Returns 1 for wall collisions
	 * Returns 2 for player collisions
	 * Returns 3 for enemy collision (and calls health deduction)
	 * Returns 4 for laser switch collision
	 **/
	
	public int getColl() {
		return mode;
	}
	
	public LevelObject getObj() {
		return object;
	}
}

