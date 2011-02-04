package fr.esiag.mezzodijava.nuclear.sensor;

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
		list= new Vector<String>();
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
	
	public Vector<String> getData(String file) {
		loadFile(file);
		return list;
	}

	
}
