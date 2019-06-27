package game_base;

import java.awt.RenderingHints;

public class RenderingParams {
	/*
	 * This class controls the rendering parameters to be used such as AntiAliasing to be accessed by the display
	 */
	
	private static Object antialias;
	private static Object quality;
	
	private static int AAMode;
	private static int qualityMode;
	
	public static void setAA(int mode) {
		AAMode = mode;
		
		if (mode == 0) {
			antialias = RenderingHints.VALUE_ANTIALIAS_DEFAULT;
		} else if (mode == 1 ){
			antialias = RenderingHints.VALUE_ANTIALIAS_ON;
		} else {
			antialias = RenderingHints.VALUE_ANTIALIAS_OFF;
		}
	}
	
	public static void setQuality(int mode) {
		qualityMode = mode;
		
		if (mode == 0) {
			quality = RenderingHints.VALUE_RENDER_DEFAULT;
		} else if (mode == 1) {
			quality = RenderingHints.VALUE_RENDER_QUALITY;
		} else {
			quality = RenderingHints.VALUE_RENDER_SPEED;
		}
	}
	
	public static Object getAA() {
		return antialias;
	}
	
	public static int getAAMode() {
		return AAMode;
	}

	public static int getQualityMode() {
		return qualityMode;
	}

	public static Object getQuality() {
		return quality;
	}
}
