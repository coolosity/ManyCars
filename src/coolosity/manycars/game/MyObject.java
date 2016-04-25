package coolosity.manycars.game;

public class MyObject
{

	public static final int CIRCLE = 0;
	public static final int SQUARE = 1;
	
	private int type;
	private int col;
	private double yloc;
	
	public MyObject(int type, int col, double yloc)
	{
		this.type = type;
		this.col = col;
		this.yloc = yloc;
	}
	
	public int getType()
	{
		return type;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public double getYLoc()
	{
		return yloc;
	}
	
	public void setYLoc(double yloc)
	{
		this.yloc = yloc;
	}
}
