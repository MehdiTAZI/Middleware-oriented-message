package fr.esiag.mezzodijava.mezzo.cosevent;


/**
 * Generated from IDL interface "ITimeable".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 f�vr. 2011 00:17:04
 */

public final class ITimeableHelper
{
	public static void insert (final org.omg.CORBA.Any any, final fr.esiag.mezzodijava.mezzo.cosevent.ITimeable s)
	{
			any.insert_Object(s);
	}
	public static fr.esiag.mezzodijava.mezzo.cosevent.ITimeable extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:cosevent/ITimeable:1.0", "ITimeable");
	}
	public static String id()
	{
		return "IDL:cosevent/ITimeable:1.0";
	}
	public static ITimeable read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(fr.esiag.mezzodijava.mezzo.cosevent._ITimeableStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final fr.esiag.mezzodijava.mezzo.cosevent.ITimeable s)
	{
		_out.write_Object(s);
	}
	public static fr.esiag.mezzodijava.mezzo.cosevent.ITimeable narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof fr.esiag.mezzodijava.mezzo.cosevent.ITimeable)
		{
			return (fr.esiag.mezzodijava.mezzo.cosevent.ITimeable)obj;
		}
		else if (obj._is_a("IDL:cosevent/ITimeable:1.0"))
		{
			fr.esiag.mezzodijava.mezzo.cosevent._ITimeableStub stub;
			stub = new fr.esiag.mezzodijava.mezzo.cosevent._ITimeableStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static fr.esiag.mezzodijava.mezzo.cosevent.ITimeable unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof fr.esiag.mezzodijava.mezzo.cosevent.ITimeable)
		{
			return (fr.esiag.mezzodijava.mezzo.cosevent.ITimeable)obj;
		}
		else
		{
			fr.esiag.mezzodijava.mezzo.cosevent._ITimeableStub stub;
			stub = new fr.esiag.mezzodijava.mezzo.cosevent._ITimeableStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
