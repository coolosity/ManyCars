package coolosity.manycars.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Resources
{

	public static final int DRAW_LEFT = 0;
	public static final int DRAW_CENTER = 1;
	public static final int DRAW_RIGHT = 2;
	public static final int DRAW_TOP = 3;
	public static final int DRAW_MIDDLE = 4;
	public static final int DRAW_BOTTOM = 5;
	
	private static HashMap<String,BufferedImage> images;
	private static HashMap<String,BufferedImage> alphabet;
	
	private static String[] res = {"boat","gas","mine","alphabet","arrows","defaultButton","ocean"};
	
	public static void loadResources()
	{
		images = new HashMap<String,BufferedImage>();
		
		try
		{
			for(String s : res)
			{
				images.put(s, ImageIO.read(new File("res/"+s+".png")));
			}
		} catch(IOException e) {
			System.err.println("An error occured while loading images");
			e.printStackTrace();
		}
		
		alphabet = new HashMap<String,BufferedImage>();
		String alpha = "abcdefghijklmnopqrstuvwxyz_-!?/\\";
		int perrow = 10;
		int pw = 5;
		int ph = 7;
		int ps = 1;
		BufferedImage img = getImage("alphabet");
		for(int i=0;i<alpha.length();i++)
		{
			int xx = i%perrow;
			int yy = i/perrow;
			String a = alpha.charAt(i)+"";
			alphabet.put(a, img.getSubimage(xx*(pw+ps), yy*(ph+ps), pw, ph));
		}
	}
	
	public static BufferedImage getImage(String img)
	{
		return images.get(img);
	}
	
	public static BufferedImage[] getTiledImage(String img, int width, int height)
	{
		BufferedImage imgg = getImage(img);
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		try
		{
			int curx = 0;
			int cury = 0;
			while(cury<imgg.getHeight())
			{
				images.add(imgg.getSubimage(curx, cury, width, height));
				curx += width;
				if(curx>=imgg.getWidth())
				{
					curx = 0;
					cury += height;
				}
			}
		} catch(Exception e) {
			return null;
		}
		BufferedImage[] imggs = new BufferedImage[images.size()];
		for(int i=0;i<images.size();i++)imggs[i] = images.get(i);
		return imggs;
	}
	
	public static void drawString(BufferedImage img, String str, int size, Color color, int x, int y, int drawStyleX, int drawStyleY)
	{
		str = str.toLowerCase();
		Graphics g = img.getGraphics();
		int curx = x;
		int len = stringLength(str,size);
		if(drawStyleX==DRAW_CENTER)curx -= len/2;
		else if(drawStyleX==DRAW_RIGHT)curx -= len;
		if(drawStyleY==DRAW_MIDDLE)y -= size/2;
		else if(drawStyleY==DRAW_BOTTOM)y -= size;
		for(int i=0;i<str.length();i++)
		{
			String c = str.charAt(i)+"";
			if(alphabet.containsKey(c))
			{
				BufferedImage td = alphabet.get(c);
				td = recolor(td,Color.BLACK,color);
				int width = (int)(td.getWidth()*1.0/td.getHeight()*size);
				g.drawImage(td, curx, y, width, size, null);
				curx += width+5;
			}
			else
			{
				if(c.equals(" "))
				{
					curx += size*9/16;
				}
			}
		}
	}
	
	public static BufferedImage recolor(BufferedImage img, Color search, Color replace)
	{
		BufferedImage n = new BufferedImage(img.getWidth(),img.getHeight(),img.getType());
		for(int i=0;i<n.getWidth();i++)
		{
			for(int j=0;j<n.getHeight();j++)
			{
				if(img.getRGB(i, j)==search.getRGB())
					n.setRGB(i, j, replace.getRGB());
				else
					n.setRGB(i, j, img.getRGB(i, j));
			}
		}
		return n;
	}
	
	public static int stringLength(String str, int size)
	{
		str = str.toLowerCase();
		int curx = 0;
		for(int i=0;i<str.length();i++)
		{
			String c = str.charAt(i)+"";
			if(alphabet.containsKey(c))
			{
				BufferedImage td = alphabet.get(c);
				int width = (int)(td.getWidth()*1.0/td.getHeight()*size);
				curx += width+5;
			}
			else
			{
				if(c.equals(" "))
				{
					curx += size*9/16;
				}
			}
		}
		return curx;
	}
}
