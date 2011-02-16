package fr.esiag.mezzodijava.mezzo.costime;

/**
 * Generated from IDL exception "AlreadyRegisteredException".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:48:13
 */

public final class AlreadyRegisteredException
	extends org.omg.CORBA.UserException
{
	public AlreadyRegisteredException()
	{
		super(fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredExceptionHelper.id());
	}

	public java.lang.String message = "";
	public AlreadyRegisteredException(java.lang.String _reason,java.lang.String message)
	{
		super(_reason);
		this.message = message;
	}
	public AlreadyRegisteredException(java.lang.String message)
	{
		super(fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredExceptionHelper.id());
		this.message = message;
	}
}
