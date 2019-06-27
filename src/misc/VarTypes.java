package misc;

public class VarTypes { //Simple methods to simplify loading config files
	public static boolean stringToBool(String str) {
		boolean ret = false;
		
		try {
			ret = Boolean.parseBoolean(str);
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
		}
		
		return ret;
	}
	
	public static int stringToInt(String str) {
		int ret = 0;
		
		try {
			ret = Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
			ret = 0;
		}
		
		return ret;
	}
	
	public static double stringToDouble(String str) {
		double ret = 0;
		
		try {
			ret = Double.parseDouble(str);
		} catch (Exception e) {
			e.printStackTrace();
			ret = 0;
		}
		
		return ret;
	}
}
