package coolosity.manycars.core;

import java.io.File;

public class Utils
{

	public static String OS;
	public static String workingDirectory;
	
	public static void init()
	{
		OS = (System.getProperty("os.name")).toUpperCase();
		if (OS.contains("WIN"))
		{
		    workingDirectory = System.getenv("AppData");
		}
		else
		{
		    workingDirectory = System.getProperty("user.home")+"/Library/Application Support";
		}
		workingDirectory += File.separator+"ManyCars"+File.separator;
		File dir = new File(workingDirectory);
		dir.mkdirs();
	}
}