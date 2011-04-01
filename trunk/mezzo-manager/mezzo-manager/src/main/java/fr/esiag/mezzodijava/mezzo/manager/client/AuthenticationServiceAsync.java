package fr.esiag.mezzodijava.mezzo.manager.client;



import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AuthenticationServiceAsync {
	void authenticate(String loging, String pwd, AsyncCallback<Boolean> callback);
}
