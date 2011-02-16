package fr.esiag.mezzodijava.mezzo.costime;

/**
 * Generated from IDL interface "ITimeable".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:48:13
 */

public final class ITimeableHolder	implements org.omg.CORBA.portable.Streamable{
	 public ITimeable value;
	public ITimeableHolder()
	{
	}
	public ITimeableHolder (final ITimeable initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return ITimeableHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ITimeableHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		ITimeableHelper.write (_out,value);
	}
}
