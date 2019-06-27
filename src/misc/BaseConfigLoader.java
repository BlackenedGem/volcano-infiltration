package misc;

import game_base.RenderingParams;
import game_logic.Data;
import game_logic.ElitedLevels;
import game_logic.Unlock;
import interfaceComponents.menuName.InputName;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class BaseConfigLoader {
	public static void load() {
		Properties props = new Properties();
		InputStream file = null;

		//Load general level data
		try {
			file = new FileInputStream(new File(Data.getResourceLoc() + "data.properties"));
			props.load(file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		ElitedLevels.load(props.getProperty("elitedLevels"));
		RenderingParams.setAA(VarTypes.stringToInt(props.getProperty("gameEngine_AntiAliasing")));
		RenderingParams.setQuality(VarTypes.stringToInt(props.getProperty("gameEngine_Quality")));
		InputName.setName(props.getProperty("lastName"));
		Unlock.setMaxLevel(VarTypes.stringToInt(props.getProperty("maxLevel")));
	}

	public static void save() {
		//Save properties to file
		try {
			Properties props = new Properties();

			File file = new File(Data.getResourceLoc() + "data.properties");
			file.delete();

			file.createNewFile();


			props.load(new FileInputStream(file));

			props.setProperty("elitedLevels", ElitedLevels.getString());
			props.setProperty("gameEngine_AntiAliasing", String.valueOf(RenderingParams.getAAMode()));
			props.setProperty("gameEngine_Quality", String.valueOf(RenderingParams.getQualityMode()));
			props.setProperty("lastName", InputName.getName());
			props.setProperty("maxLevel", String.valueOf(Unlock.getMaxLevel()));

			OutputStream out = new FileOutputStream(file);
			props.store(out, "Data for Volcano Infiltration");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
