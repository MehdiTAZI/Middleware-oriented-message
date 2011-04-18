/**
 * 
 */
package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

/**
 * Exception PersistenceException.
 * 
 * Technical exception trhwon by persistence service when error.
 * 
 * 
 * UC n°: US41 (+US children) CI 06: Persistance des messages 
 * 
 * @author Mezzo-Team
 */
@SuppressWarnings("serial")
public class PersistenceException extends RuntimeException {

    /**
     * Default Constructor.
     */
    public PersistenceException() {
    }

    /**
     * Contructor with text message.
     * @param message text explanation
     */
    public PersistenceException(String message) {
	super(message);
    }

    /**
     * Constructor with cause throwable.
     * 
     * @param cause
     */
    public PersistenceException(Throwable cause) {
	super(cause);
    }

    /**
     * Constructor with text message and cause throwable.
     * @param message
     * @param cause
     */
    public PersistenceException(String message, Throwable cause) {
	super(message, cause);
    }

}
