package coolosity.manycars.display;

import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameDisplay
{

	private JFrame frame;
	private JLabel label;
	
	public GameDisplay(String title, KeyListener keyListener, int width)
	{
		frame = new JFrame(title);
		frame.addKeyListener(keyListener);
		frame.setSize(width, 600);
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
	
	public void destroy()
	{
		frame.dispose();
	}
	
	public JFrame getFrame()
	{
		return frame;
	}
}
