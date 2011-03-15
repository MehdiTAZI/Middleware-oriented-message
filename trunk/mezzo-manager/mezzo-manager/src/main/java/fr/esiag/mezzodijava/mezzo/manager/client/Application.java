package fr.esiag.mezzodijava.mezzo.manager.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;



/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application
    implements EntryPoint
{
	private AdminPanel control = new AdminPanel();
  /**
   * This is the entry point method.
   */
	
  public void onModuleLoad()
  {
	  	final Label label = new Label ( "Bienvenue sur la console d'administration de mezzo");
	 
	  	Widget wiPanel = control.onInitialize();
	  	
		DisclosurePanel advancedDisclosure = new DisclosurePanel("Administration");
		advancedDisclosure.setAnimationEnabled(true);
	    advancedDisclosure.ensureDebugId("cwDisclosurePanel");
	    advancedDisclosure.setContent(wiPanel);
	   
	    VerticalPanel vPanel = new VerticalPanel();
		vPanel.add(label);
		vPanel.add(advancedDisclosure);

			// Wrap the contents in a DecoratorPanel
		    DecoratorPanel decPanel = new DecoratorPanel();
		    decPanel.setTitle("Console d'administration");
		    decPanel.setWidget(vPanel);
		    
		
     RootPanel.get().add(decPanel);
     
  }
}
