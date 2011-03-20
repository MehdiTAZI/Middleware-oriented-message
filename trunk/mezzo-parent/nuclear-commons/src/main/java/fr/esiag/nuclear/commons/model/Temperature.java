package fr.esiag.nuclear.commons.model;

import java.io.Serializable;

public class Temperature implements Serializable{

	private String unite;
	private long value;
	public Temperature() {
		// TODO Auto-generated constructor stub
		this.value=0;
		this.unite="Pascal";
	}
	
	
	public Temperature(long value, String unite) {
		// TODO Auto-generated constructor stub
		this.value=value;
		this.unite=unite;
		
		
	}


	public String getUnite() {
		return unite;
	}


	public void setUnite(String unite) {
		this.unite = unite;
	}


	public long getValue() {
		return value;
	}


	public void setValue(long value) {
		this.value = value;
	}


	@Override
	public String toString() {
		return "Temperature /"  + value+  "/" + unite;
	}
	
	

}
