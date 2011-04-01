package fr.esiag.mezzodijava.mezzo.manager.client;




import java.util.Date;

public class ChannelData {

	public ChannelData() {
		// TODO Auto-generated constructor stub
	}
	private static ChannelRecord[] records;  
	   
    public static ChannelRecord[] getRecords() {  
        if (records == null) {  
            records = getNewRecords();  
        }  
        return records;  
    }  
  
    public static ChannelRecord[] getNewRecords() {  
        return new ChannelRecord[]{  
        		new ChannelRecord(new ChannelInfosCollector("Mezzo1", 1, 1,1,1,1,new Message[]{new Message(new Date().toGMTString(), "1", "2","kiki")})),
        		new ChannelRecord(new ChannelInfosCollector("Mezzo2", 2, 3,2,2,2,new Message[]{new Message(new Date().toGMTString(), "1", "2","kiki")})),	
        		}; 
    }  
}
