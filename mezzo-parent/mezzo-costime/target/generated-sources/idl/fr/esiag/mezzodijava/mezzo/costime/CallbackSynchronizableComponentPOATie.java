package fr.esiag.mezzodijava.mezzo.costime;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "CallbackSynchronizableComponent".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:48:13
 */

public class CallbackSynchronizableComponentPOATie
	extends CallbackSynchronizableComponentPOA
{
	private CallbackSynchronizableComponentOperations _delegate;

	private POA _poa;
	public CallbackSynchronizableComponentPOATie(CallbackSynchronizableComponentOperations delegate)
	{
		_delegate = delegate;
	}
	public CallbackSynchronizableComponentPOATie(CallbackSynchronizableComponentOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent _this()
	{
		return fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponentHelper.narrow(_this_object());
	}
	public fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent _this(org.omg.CORBA.ORB orb)
	{
		return fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponentHelper.narrow(_this_object(orb));
	}
	public CallbackSynchronizableComponentOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(CallbackSynchronizableComponentOperations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		return super._default_POA();
	}
	public void receive(long dateTime) throws fr.esiag.mezzodijava.mezzo.costime.UnreachableException
	{
_delegate.receive(dateTime);
	}

}
