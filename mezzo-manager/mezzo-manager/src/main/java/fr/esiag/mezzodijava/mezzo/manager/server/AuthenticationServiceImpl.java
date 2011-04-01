package fr.esiag.mezzodijava.mezzo.manager.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.esiag.mezzodijava.mezzo.manager.client.AuthenticationService;



public class AuthenticationServiceImpl  extends RemoteServiceServlet implements AuthenticationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean authenticate(String loging, String pwd) {
		System.out.println(loging+":"+pwd);
		if("tazi".equals(loging) && "poyo".equals(pwd))
			return true;
		return false;
	}
}