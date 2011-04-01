package fr.esiag.mezzodijava.mezzo.manager.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mezzo.esiag.mezzodijava.mezzo.manager.client.old.ShellExecuter;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class ShellExecuterImpl extends RemoteServiceServlet implements ShellExecuter {

	public String execute(String[] command)
	{
		Runtime run = Runtime.getRuntime();
		Process proc = null;
		String line = "";
		//execBatch.go(command);
		try {
			proc = run.exec(command);
			int len = command.length;
			
		/*	command[len-1]=command[len-1]+" > c:/outResult.req";
			for(String s : command)
			System.out.println(s);*/
			// error mesage
			StreamCatcher errorCatcher = new 	StreamCatcher(proc.getErrorStream());            
            // output mesage
			StreamCatcher outputCatcher = new 	StreamCatcher(proc.getInputStream());
                
           
			//errorCatcher.read();
           line= outputCatcher.getData();
			
			System.out.println("waiting");
			int exitValue = proc.waitFor();
			System.out.println("Exit Code : "+exitValue);
//			InputReader reader = new FileCatcher("c:/outResult.req");
//			line = reader.getData();
//			System.out.println("end line = "+line);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return line;
	}
}

