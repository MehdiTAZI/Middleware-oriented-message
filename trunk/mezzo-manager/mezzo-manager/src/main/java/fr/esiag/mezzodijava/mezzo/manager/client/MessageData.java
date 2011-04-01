package fr.esiag.mezzodijava.mezzo.manager.client;



import java.util.Date;

public class MessageData {
	private static MessageRecord[] records;  
	   
    public static MessageRecord[] getRecords() {  
        if (records == null) {  
            records = getNewRecords();  
        }  
        return records;  
    }  
  
    public static MessageRecord[] getNewRecords() {  
        return new MessageRecord[]{  
        		new MessageRecord(new Message(new Date().toString(), "1", "Temperature", "32 degre")),
        		new MessageRecord(new Message(new Date().toString(), "2", "RadioActivite", "2")),
                new MessageRecord(new Message(new Date().toString(), "3", "Pression", "3")),  
                new MessageRecord(new Message(new Date().toString(), "1", "Temperature", "38 degre")),
                new MessageRecord(new Message(new Date().toString(), "2", "Pression", "5")),
                new MessageRecord(new Message(new Date().toString(), "3", "RadioActivite", "6")),
                new MessageRecord(new Message(new Date().toString(), "3", "Temperature", "40 degre")),
                new MessageRecord(new Message(new Date().toString(), "2", "Pression", "8")),
                new MessageRecord(new Message(new Date().toString(), "1", "Temperature", "60 degre")),
                new MessageRecord(new Message(new Date().toString(), "3", "RadioActivite", "10")),
                new MessageRecord(new Message(new Date().toString(), "1", "Temperature", "70 degre")),
                new MessageRecord(new Message(new Date().toString(), "2", "Pression", "12")),
                new MessageRecord(new Message(new Date().toString(), "1", "RadioActivite", "13")),
                new MessageRecord(new Message(new Date().toString(), "2", "Temperature", "100 degre")),
        };  
    }  
}
