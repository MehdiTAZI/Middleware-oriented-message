package fr.esiag.mezzodijava.mezzo.cosevent;

/**
 * Generated from IDL interface "ProxySynchronizableComponent".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:17:04
 */

public final class ProxySynchronizableComponentHolder	implements org.omg.CORBA.portable.Streamable{
	 public ProxySynchronizableComponent value;
	public ProxySynchronizableComponentHolder()
	{
	}
	public ProxySynchronizableComponentHolder (final ProxySynchronizableComponent initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return ProxySynchronizableComponentHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ProxySynchronizableComponentHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		ProxySynchronizableComponentHelper.write (_out,value);
	}
}
