package coolosity.manycars.online;

import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class OnlineLeaderboard
{

	public static boolean addScore(String username, int score) {
		try {
		    URL url = new URL("http://halloweenarcade.webuda.com/upload.php");
		    URLConnection con = url.openConnection();
		    con.setDoOutput(true);
		    PrintStream ps = new PrintStream(con.getOutputStream());
		    ps.print("username="+username);
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
