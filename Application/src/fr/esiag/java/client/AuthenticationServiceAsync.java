package fr.esiag.java.client;



import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.esiag.java.shared.ChannelInfosCollector;


public interface AuthenticationServiceAsync {
	void authenticate(String loging, String pwd, AsyncCallback<ChannelInfosCollector[]> callback);
}
