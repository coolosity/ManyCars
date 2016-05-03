package coolosity.manycars.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Game
{

	public static final int TPS = 60;
	
	private static final Color[] colors = {Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW};
	private static final Font scoreFont = new Font("Arial",Font.PLAIN,32);
	
	private ArrayList<Column> columns;
	private boolean scoreSaved;
	private double score;
	private double modifier;
	
	public Game(int numCars)
	{
		Random random = new Random();
		columns = new ArrayList<Column>();
		int curCol = 0;
		for(int i=0;i<numCars;i++)
		{
			columns.add(new Column(random,colors[curCol++]));
			if(curCol>=colors.length)curCol = 0;
		}
	}
	
	public void draw(BufferedImage img)
	{
		Graphics g = img.getGraphics();
		double wid = img.getWidth()*1.0/columns.size();
		for(int i=0;i<columns.size();i++)
		{
			int xx = (int)(i*wid);
			columns.get(i).draw(img.getSubimage(xx, 0, (int)wid, img.getHeight()));
		}
		g.setColor(Color.GREEN);
		int sepWid = 5;
		for(int i=1;i<columns.size();i++)
		{
			int xx = (int)(i*wid);
			g.fillRect(xx-sepWid/2, 0, sepWid, img.getHeight());
		}
		
		g.setFont(scoreFont);
		FontMetrics fm = g.getFontMetrics(scoreFont);
		String txt = getScore()+"";
		g.setColor(Color.WHITE);
		g.drawString(txt, img.getWidth()-fm.stringWidth(txt)-5, fm.getHeight()-5);
		txt = modifier+"";
		if(txt.length()>5)txt = txt.substring(0, 5);
		g.drawString(txt, 10, fm.getHeight()-5);
	}
	
	public int getScore()
	{
		/*int sum = 0;
		for(Column c : columns)sum += c.getScore();
		return sum;*/
		return (int)(score)/100;
	}
	
	public void tick(double delta)
	{
		if(isDead())return;
		
		double start = 1;
		double end = 1+1.6/columns.size();
		
		modifier = start+(end-start)/(Math.pow(1+1.6*Math.pow(Math.E, -1.3 * score/680), 10));
		
		for(Column c : columns)c.tick(modifier, delta);
		score+=delta;
	}
	
	public void moveColumn(int col)
	{
		if(isDead())return;
		if(col>=0 && col<columns.size())
		{
			columns.get(col).changeLane();
		}
	}
	
	public boolean isDead()
	{
		for(Column c : columns)if(c.isDead())return true;
		return false;
	}
	
	public boolean isScoreSaved()
	{
		return scoreSaved;
	}
	
	public void setScoreSaved(boolean scoreSaved)
	{
		this.scoreSaved = scoreSaved;
	}
}
