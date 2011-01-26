/**
 *
 */
package fr.esiag.mezzodijava.mezzo.coseventserver.exceptions;

/**
 * Class EventServerException.
 * 
 * Technical Exception oof Mezzo COS Event Server.
 * 
 * Main class for the Mezzo di Java's COS Event Server
 * 
 * @author Mezzo Di Java
 * 
 */
public class EventServerException extends Exception {

    /**
     * Constructor.
     * 
     */
    public EventServerException() {
	super();
    }

    /**
     * Constructor.
     * 
     * @param message
     *            More info.
     */
    public EventServerException(String message) {
	super(message);
    }

    /**
     * Constructor.
     * 
     * @param message
     *            More info.
     * @param cause
     *            the throwable cause.
     */
    public EventServerException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * Constructor.
     * 
     * @param cause
     *            the throwable cause.
     */
    public EventServerException(Throwable cause) {
	super(cause);
    }

}
