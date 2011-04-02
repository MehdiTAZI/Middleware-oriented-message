package fr.esiag.mezzodijava.mezzo.manager.client;



import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.esiag.mezzodijava.mezzo.manager.shared.ChannelInfosCollector;


public interface AuthenticationServiceAsync {
	void authenticate(String loging, String pwd, AsyncCallback<ChannelInfosCollector[]> callback);
}
