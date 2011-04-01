package fr.esiag.mezzodijava.mezzo.manager.client;



public  class ChannelInfosCollector

{
public ChannelInfosCollector(){}
public java.lang.String topic = "";
public int capacity;
public int nbQueueEvents;
public int consumersConnected;
public int consumersSubscribed;
public int suppliersConnected;
public Message[] messages;
public ChannelInfosCollector(String topic, int capacity, int nbQueueEvents, int consumersConnected, int consumersSubscribed, int suppliersConnected, Message[] messages)
{
	this.topic = topic;
	this.capacity = capacity;
	this.nbQueueEvents = nbQueueEvents;
	this.consumersConnected = consumersConnected;
	this.consumersSubscribed = consumersSubscribed;
	this.suppliersConnected = suppliersConnected;
	this.messages = messages;
}
}
