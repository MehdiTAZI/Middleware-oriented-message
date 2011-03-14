package fr.esiag.mezzodijava.mezzo.manager.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.esiag.mezzodijava.mezzo.manager.client.ShellExecuter;

public class ShellExecuterImpl extends RemoteServiceServlet implements ShellExecuter {

	public String execute(String command)
	{
		Runtime run = Runtime.getRuntime();
		Process pr = null;
		String line = "";
		try {
			pr = run.exec(command);
			pr.waitFor();
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			
			while ((line=buf.readLine())!=null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return line;
	}
}

