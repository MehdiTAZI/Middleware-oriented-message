package fr.esiag.mezzodijava.mezzo.manager.client.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellExecuter {
	private String cmd;
	public ShellExecuter(String command)
	{
		this.cmd=command;
	}
	public String execute()
	{
		Runtime run = Runtime.getRuntime();
		Process pr = null;
		String line = "";
		try {
			pr = run.exec(cmd);
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

