package coolosity.manycars.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LeaderboardHandler
{

	private static ArrayList<Score> localScores;
	
	public static void loadLocalScores()
	{
		localScores = new ArrayList<Score>();
		
		File scores = new File(Utils.workingDirectory+"scores.txt");
		
		try
		{
			if(!scores.exists())scores.createNewFile();
			BufferedReader reader = new BufferedReader(new FileReader(scores));
			String line;
			while((line=reader.readLine())!=null)
			{
				localScores.add(Score.fromString(line));
			}
			reader.close();
		}
		catch(IOException e)
		{
			System.err.println("An error occurred while loading local scores");
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Score> getScoresFor(int numCars)
	{
		ArrayList<Score> ret = new ArrayList<Score>();
		for(Score s : localScores)
		{
			if(s.getNumCars()==numCars)ret.add(s);
		}
		return ret;
	}
	
	public static void saveScore(Score score)
	{
		localScores.add(score);
		saveScores();
	}
	
	public static void saveScores()
	{
		File scores = new File(Utils.workingDirectory+"scores.txt");
		
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(scores));
			for(Score s : localScores)
			{
				writer.write(s.getSaveString());
				writer.newLine();
			}
			writer.close();
		}
		catch(IOException e)
		{
			System.err.println("An error occurred while saving local scores");
			e.printStackTrace();
		}
	}
}
