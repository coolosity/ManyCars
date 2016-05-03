package coolosity.manycars.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import coolosity.manycars.core.OnlineHandler;

public class SQLTest
{

	public static void main(String[] args) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Input:\nconnectionURL userID score");
		String[] data = reader.readLine().split(" ");
		if(data.length!=3)
		{
			System.err.println("Invalid input, pls leave");
			System.exit(0);
		}
		try
		{
			int userID = Integer.parseInt(data[1]);
			int score = Integer.parseInt(data[2]);
			OnlineHandler.setConnectionURL(data[0]);
			boolean success = OnlineHandler.addScore(userID, score);
			System.out.println(success?"Success!":"Failure :(");
		} catch(Exception e) {
			System.err.println("Invalid integer(s)");
		}
	}
	
}
