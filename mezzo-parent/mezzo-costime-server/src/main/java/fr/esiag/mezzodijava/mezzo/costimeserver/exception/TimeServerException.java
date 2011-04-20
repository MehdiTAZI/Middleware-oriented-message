/**
 * 
 */
package fr.esiag.mezzodijava.mezzo.costimeserver.exception;

/**
 * Exception TimeServerException.
 * 
 * Technical exception trhwon by time server service when error.
 * @author Mezzo-Team
 */
@SuppressWarnings("serial")
public class TimeServerException extends RuntimeException {

    /**
     * Default Constructor.
     */
    public TimeServerException() {
    }

    /**
     * Contructor with text message.
     * @param message text explanation
     */
    public TimeServerException(String message) {
	super(message);
    }

    /**
     * Constructor with cause throwable.
     * 
     * @param cause
     */
    public TimeServerException(Throwable cause) {
	super(cause);
    }

    /**
     * Constructor with text message and cause throwable.
     * @param message
     * @param cause
     */
    public TimeServerException(String message, Throwable cause) {
	super(message, cause);
    }

}
