package fr.esiag.mezzodijava.mezzoproto.capteur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

public class TextFile implements Implementor{

	private Vector<String> list;

	public TextFile() {
		list=new Vector<String>();
	}
	
	public void loadFile(String file){
		try {
			
		FileReader fr=new FileReader(file);
		BufferedReader in=new BufferedReader(fr);
		String data="";				
		while((data=in.readLine())!=null){
				
			list.add(data);
		}
		in.close();
		} catch (Exception e) {
			System.out.println("Le fichier "+file+" est introuvable!!!");
		}
		
	}
	
	public Vector<String> getData() {
		loadFile("data.txt");
		return list;
	}

	
}
