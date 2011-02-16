package fr.esiag.mezzodijava.mezzo.cosevent;

/**
 * Generated from IDL exception "NotRegisteredException".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:17:04
 */

public final class NotRegisteredException
	extends org.omg.CORBA.UserException
{
	public NotRegisteredException()
	{
		super(fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredExceptionHelper.id());
	}

	public java.lang.String message = "";
	public NotRegisteredException(java.lang.String _reason,java.lang.String message)
	{
		super(_reason);
		this.message = message;
	}
	public NotRegisteredException(java.lang.String message)
	{
		super(fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredExceptionHelper.id());
		this.message = message;
	}
}
