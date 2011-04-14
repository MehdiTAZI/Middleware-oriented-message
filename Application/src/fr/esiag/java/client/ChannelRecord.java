package fr.esiag.java.client;


import com.smartgwt.client.widgets.grid.ListGridRecord;
import fr.esiag.java.shared.ChannelInfosCollector;

public class ChannelRecord extends ListGridRecord{


	public String getTopic() {
		return getAttributeAsString("topic");
	}
	public void setTopic(String topic) {
		  setAttribute("topic",topic);
	}
	public String getCapacity() {
		return getAttributeAsString("capacity");
	}
	public void setCapacity(String capacity) {
		setAttribute("capacity",capacity);
	}
	public ChannelRecord(){
		
	}
	public ChannelRecord(ChannelInfosCollector channelInfosCollector) {
		super();
		setTopic(channelInfosCollector.topic);
		setCapacity(""+channelInfosCollector.capacity);
		setNbConsumerConnected(""+channelInfosCollector.consumersConnected);
		setNbSupplierConnected(""+channelInfosCollector.suppliersConnected);
		setNbConsumersSubscribed(""+channelInfosCollector.consumersSubscribed);
		setNbQeueMessage(""+channelInfosCollector.nbQueueEvents);
	}
	private void setNbQeueMessage(String nbQeueMessage) {
		setAttribute("nbQeueMessage",nbQeueMessage);
	}
	public String getNbQeueMessage() {
		return getAttributeAsString("nbQeueMessage");
	}
	private void setNbConsumersSubscribed(String nbConsumersSubscribed) {
		setAttribute("nbConsumersSubscribed",nbConsumersSubscribed);
	}
	public String getNbConsumersSubscribed() {
		return getAttributeAsString("nbConsumersSubscribed");
	}
	public String getNbConsumerConnected() {
		return getAttributeAsString("nbConsumerConnected");
	}
	public void setNbConsumerConnected(String nbConsumerConnected) {
		setAttribute("nbConsumerConnected", nbConsumerConnected);
	}
	public String getNbSupplierConnected() {
		return getAttributeAsString("nbSupplierConnected");
	}
	public void setNbSupplierConnected(String nbSupplierConnected) {
		setAttribute("nbSupplierConnected", nbSupplierConnected);
	}
}
