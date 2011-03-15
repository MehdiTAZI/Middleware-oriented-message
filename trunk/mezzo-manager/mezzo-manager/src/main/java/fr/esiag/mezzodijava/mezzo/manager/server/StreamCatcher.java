package fr.esiag.mezzodijava.mezzo.manager.server;
import java.util.*;
import java.io.*;
class StreamCatcher
{
	private InputStream is;
	StreamCatcher(InputStream is)
	{
		this.is = is;
	}

	public String read()
	{
		StringBuffer data= new StringBuffer();
		try
		{
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line=null;
			while ( (line = br.readLine()) != null)
				data.append(line);

		} catch (IOException ioe)
		{
			ioe.printStackTrace();  
		}
		return data.toString();
	}
}