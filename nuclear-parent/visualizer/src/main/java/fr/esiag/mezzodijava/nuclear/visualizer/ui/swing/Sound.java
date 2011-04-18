package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import sun.rmi.server.InactiveGroupException;

public class Sound {

	private AudioFormat format;
	private byte[] samples;
	private static InputStream stream;

	public Sound(String filename){
		analyseFile(filename);
		//play();
		Thread thread=new Thread(new ThreadSound());
		thread.start();

	}

	public byte[] getSamples(){
		return samples;
	}

	public byte[] getSamples(AudioInputStream stream){
		int length = (int)(stream.getFrameLength() * format.getFrameSize());
		byte[] samples = new byte[length];
		DataInputStream in = new DataInputStream(stream);
		try{
			in.readFully(samples);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return samples;
	}


	public void lire(InputStream source){
		// 100 ms buffer for real time change to the sound stream
		int bufferSize = format.getFrameSize() * Math.round(format.getSampleRate() / 10);
		byte[] buffer = new byte[bufferSize];
		SourceDataLine line;
		try{
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			line = (SourceDataLine)AudioSystem.getLine(info);
			line.open(format, bufferSize);
		}
		catch (LineUnavailableException e){
			e.printStackTrace();
			return;
		}
		line.start();
		try{
			int numBytesRead = 0;
			while (numBytesRead != -1){
				numBytesRead = source.read(buffer, 0, buffer.length);
				if (numBytesRead != -1)
					line.write(buffer, 0, numBytesRead);
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		line.drain();
		line.close();
	}

	public void analyseFile(String file){
		try{
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(file));
			format = stream.getFormat();
			samples = getSamples(stream);
		}
		catch (UnsupportedAudioFileException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	public void analyseFile(URI file){
		try{
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(file));
			format = stream.getFormat();
			samples = getSamples(stream);
		}
		catch (UnsupportedAudioFileException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	
	public void play() {
		
			//Sound player = new Sound("alerte.wav");
			stream = new ByteArrayInputStream(getSamples());
			lire(stream);
		
	}
	
	class ThreadSound implements Runnable{

		@Override
		public void run() {
			play();
			
		}
		
	}

}
