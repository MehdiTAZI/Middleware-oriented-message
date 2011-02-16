package fr.esiag.mezzodijava.mezzo.cosevent;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "ProxySynchronizableComponent".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:17:04
 */

public class ProxySynchronizableComponentPOATie
	extends ProxySynchronizableComponentPOA
{
	private ProxySynchronizableComponentOperations _delegate;

	private POA _poa;
	public ProxySynchronizableComponentPOATie(ProxySynchronizableComponentOperations delegate)
	{
		_delegate = delegate;
	}
	public ProxySynchronizableComponentPOATie(ProxySynchronizableComponentOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public fr.esiag.mezzodijava.mezzo.cosevent.ProxySynchronizableComponent _this()
	{
		return fr.esiag.mezzodijava.mezzo.cosevent.ProxySynchronizableComponentHelper.narrow(_this_object());
	}
	public fr.esiag.mezzodijava.mezzo.cosevent.ProxySynchronizableComponent _this(org.omg.CORBA.ORB orb)
	{
		return fr.esiag.mezzodijava.mezzo.cosevent.ProxySynchronizableComponentHelper.narrow(_this_object(orb));
	}
	public ProxySynchronizableComponentOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(ProxySynchronizableComponentOperations delegate)
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
	public long synchroValue()
	{
		return _delegate.synchroValue();
	}

	public void synchroValue(long a)
	{
		_delegate.synchroValue(a);
	}

	public void subscribe(fr.esiag.mezzodijava.mezzo.cosevent.CallbackSynchronizableComponent csc) throws fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException,fr.esiag.mezzodijava.mezzo.cosevent.UnreachableException
	{
_delegate.subscribe(csc);
	}

	public void unsubscribe() throws fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException,fr.esiag.mezzodijava.mezzo.cosevent.UnreachableException
	{
_delegate.unsubscribe();
	}

}
