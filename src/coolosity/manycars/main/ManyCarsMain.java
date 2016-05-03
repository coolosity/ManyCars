package coolosity.manycars.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import coolosity.manycars.core.LeaderboardHandler;
import coolosity.manycars.core.Resources;
import coolosity.manycars.core.Score;
import coolosity.manycars.core.Utils;
import coolosity.manycars.display.GameDisplay;
import coolosity.manycars.display.MainDisplay;
import coolosity.manycars.display.Menu;
import coolosity.manycars.display.MenuLocalLeaderboard;
import coolosity.manycars.display.MenuMain;
import coolosity.manycars.game.Game;

public class ManyCarsMain implements KeyListener, MouseListener, MouseMotionListener
{
	
	private static final int MAX_USERNAME_LENGTH = 10;
	
	public static void main(String[] args)
	{
		Utils.init();
		LeaderboardHandler.loadLocalScores();
		Resources.loadResources();
		new ManyCarsMain();
	}
	
	private MainDisplay mainDisplay;
	private Menu currentMenu;
	private GameDisplay gameDisplay;
	private Game game;
	private volatile boolean running, menuRunning;
	private String username;
	private boolean showUnderscore;
	private long nextUnderscoreChange;
	
	private Point mse;
	private int numCars;
	
	public ManyCarsMain()
	{
		mainDisplay = new MainDisplay("Many Cars",this);
		currentMenu = new MenuMain(this);
		mse = new Point(0,0);
		numCars = 2;
		username = "";
		
		new Thread(new Runnable(){
			public void run(){
				menuLoop();
			}
		}).start();
	}
	
	private void menuLoop()
	{
		menuRunning = true;
		mainDisplay.setVisible(true);
		while(menuRunning)
		{
			long start = System.currentTimeMillis();
			BufferedImage img = mainDisplay.getCanvas();
			currentMenu.draw(img, mse.x, mse.y);
			mainDisplay.draw(img);
			
			while(System.currentTimeMillis()-start<1000/60);
		}
	}
	
	private void gameLoop()
	{
		running = true;
		showUnderscore = false;
		long lastDraw = 0, lastTick = 0;
		while(running)
		{
			if(System.currentTimeMillis()-lastTick>=1000/60)
			{
				lastTick = System.currentTimeMillis();
				game.tick();
			}
			if(System.currentTimeMillis()-lastDraw>=0)
			{
				lastDraw = System.currentTimeMillis();
				BufferedImage img = gameDisplay.getCanvas();
				Graphics g = img.getGraphics();
				g.setColor(Color.GREEN);
				g.fillRect(0, 0, img.getWidth(), img.getHeight());
				
				if(numCars>1)
					game.draw(img);
				else
					game.draw(img.getSubimage(img.getWidth()/4, 0, img.getWidth()/2, img.getHeight()));
				
				if(game.isDead())
				{
					Font name = new Font("Arial",Font.PLAIN,32);
					FontMetrics fm = g.getFontMetrics(name);
					g.setFont(name);

					int they = fm.getHeight()+10;
					
					g.setColor(new Color(153, 77, 0));
					g.fillRect(0, they-fm.getHeight()+7, img.getWidth(), fm.getHeight());
					
					int thex = (img.getWidth()-fm.stringWidth(username))/2;
					
					g.setColor(Color.YELLOW);
					g.drawString(username, thex, they);
					thex += fm.stringWidth(username);
					
					if(System.currentTimeMillis()>=nextUnderscoreChange)
					{
						nextUnderscoreChange = System.currentTimeMillis()+1000/3;
						showUnderscore = !showUnderscore;
					}
					
					if(showUnderscore)
					{
						g.drawString("|", thex, they);
					}
					
					int numlines = 2;
					int ybor = 25;
					int height = fm.getHeight()*numlines+ybor*2;
					g.setColor(Color.CYAN);
					g.fillRect(0, (img.getHeight()-height)/2, img.getWidth(), height);
					int cury = img.getHeight()/2-height/2+fm.getHeight()+ybor-5;
					String txt = "SPACE to restart";
					g.setColor(Color.BLACK);
					g.drawString(txt, (img.getWidth()-fm.stringWidth(txt))/2, cury);
					cury += fm.getHeight();
					txt = "ESC to exit";
					g.drawString(txt, (img.getWidth()-fm.stringWidth(txt))/2, cury);
					cury += fm.getHeight();
				}
				
				gameDisplay.draw(img);
			}
		}
		
		JFrame mf = gameDisplay.getFrame();
		JFrame gf = mainDisplay.getFrame();
		gf.setLocation(mf.getX()+mf.getWidth()/2-gf.getWidth()/2, mf.getY()+mf.getHeight()/2-gf.getHeight()/2);
		
		gameDisplay.destroy();
		menuLoop();
	}
	
