package coolosity.manycars.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import coolosity.manycars.main.ManyCarsMain;

public abstract class Menu
{

	protected ManyCarsMain main;
	protected int hoverButton;
	
	public Menu(ManyCarsMain main)
	{
		this.main = main;
	}
	
	public abstract void draw(BufferedImage img, int mx, int my);
	
	public abstract void mousePressed();
	

	protected boolean drawButton(BufferedImage img, int x, int y, int w, int h, Font font, String txt, int mx, int my)
	{
		return drawButton(img,x,y,w,h,font,txt,mx,my,0);
	}
	
	protected boolean drawButton(BufferedImage img, int x, int y, int w, int h, Font font, String txt, int mx, int my, int yoffset)
	{
		boolean hover = false;
		Graphics g = img.getGraphics();
		
		Color bg = Color.RED;
		if(mx>=x && my>=y && mx<x+w && my<y+h)
		{
			bg = Color.GREEN;
			hover = true;
		}
		
		g.setColor(bg);
		g.fillRect(x, y, w, h);
		
		g.setFont(font);
		g.setColor(Color.BLACK);
		FontMetrics fm = g.getFontMetrics(font);
		g.drawString(txt, x+(w-fm.stringWidth(txt))/2, y+h/2+fm.getHeight()/3+yoffset);
		
		return hover;
	}
}
