package misc;

public class CollisionDetection {
	public static boolean isCollided(double x, double y, int x2, int y2, int width, int height) {
		if (x > x2 & x < (x2 + width)) {
			if (y > y2 & y < (y2 + height)) {
				return true;
			}
		}

		return false;
	}
}