	public void newGame()
	{
		menuRunning = false;
		gameDisplay = new GameDisplay("Many Cars",this, 150*Math.max(numCars,2));
		
		JFrame gf = gameDisplay.getFrame();
		JFrame mf = mainDisplay.getFrame();
		gf.setLocation(mf.getX()+mf.getWidth()/2-gf.getWidth()/2, mf.getY()+mf.getHeight()/2-gf.getHeight()/2);

		mainDisplay.setVisible(false);
		game = new Game(numCars);
		new Thread(new Runnable(){
			public void run(){
				gameLoop();
			}
		}).start();
	}
	
	public void setNumCars(int numCars)
	{
		this.numCars = numCars;
	}
	
	public int getNumCars()
	{
		return numCars;
	}
	
	public void openLocalLeaderboard()
	{
		currentMenu = new MenuLocalLeaderboard(this,numCars);
	}
	
	public void openMainMenu()
	{
		currentMenu = new MenuMain(this);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-_!? ";
		if(game!=null)
		{
			String order = "asdfghjkl";
			String key = e.getKeyChar()+"";
			game.moveColumn(order.indexOf(key));
			if(game.isDead())
			{
				int code = e.getKeyCode();
				System.out.println(code);
				if(code==10)//ENTER
				{
					saveScore();
					game = new Game(numCars);
				}
				else if(code==27)//ESC
				{
					saveScore();
					running = false;
				}
				else if(code==8)//BKSP
				{
					if(username.length()>0)username = username.substring(0, username.length()-1);
				}
				else
				{
					String c = e.getKeyChar()+"";
					if(alpha.contains(c) && username.length()<MAX_USERNAME_LENGTH)
					{
						username += c;
					}
				}
			}
		}
	}
	
	private void saveScore()
	{
		LeaderboardHandler.saveScore(new Score(username,game.getScore(),System.currentTimeMillis(),numCars));
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		if(currentMenu != null)currentMenu.mousePressed();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		Point off = mainDisplay.getOffset();
		mse.x = e.getX()-off.x;
		mse.y = e.getY()-off.y;
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		Point off = mainDisplay.getOffset();
		mse.x = e.getX()-off.x;
		mse.y = e.getY()-off.y;
	}
	
	public String getInput(String msg)
	{
		return JOptionPane.showInputDialog(mainDisplay.getFrame(), msg);
	}
	
	public int getInputInt(String msg)
	{
		String in = "";
		boolean valid = false;
		while(!valid)
		{
			in = JOptionPane.showInputDialog(mainDisplay.getFrame(), msg);
			try
			{
				Integer.parseInt(in);
				valid = true;
			} catch(Exception e) {
				showMessage("Invalid integer, try again");
			}
		}
		return Integer.parseInt(in);
	}
	
	public void showMessage(String msg)
	{
		JOptionPane.showMessageDialog(mainDisplay.getFrame(), msg);
	}
	
	@Override
	public void keyReleased(KeyEvent e){}

	@Override
	public void keyTyped(KeyEvent e){}

	@Override
	public void mouseClicked(MouseEvent e){}

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e){}
}
