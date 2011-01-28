package fr.esiag.mezzodijava.nuclear.sensor;

import java.util.Random;

public class GenerateMessage {
	private final String[] type= new String[3];
	private final Random random = new Random();
	public GenerateMessage(){
		type[0]= "RADIOACTIVITE";
		type[1]= "TEMPERATURE";
		type[2]= "PRESSION";
	}
	
	public String normalMessage(){
		return random.nextInt(900)+"/"+type[random.nextInt(3)]+"/"+random.nextInt();
	
	}
	public String alertMessage(){
		int code = 900+random.nextInt(100);
		return code+"/"+type[random.nextInt(3)]+"/"+random.nextInt();
	}
	public String randomMessage(){
		if(random.nextBoolean())
			return alertMessage();
		else
			return normalMessage();
	}

}
