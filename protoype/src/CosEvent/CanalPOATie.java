package CosEvent;


/**
* CosEvent/CanalPOATie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from prototype.idl
* samedi 27 novembre 2010 15 h 07 CET
*/

public class CanalPOATie extends CanalPOA
{

  // Constructors

  public CanalPOATie ( CosEvent.CanalOperations delegate ) {
      this._impl = delegate;
  }
  public CanalPOATie ( CosEvent.CanalOperations delegate , org.omg.PortableServer.POA poa ) {
      this._impl = delegate;
      this._poa      = poa;
  }
  public CosEvent.CanalOperations _delegate() {
      return this._impl;
  }
  public void _delegate (CosEvent.CanalOperations delegate ) {
      this._impl = delegate;
  }
  public org.omg.PortableServer.POA _default_POA() {
      if(_poa != null) {
          return _poa;
      }
      else {
          return super._default_POA();
      }
  }
  public void add_consumer (CosEvent.ProxyPushConsumer ppc)
  {
    _impl.add_consumer(ppc);
  } // add_consumer

  public void add_supplier (CosEvent.ProxyPushSupplier pps)
  {
    _impl.add_supplier(pps);
  } // add_supplier

  public void remove_consumer (CosEvent.ProxyPushConsumer ppc)
  {
    _impl.remove_consumer(ppc);
  } // remove_consumer

  public void remove_supplier (CosEvent.ProxyPushSupplier pps)
  {
    _impl.remove_supplier(pps);
  } // remove_supplier

  public void add_event (CosEvent.Event event)
  {
    _impl.add_event(event);
  } // add_event

  private CosEvent.CanalOperations _impl;
  private org.omg.PortableServer.POA _poa;

} // class CanalPOATie