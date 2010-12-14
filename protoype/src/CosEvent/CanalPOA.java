package CosEvent;


/**
* CosEvent/CanalPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from prototype.idl
* samedi 27 novembre 2010 15 h 07 CET
*/

public abstract class CanalPOA extends org.omg.PortableServer.Servant
 implements CosEvent.CanalOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("add_consumer", new java.lang.Integer (0));
    _methods.put ("add_supplier", new java.lang.Integer (1));
    _methods.put ("remove_consumer", new java.lang.Integer (2));
    _methods.put ("remove_supplier", new java.lang.Integer (3));
    _methods.put ("add_event", new java.lang.Integer (4));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // CosEvent/Canal/add_consumer
       {
         CosEvent.ProxyPushConsumer ppc = CosEvent.ProxyPushConsumerHelper.read (in);
         this.add_consumer (ppc);
         out = $rh.createReply();
         break;
       }

       case 1:  // CosEvent/Canal/add_supplier
       {
         CosEvent.ProxyPushSupplier pps = CosEvent.ProxyPushSupplierHelper.read (in);
         this.add_supplier (pps);
         out = $rh.createReply();
         break;
       }

       case 2:  // CosEvent/Canal/remove_consumer
       {
         CosEvent.ProxyPushConsumer ppc = CosEvent.ProxyPushConsumerHelper.read (in);
         this.remove_consumer (ppc);
         out = $rh.createReply();
         break;
       }

       case 3:  // CosEvent/Canal/remove_supplier
       {
         CosEvent.ProxyPushSupplier pps = CosEvent.ProxyPushSupplierHelper.read (in);
         this.remove_supplier (pps);
         out = $rh.createReply();
         break;
       }

       case 4:  // CosEvent/Canal/add_event
       {
         CosEvent.Event event = CosEvent.EventHelper.read (in);
         this.add_event (event);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CosEvent/Canal:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Canal _this() 
  {
    return CanalHelper.narrow(
    super._this_object());
  }

  public Canal _this(org.omg.CORBA.ORB orb) 
  {
    return CanalHelper.narrow(
    super._this_object(orb));
  }


} // class CanalPOA