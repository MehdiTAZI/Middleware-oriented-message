package fr.esiag.mezzodijava.mezzo.manager.server.old;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileCatcher implements InputReader{
	private String file;

	public FileCatcher(String file)
	{
			this.file=file;
	}
	

	public String getData() {
		String inputLine;
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br;
			File f = new File(file);
			FileReader fr = new FileReader(f);
			br=new BufferedReader(fr);
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			br.close();
			fr.close();
		} catch (IOException e) {
		}
		return sb.toString();
	}
}
