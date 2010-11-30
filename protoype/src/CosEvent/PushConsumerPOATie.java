package CosEvent;


/**
* CosEvent/PushConsumerPOATie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from prototype.idl
* samedi 27 novembre 2010 15 h 07 CET
*/

public class PushConsumerPOATie extends PushConsumerPOA
{

  // Constructors

  public PushConsumerPOATie ( CosEvent.PushConsumerOperations delegate ) {
      this._impl = delegate;
  }
  public PushConsumerPOATie ( CosEvent.PushConsumerOperations delegate , org.omg.PortableServer.POA poa ) {
      this._impl = delegate;
      this._poa      = poa;
  }
  public CosEvent.PushConsumerOperations _delegate() {
      return this._impl;
  }
  public void _delegate (CosEvent.PushConsumerOperations delegate ) {
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
  public void disconnect ()
  {
    _impl.disconnect();
  } // disconnect

  public void push (CosEvent.Event event)
  {
    _impl.push(event);
  } // push

  private CosEvent.PushConsumerOperations _impl;
  private org.omg.PortableServer.POA _poa;

} // class PushConsumerPOATie