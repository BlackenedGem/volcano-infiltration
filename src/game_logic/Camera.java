package game_logic;

import player.Player;

public class Camera {
	private static int width = 640;
	private static int height = 880;
	private static int xOffSet = 0;
	private static int yOffSet = 0;

	public static void preRender() {
		//Work out camera position relative to level
		xOffSet = -(Player.getXInt() - 336);//336
		yOffSet = -(Player.getYInt() - 600);

		if (yOffSet < 0) {
			yOffSet = 0;
		}
		if (yOffSet > (height - 880)) { //-867

			yOffSet = (height - 880);
		}

		if (xOffSet > 0) {
			xOffSet = 0;
		}
		if (xOffSet < -(width - 640)) {
			xOffSet = -(width - 640);
		}
	}
	public static int getXOffSet() { //Get the amount that objects need to be adjusted in the X axis
		return xOffSet;
	}
	public static int getYOffSet() { //Get the amount that objects need to be adjusted in the X axis
		return yOffSet;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static void setWidth(int width) {
		Camera.width = width;
	}

	public static void setHeight(int height) {
		Camera.height = height;
	}
	
	public static void reset() {
		xOffSet = 0;
		yOffSet = 0;
	}
}
