package coolosity.manycars.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import coolosity.manycars.core.Resources;
import coolosity.manycars.main.ManyCarsMain;

public class MenuMain extends Menu
{
	
	public MenuMain(ManyCarsMain main)
	{
		super(main);
	}

	private static final int BUTTON_NEWGAME = 0;
	private static final int BUTTON_LEADERBOARD = 2;
	private static final int BUTTON_LEFT = 3;
	private static final int BUTTON_RIGHT = 4;
	
	@Override
	public void draw(BufferedImage img, int mx, int my)
	{
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		
		int hoverButton = -1;
		
		int bwid = img.getWidth()*2/3;
		int bhei = 100;
		int bspace = 10;
		int arrowsize = 96;
		int bx = (img.getWidth()-bwid)/2;
		int cury = 10;
		Font bfont = new Font("Comic Sans MS",Font.PLAIN,32);
		
		BufferedImage[] dbutton = Resources.getTiledImage("defaultButton", 100, 25);

		//New game button
		if(drawButton(img,bx,cury,bwid,bhei,dbutton[0],dbutton[1],mx,my))
		{
			hoverButton = BUTTON_NEWGAME;
		}
		Resources.drawString(img, "Play Game", 35, hoverButton==BUTTON_NEWGAME?Color.WHITE:Color.BLACK, img.getWidth()/2, cury+bhei/2, Resources.DRAW_CENTER, Resources.DRAW_MIDDLE);
		cury += bhei+bspace;
		
		//Num cars selection
		{
			g.setFont(bfont);
			g.setColor(Color.BLACK);
			String txt = "Num Cars: "+main.getNumCars();
			//g.drawString(txt, (img.getWidth()-fm.stringWidth(txt))/2, cury+arrowsize/2+fm.getHeight()/3-3);
			Resources.drawString(img, txt, 25, Color.BLACK, img.getWidth()/2, cury+bhei/2, Resources.DRAW_CENTER, Resources.DRAW_MIDDLE);;
		}
		BufferedImage[] arrows = Resources.getTiledImage("arrows", 32, 32);
		if(drawButton(img,bx,cury,arrowsize,arrowsize,arrows[0],arrows[1],mx,my))
		{
			hoverButton = BUTTON_LEFT;
		}
		if(drawButton(img,bx+bwid-arrowsize,cury,arrowsize,arrowsize,arrows[2],arrows[3],mx,my))
		{
			hoverButton = BUTTON_RIGHT;
		}
		cury += arrowsize+bspace;
		
		//Leaderboard button
		if(drawButton(img,bx,cury,bwid,bhei,dbutton[0],dbutton[1],mx,my))
		{
			hoverButton = BUTTON_LEADERBOARD;
		}
		Resources.drawString(img, "leaderboard", 35, hoverButton==BUTTON_LEADERBOARD?Color.WHITE:Color.BLACK, img.getWidth()/2, cury+bhei/2, Resources.DRAW_CENTER, Resources.DRAW_MIDDLE);
		
		this.hoverButton = hoverButton;
	}

	@Override
	public void mousePressed()
	{
		if(hoverButton == BUTTON_NEWGAME)
		{
			main.newGame();
		}
		else if(hoverButton == BUTTON_LEADERBOARD)
		{
			main.openLocalLeaderboard();
		}
		else if(hoverButton == BUTTON_LEFT)
		{
			int numcars = main.getNumCars()-1;
			if(numcars<1)numcars=1;
			main.setNumCars(numcars);
		}
		else if(hoverButton == BUTTON_RIGHT)
		{
			int numcars = main.getNumCars()+1;
			if(numcars>9)numcars=9;
			main.setNumCars(numcars);
		}
	}
}
