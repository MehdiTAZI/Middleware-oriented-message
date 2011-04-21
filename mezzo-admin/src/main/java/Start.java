import java.net.URL;
import java.security.ProtectionDomain;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class Start {
 
  public static void main(String[] args) throws Exception {
    Server server = new Server();
    
    /*
    SelectChannelConnector connector0 = new SelectChannelConnector();
    connector0.setMaxIdleTime(1000 * 60 * 60);
    connector0.setSoLingerTime(-1);
    connector0.setPort(8080);
    

    SelectChannelConnector connector1 = new SelectChannelConnector();
    connector1.setMaxIdleTime(1000 * 60 * 60);
    connector1.setSoLingerTime(-1);
    connector1.setPort(8888);
    connector1.setHost("127.0.0.1");   
    connector1.setName("admin");
    
    SelectChannelConnector connector2 = new SelectChannelConnector();
    connector2.setMaxIdleTime(1000 * 60 * 60);
    connector2.setSoLingerTime(-1);
    connector2.setPort(9999);
    connector2.setHost("194.214.13.176");   
    connector2.setName("client1");
    
    SelectChannelConnector connector3 = new SelectChannelConnector();
    connector3.setMaxIdleTime(1000 * 60 * 60);
    connector3.setSoLingerTime(-1);
    connector3.setPort(2211);
    connector3.setHost("194.214.13.177");   
    connector3.setName("client2");
    
    
    SelectChannelConnector connector4 = new SelectChannelConnector();
    connector4.setMaxIdleTime(1000 * 60 * 60);
    connector4.setSoLingerTime(-1);
    connector4.setPort(3543);
    connector4.setHost("194.214.13.178");   
    connector4.setName("client3");*/
    
    SocketConnector connector = new SocketConnector();
 
    // Set some timeout options to make debugging easier.
    connector.setMaxIdleTime(1000 * 60 * 60);
    connector.setSoLingerTime(-1);
    connector.setPort(8080);
   
    server.setConnectors(new Connector[] { connector });
    //server.setConnectors(new Connector[] { connector0,connector1,connector2,connector3,connector4 });
 
    WebAppContext context = new WebAppContext();
    context.setServer(server);
    context.setContextPath("/");
 
    ProtectionDomain protectionDomain = Start.class.getProtectionDomain();
    URL location = protectionDomain.getCodeSource().getLocation();
    context.setWar(location.toExternalForm());
 
    server.addHandler(context);
    try {
      server.start();
      System.in.read();
      server.stop();
      server.join();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(100);
    }
  }
}