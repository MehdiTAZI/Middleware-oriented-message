package fr.esiag.mezzodijava.mezzo.cosevent;


/**
 * Generated from IDL interface "ProxySynchronizableComponent".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:17:04
 */

public interface ProxySynchronizableComponentOperations
	extends fr.esiag.mezzodijava.mezzo.cosevent.ITimeableOperations
{
	/* constants */
	/* operations  */
	void subscribe(fr.esiag.mezzodijava.mezzo.cosevent.CallbackSynchronizableComponent csc) throws fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException,fr.esiag.mezzodijava.mezzo.cosevent.UnreachableException;
	void unsubscribe() throws fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException,fr.esiag.mezzodijava.mezzo.cosevent.UnreachableException;
}
