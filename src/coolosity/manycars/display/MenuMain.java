package coolosity.manycars.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import coolosity.manycars.main.ManyCarsMain;

public class MenuMain extends Menu
{
	
	public MenuMain(ManyCarsMain main)
	{
		super(main);
	}

	private static final int BUTTON_NEWGAME = 0;
	private static final int BUTTON_NUMCARS = 1;
	private static final int BUTTON_LEADERBOARD = 2;
	
	@Override
	public void draw(BufferedImage img, int mx, int my)
	{
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		
		int hoverButton = -1;
		
		int bwid = img.getWidth()*2/3;
		int bhei = 100;
		int bx = (img.getWidth()-bwid)/2;
		int cury = 10;
		Font bfont = new Font("Comic Sans MS",Font.PLAIN,32);
		
		if(drawButton(img,bx,cury,bwid,bhei,bfont,"New Game",mx,my))
		{
			hoverButton = BUTTON_NEWGAME;
		}
		cury += bhei+10;
		if(drawButton(img,bx,cury,bwid,bhei,bfont,"Num Cars: "+main.getNumCars(),mx,my))
		{
			hoverButton = BUTTON_NUMCARS;
		}
		cury += bhei+10;
		if(drawButton(img,bx,cury,bwid,bhei,bfont,"Leaderboard",mx,my))
		{
			hoverButton = BUTTON_LEADERBOARD;
		}
		
		this.hoverButton = hoverButton;
	}

	@Override
	public void mousePressed()
	{
		if(hoverButton == BUTTON_NEWGAME)
		{
			main.newGame();
		}
		else if(hoverButton == BUTTON_NUMCARS)
		{
			int numcars = 0;
			while(numcars<1||numcars>9)
			{
				numcars = main.getInputInt("Number of Cars [1-9]");
				if(numcars<1||numcars>9)
				{
					main.showMessage("Must be between 1 and 9, try again");
				}
			}
			main.setNumCars(numcars);
		}
		else if(hoverButton == BUTTON_LEADERBOARD)
		{
			main.openLocalLeaderboard();
		}
	}
}
