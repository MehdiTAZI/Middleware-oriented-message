package fr.esiag.mezzodijava.mezzo.manager.shared;

import java.io.Serializable;



public  class Message implements Serializable
{
public Message(){}
public java.lang.String time = "";
public java.lang.String code = "";
public java.lang.String type = "";
public java.lang.String data = "";
public Message(java.lang.String time, java.lang.String code, java.lang.String type, java.lang.String data)
{
	this.time = time;
	this.code = code;
	this.type = type;
	this.data = data;
}
}

