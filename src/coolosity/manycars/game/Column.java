package coolosity.manycars.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import coolosity.manycars.core.Resources;

public class Column
{
	
	private static final double carWidth = 0.25;
	private static final double carHeightOfWidth = 2.727;
	private static final double carYLoc = 0.80;
	private static final int spawnMinTicks = 60;
	private static final int spawnMaxTicks = 100;
	private static final double offsetPerTick = 10.0/Game.TPS;
	private static final double objectSpeed = 0.01;
	
	private static final double objSize = .25;
	
	private Random random;
	private ArrayList<MyObject> objects;
	private int carCol;
	private double nextSpawn;
	private BufferedImage boat;
	private double carOffset;
	private double objHeight = 0.05;
	private double carHeight = 0.1;
	private boolean dead;
	private double oceanOffset;
	
	public Column(Random random, Color color)
	{
		this.random = random;
		
		BufferedImage bot = Resources.getImage("boat");
		boat = new BufferedImage(bot.getWidth(),bot.getHeight(),bot.getType());
		for(int x=0;x<bot.getWidth();x++)
		{
			for(int y=0;y<bot.getHeight();y++)
			{
				Color c = new Color(bot.getRGB(x, y), true);
				if(c.getRed()==107 && c.getGreen()==106 && c.getBlue()==106)
					c = color;
				boat.setRGB(x, y, c.getRGB());
			}
		}
		
		objects = new ArrayList<MyObject>();
		newSpawn();
	}
	
	public void draw(BufferedImage img)
	{
		int midWid = 3;
		int carWid = (int)(img.getWidth()*carWidth);
		int carHei = (int)(carWid*carHeightOfWidth);
		int carY = (int)(img.getHeight()*carYLoc);
		int objWid = (int)(img.getWidth()*objSize);
		int objHei = objWid;
		objHeight = objHei*1.0/img.getHeight();
		carHeight = carHei*1.0/img.getHeight();
		
		Graphics g = img.getGraphics();
		
		//Background
		BufferedImage ocean = Resources.getImage("ocean");
		int ohei = ocean.getHeight()*img.getWidth()/ocean.getWidth();
		double reset = ohei*1.0/img.getHeight();
		while(oceanOffset>=reset)oceanOffset -= reset;
		int cury = (int)(img.getHeight()*oceanOffset)-ohei;
		while(cury<img.getHeight())
		{
			g.drawImage(ocean, 0, cury, img.getWidth(), ohei, null);
			cury += ohei;
		}
		
		//Center line
		g.setColor(Color.WHITE);
		g.fillRect(img.getWidth()/2-midWid/2, 0, midWid, img.getHeight());
		
		//Car
		g.drawImage(boat, carCol*img.getWidth()/2+(img.getWidth()/2-carWid)/2+(int)(carOffset*img.getWidth()/2), carY, carWid, carHei, null);
		
		//Objects
		for(MyObject obj : objects)
		{
			int xloc = obj.getCol()*img.getWidth()/2+(img.getWidth()/2-objWid)/2;
			int yloc = (int)(obj.getYLoc()*img.getHeight());
			if(obj.getType()==0)
			{
				g.drawImage(Resources.getImage("gas"), xloc, yloc-objHei, objWid, objHei, null);
			}
			else
			{
				g.drawImage(Resources.getImage("mine"), xloc, yloc-objHei, objWid, objHei, null);
			}
		}
	}
	
	public void tick(double modifier, double delta)
	{
		oceanOffset += objectSpeed*modifier*delta;
		for(int i=objects.size()-1;i>=0;i--)
		{
			MyObject obj = objects.get(i);
			obj.setYLoc(obj.getYLoc()+objectSpeed*modifier*delta);
			
			boolean rm = false;
			if(obj.getCol()==carCol)
			{
				if(obj.getYLoc()>=carYLoc && obj.getYLoc()<=carYLoc+carHeight || obj.getYLoc()+objHeight>=carYLoc && obj.getYLoc()+objHeight<=carYLoc)
				{
					if(obj.getType()==1)
					{
						dead = true;
					}
					else
					{
						objects.remove(i);
						rm = true;
					}
				}
			}
			
			if(obj.getYLoc()>=1.0 && obj.getType()==0)
			{
				dead = true;
			}
			
			if(!rm && obj.getYLoc()>=1.0+objHeight)
			{
				objects.remove(i);
			}
		}
		
		nextSpawn -= 1.0*modifier*delta;
		if(nextSpawn<=0)
		{
			objects.add(new MyObject(random.nextInt(2),random.nextInt(2),0));
			newSpawn();
		}
		
		if(Math.abs(carOffset)>0.0001)
		{
			double d = Math.abs(carOffset)/carOffset*offsetPerTick*delta;
			if(Math.abs(d)>Math.abs(carOffset))
				d = carOffset;
			carOffset -= d;
		}
	}
	
	public void changeLane()
	{
		carCol = 1-carCol;
		carOffset = carCol==1?-1:1;
	}
	
	private void newSpawn()
	{
		nextSpawn = random.nextInt(spawnMaxTicks-spawnMinTicks+1)+spawnMinTicks;
	}
	
	public boolean isDead()
	{
		return dead;
	}
}
