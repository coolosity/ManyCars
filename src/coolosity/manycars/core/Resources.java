package coolosity.manycars.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Resources
{

	private static HashMap<String,BufferedImage> images;
	private static HashMap<String,BufferedImage> alphabet;
	
	private static String[] res = {"boat","gas","mine","alphabet"};
	
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
	
	public static void drawString(BufferedImage img, String str, int size, Color color, int x, int y)
	{
		Graphics g = img.getGraphics();
		g.setFont(new Font("Arial",Font.PLAIN,size));
		g.setColor(color);
		int curx = x;
		for(int i=0;i<str.length();i++)
		{
			String c = str.charAt(i)+"";
			if(alphabet.containsKey(c))
			{
				BufferedImage td = alphabet.get(c);
				int width = (int)(td.getWidth()*1.0/td.getHeight()*size);
				g.drawImage(td, curx, y, width, size, null);
			}
			else
			{
				
			}
		}
	}
}
