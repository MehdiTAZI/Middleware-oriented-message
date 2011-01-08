package fr.esiag.mezzodijava.mezzo.libclient.exception;

public class TopicNotFoundExceptionCRAP extends Exception {
	/**
	 * When a topic is not kfound
	 * 
	 * @param topic
	 *            Topic not found
	 * @param exception
	 *            Cause exception
	 */
	public TopicNotFoundExceptionCRAP(String topic, Throwable exception) {
		super("Channel not found with this topic : " + topic, exception);
	}

}
