package coolosity.manycars.core;

import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class OnlineHandler
{

	private static String connURL;
	
	public static void setConnectionURL(String url)
	{
		connURL = url;
	}
	
	public static boolean addScore(int uid, int score)
	{
		try {
		    URL url = new URL(connURL);
		    URLConnection con = url.openConnection();
		    con.setDoOutput(true);
		    PrintStream ps = new PrintStream(con.getOutputStream());
		    ps.print("id="+uid);
		    ps.print("&score="+score);
		    con.getInputStream();
		    ps.close();
		    return true;
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	        return false;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
