package fr.esiag.mezzodijava.mezzo.cosevent;

/**
 * Generated from IDL exception "UnreachableException".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:17:04
 */

public final class UnreachableException
	extends org.omg.CORBA.UserException
{
	public UnreachableException()
	{
		super(fr.esiag.mezzodijava.mezzo.cosevent.UnreachableExceptionHelper.id());
	}

	public java.lang.String message = "";
	public UnreachableException(java.lang.String _reason,java.lang.String message)
	{
		super(_reason);
		this.message = message;
	}
	public UnreachableException(java.lang.String message)
	{
		super(fr.esiag.mezzodijava.mezzo.cosevent.UnreachableExceptionHelper.id());
		this.message = message;
	}
}
