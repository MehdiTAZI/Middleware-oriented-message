package fr.esiag.mezzodijava.mezzo.libclient.exception;

/**
 * 
 * @author MEZZODIJAVAAAAAAAAA
 * exception when a topic is not found
 *
 */
public class TopicNotFoundException extends Exception {
	/**
	 * When a topic is not kfound
	 * 
	 * @param topic
	 *            Topic not found
	 * @param exception
	 *            Cause exception
	 */
	public TopicNotFoundException(String topic, Throwable exception) {
		super("Channel not found with this topic : " + topic, exception);
	}

}
