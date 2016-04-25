package coolosity.manycars.display;

import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import coolosity.manycars.main.ManyCarsMain;

public class MainDisplay
{

	private JFrame frame;
	private JLabel label;
	
	public MainDisplay(String title, ManyCarsMain inputListener)
	{
		frame = new JFrame(title);
		frame.addKeyListener(inputListener);
		frame.addMouseListener(inputListener);
		frame.addMouseMotionListener(inputListener);
		frame.setSize(600, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		label = new JLabel();
		frame.add(label);
		
		frame.setVisible(true);
	}
	
	public BufferedImage getCanvas()
	{
		return new BufferedImage(label.getWidth(),label.getHeight(),BufferedImage.TYPE_INT_ARGB);
	}
	
	public void draw(BufferedImage img)
	{
		label.setIcon(new ImageIcon(img));
	}
	
	public Point getOffset()
	{
		int xoff = (frame.getWidth()-label.getWidth())/2;
		int yoff = frame.getHeight()-label.getHeight()-xoff;
		return new Point(xoff,yoff);
	}
	
	public void setVisible(boolean visible)
	{
		frame.setVisible(visible);
	}
	
	public JFrame getFrame()
	{
		return frame;
	}
}
