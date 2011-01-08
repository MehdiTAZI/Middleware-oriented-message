package fr.esiag.mezzodijava.mezzo.libclient.exception;

/**
 * EventClientException are thrown by the Client when a local operation goes
 * wrong (ressource init error,...)
 * 
 * @author Franck
 * 
 */
//MTA : depreced utilisé plutot EventClientException Definie dans l'IDL
public class EventClientExceptionCRAP extends Exception {

	/**
	 * Build a Client Exception
	 * 
	 * @param message
	 *            Information message of the exception
	 * @param exception
	 *            Cause exception
	 */
	public EventClientExceptionCRAP(String message, Throwable exception) {
		super(message, exception);
	}

}
