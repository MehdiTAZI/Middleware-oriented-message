package fr.esiag.mezzodijava.mezzo.manager.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("greet")
public interface AuthenticationService extends RemoteService {
	public boolean authenticate(String loging,String pwd);
}
