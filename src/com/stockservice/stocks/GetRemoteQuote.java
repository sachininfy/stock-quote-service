package com.stockservice.stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class GetRemoteQuote
{
	public final static Hashtable<String, String> GetQuote(String symbol)
	{
		String[] values =
		{
				"Company", "Quote", "Date", "Time", "Change", "Open", "High",
				"Low", "Volume"
		};
		int x = 0;
		Hashtable<String, String> info = new Hashtable<String, String>();

		if (symbol.isEmpty())
			symbol = "IBM";

		URL quoteURL;
		try
		{
			quoteURL = new URL(
					"http://download.finance.yahoo.com/d/quotes.csv?s="
							+ symbol + "&f=sl1d1t1c1ohgv&e=.csv");
			URLConnection quote = quoteURL.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					quote.getInputStream()));
			String theQuote = in.readLine();
			StringTokenizer tokens = new StringTokenizer(theQuote, ",\"\'\n\r");
			while (tokens.hasMoreTokens())
			{
				String item = tokens.nextToken();
				info.put((String) values[x++], (String) item);
			}
		}
		catch (MalformedURLException e)
		{
			info.put("Exception", e.getMessage());
			e.printStackTrace();
		}
		catch (IOException e)
		{
			info.put("Exception", e.getMessage());
			e.printStackTrace();
		}

		if (symbol.equals("SHKS"))
			info.put("Quote", "To be, or not to be");
		return info;
	}
}
