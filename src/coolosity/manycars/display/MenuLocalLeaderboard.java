package coolosity.manycars.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import coolosity.manycars.core.LeaderboardHandler;
import coolosity.manycars.core.Score;
import coolosity.manycars.main.ManyCarsMain;

public class MenuLocalLeaderboard extends Menu
{

	private static final int BUTTON_MAINMENU = 0;
	private static final int BUTTON_TAB = 1;
	
	private HashMap<Integer,ArrayList<Score>> scores;
	private int currentTab;
	private int hoverTab;
	
	public MenuLocalLeaderboard(ManyCarsMain main, int startingTab)
	{
		super(main);
		scores = new HashMap<Integer,ArrayList<Score>>();
		for(int i=1;i<=9;i++)
		{
			scores.put(i, LeaderboardHandler.getScoresFor(i));
			Collections.sort(scores.get(i));
		}
		currentTab = startingTab;
	}

	@Override
	public void draw(BufferedImage img, int mx, int my)
	{
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		
		int hoverButton = -1;
		if(drawButton(img, 10, 10, 185, 34, new Font("Courier",Font.BOLD,32), "Main Menu", mx, my, -4))
		{
			hoverButton = BUTTON_MAINMENU;
		}
		
		Font header = new Font("Arial",Font.PLAIN,45);
		FontMetrics hfm = g.getFontMetrics(header);
		Font sub = new Font("Arial",Font.PLAIN,35);
		//FontMetrics sfm = g.getFontMetrics(sub);
		Font font = new Font("Arial",Font.PLAIN,28);
		FontMetrics fm = g.getFontMetrics(font);
		g.setFont(header);
		g.setColor(Color.BLACK);
		String txt = "Local Leaderboard";
		int cury = hfm.getHeight()+30;
		g.drawString(txt, (img.getWidth()-hfm.stringWidth(txt))/2, cury);
		
		int curx = 10;
		int wid = 50;
		int space = 8;
		int hei = 35;
		cury += 10;
		int hoverTab = -1;
		for(int i=1;i<=9;i++)
		{
			txt = i+"";
			int tw = wid;
			if(i==currentTab)
			{
				txt += " car"+(i==1?"":"s");
				tw += 60;
			}
			if(drawButton(img,curx,cury,tw,hei,sub,txt,mx,my))
			{
				hoverButton = BUTTON_TAB;
				hoverTab = i;
			}
			curx += tw+space;
		}
		this.hoverTab = hoverTab;
		cury += 40;
		
		ArrayList<Score> scs = scores.get(currentTab);
		if(scs.size()>0)
		{
			g.setFont(font);
			int c = 0;
			for(Score s : scs)
			{
				g.setColor(c==0?Color.GRAY:Color.LIGHT_GRAY);
				g.fillRect(0, cury+7, img.getWidth(), fm.getHeight());
				g.setColor(Color.RED);
				cury += fm.getHeight();
				g.drawString(s.getName(), 30, cury);
				txt = s.getScore()+"";
				g.drawString(txt, img.getWidth()-fm.stringWidth(txt)-10, cury);
				c = 1-c;
			}
		}
		this.hoverButton = hoverButton;
	}

	@Override
	public void mousePressed()
	{
		if(hoverButton==BUTTON_MAINMENU)
		{
			main.openMainMenu();
		}
		if(hoverTab>=0)
		{
			currentTab = hoverTab;
		}
	}
}
