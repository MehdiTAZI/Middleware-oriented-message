package fr.esiag.mezzodijava.mezzo.manager.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.esiag.mezzodijava.mezzo.manager.client.ShellExecuter;

public class ShellExecuterImpl extends RemoteServiceServlet implements ShellExecuter {

	public String execute(String[] command)
	{
		Runtime run = Runtime.getRuntime();
		Process proc = null;
		String line = "";
		try {
			proc = run.exec(command);
			// error mesage;
			//StreamCatcher errorCatcher = new 	StreamCatcher(proc.getErrorStream());            
            
            // output mesage
			StreamCatcher outputCatcher = new 	StreamCatcher(proc.getInputStream());
                
           
			//errorCatcher.read();
           line= outputCatcher.read();
            
			int exitValue = proc.waitFor();
			System.out.println("Exit Code : "+exitValue);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return line;
	}
}

