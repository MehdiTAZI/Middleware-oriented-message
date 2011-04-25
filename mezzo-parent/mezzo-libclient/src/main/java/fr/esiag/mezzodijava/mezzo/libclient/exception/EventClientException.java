package fr.esiag.mezzodijava.mezzo.libclient.exception;

/**
 * EventClientException are thrown by the Client when a local operation goes
 * wrong (ressource init error,...)
 * UC n°: US14,US15 Exception.
 * @author Franck
 * 
 */

public class EventClientException extends RuntimeException {

	/**
	  * Build a Client Exception
	 * 
	 * @param message
	 *            Information message of the exception
	 * @param exception
	 *            Cause exception
	 */
	public EventClientException(String message, Throwable exception) {
		super(message, exception);
	}

}
