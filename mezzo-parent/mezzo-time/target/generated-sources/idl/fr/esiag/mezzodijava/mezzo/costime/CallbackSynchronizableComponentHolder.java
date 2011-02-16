package fr.esiag.mezzodijava.mezzo.costime;

/**
 * Generated from IDL interface "CallbackSynchronizableComponent".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:48:13
 */

public final class CallbackSynchronizableComponentHolder	implements org.omg.CORBA.portable.Streamable{
	 public CallbackSynchronizableComponent value;
	public CallbackSynchronizableComponentHolder()
	{
	}
	public CallbackSynchronizableComponentHolder (final CallbackSynchronizableComponent initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CallbackSynchronizableComponentHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CallbackSynchronizableComponentHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CallbackSynchronizableComponentHelper.write (_out,value);
	}
}
