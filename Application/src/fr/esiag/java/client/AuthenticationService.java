package fr.esiag.java.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.esiag.java.shared.ChannelInfosCollector;
@RemoteServiceRelativePath("greet")
public interface AuthenticationService extends RemoteService {
	public ChannelInfosCollector[] authenticate(String loging,String pwd);
}
