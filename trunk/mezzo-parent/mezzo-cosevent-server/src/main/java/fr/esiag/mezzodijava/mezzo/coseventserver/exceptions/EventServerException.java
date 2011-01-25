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

    public EventServerException() {
	super();
    }

    public EventServerException(String message) {
	super(message);
    }

    public EventServerException(String message, Throwable cause) {
	super(message, cause);
    }

    public EventServerException(Throwable cause) {
	super(cause);
    }

}
