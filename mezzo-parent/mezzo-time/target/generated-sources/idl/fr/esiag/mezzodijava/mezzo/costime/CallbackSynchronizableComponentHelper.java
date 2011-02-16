package fr.esiag.mezzodijava.mezzo.costime;


/**
 * Generated from IDL interface "CallbackSynchronizableComponent".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:48:13
 */

public final class CallbackSynchronizableComponentHelper
{
	public static void insert (final org.omg.CORBA.Any any, final fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent s)
	{
			any.insert_Object(s);
	}
	public static fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:costime/CallbackSynchronizableComponent:1.0", "CallbackSynchronizableComponent");
	}
	public static String id()
	{
		return "IDL:costime/CallbackSynchronizableComponent:1.0";
	}
	public static CallbackSynchronizableComponent read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(fr.esiag.mezzodijava.mezzo.costime._CallbackSynchronizableComponentStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent s)
	{
		_out.write_Object(s);
	}
	public static fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent)
		{
			return (fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent)obj;
		}
		else if (obj._is_a("IDL:costime/CallbackSynchronizableComponent:1.0"))
		{
			fr.esiag.mezzodijava.mezzo.costime._CallbackSynchronizableComponentStub stub;
			stub = new fr.esiag.mezzodijava.mezzo.costime._CallbackSynchronizableComponentStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent)
		{
			return (fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent)obj;
		}
		else
		{
			fr.esiag.mezzodijava.mezzo.costime._CallbackSynchronizableComponentStub stub;
			stub = new fr.esiag.mezzodijava.mezzo.costime._CallbackSynchronizableComponentStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
