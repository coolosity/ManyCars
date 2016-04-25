package coolosity.manycars.core;

public class Score implements Comparable<Score>
{

	public static Score fromString(String str)
	{
		String[] spl = str.split(":");
		try
		{
			return new Score(spl[0],Integer.parseInt(spl[1]),Long.parseLong(spl[2]),Integer.parseInt(spl[3]));
		}
		catch(Exception e)
		{
			System.err.println("An error occured while loading scores from string "+str);
			return null;
		}
	}
	
	public String getSaveString()
	{
		return name+":"+score+":"+time+":"+numCars;
	}
	
	private String name;
	private int score;
	private long time;
	private int numCars;
	
	public Score(String name, int score, long time, int numCars)
	{
		this.name = name;
		this.score = score;
		this.time = time;
		this.numCars = numCars;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public long getTime()
	{
		return time;
	}
	
	public int getNumCars()
	{
		return numCars;
	}

	@Override
	public int compareTo(Score s)
	{
		return s.getScore()-score;
	}
}
