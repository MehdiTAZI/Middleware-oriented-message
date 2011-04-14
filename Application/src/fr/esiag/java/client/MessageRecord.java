package fr.esiag.java.client;

import com.smartgwt.client.widgets.grid.ListGridRecord;
import fr.esiag.java.shared.Message;;


public class MessageRecord extends ListGridRecord{
	public MessageRecord(Message event) {
		super();
		setTime(event.time);
		setCode(event.code);
		setType(event.type);
		setData(event.data);
	}
	public String getTime() {
		return getAttributeAsString("time");
	}
	public void setTime(String time) {
		setAttribute("time",time);
	}
	public String getCode() {
		return getAttributeAsString("code");
	}
	public void setCode(String code) {
		setAttribute("code",code);
	}
	public String getType() {
		return getAttributeAsString("type");
	}
	public void setType(String type) {
		setAttribute("type",type);
	}
	public String getData() {
		return getAttributeAsString("data");
	}
	public void setData(String data) {
		setAttribute("data",data);
	}
	
}
