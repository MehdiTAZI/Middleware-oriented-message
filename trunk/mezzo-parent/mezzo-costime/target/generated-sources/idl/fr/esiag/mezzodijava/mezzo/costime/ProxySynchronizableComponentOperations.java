package fr.esiag.mezzodijava.mezzo.costime;


/**
 * Generated from IDL interface "ProxySynchronizableComponent".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:48:13
 */

public interface ProxySynchronizableComponentOperations
	extends fr.esiag.mezzodijava.mezzo.costime.ITimeableOperations
{
	/* constants */
	/* operations  */
	void subscribe(fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent csc) throws fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredException,fr.esiag.mezzodijava.mezzo.costime.UnreachableException;
	void unsubscribe() throws fr.esiag.mezzodijava.mezzo.costime.UnreachableException,fr.esiag.mezzodijava.mezzo.costime.NotRegisteredException;
}
