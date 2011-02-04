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
		int code = random.nextInt(1000);
		
		return code+"/"+generateCode()+"/"+generateData();
	
	}
	public String alertMessage(){
		int code = 900+random.nextInt(100);
		
		return code+"/"+generateCode()+"/"+generateData();
	}
	public String randomMessage(){
		if(random.nextBoolean())
			return alertMessage();
		else
			return normalMessage();
	}
	private int generateData(){
		int data = random.nextInt(100);
		if (random.nextBoolean())
			return 0-data;
		else
			return data;
	}
	private String generateCode(){
		int typeC = random.nextInt(3);
		return type[typeC];
	}
	public static void main(String[] args) {
		for(int i =0;i<15;i++){
		GenerateMessage gm = new GenerateMessage();
		System.out.println(gm.normalMessage());
		}
	}

}
