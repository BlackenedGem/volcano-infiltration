package game_logic;

import misc.BaseConfigLoader;
import misc.VarTypes;

public class ElitedLevels {
	public static boolean levels[] = new boolean [LevelState.getTotLevels()];

	public static void load(String newLevels) {
		try {

			for (int counter = 1; counter <= levels.length; counter++) { //Set all values to false to be on the safe side
				levels[counter - 1] = false;
			}

			String data[] = newLevels.split(";");

			int curLevel = 1; //Level to assign the data to 

			for (int counter = 0; counter < data.length; counter++) {
				if (data[counter].equalsIgnoreCase("")) { //Don't process if it is blank
					continue;
				}

				levels[curLevel - 1] = VarTypes.stringToBool(data[counter]); //Set the elited value
				curLevel++; //Increment level to process next
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isElite(int levelID) {
		return levels[levelID - 1];
	}

	public static void setElite(int levelID) {
		levels[levelID - 1] = true;
		BaseConfigLoader.save();
	}

	public static String getString() {
		String ret = "";

		for (int counter = 1; counter <= levels.length; counter++) {
			ret = ret.concat(";" + levels[counter - 1]);
		}

		ret = ret.substring(1, ret.length());
		return ret;
	}
}
