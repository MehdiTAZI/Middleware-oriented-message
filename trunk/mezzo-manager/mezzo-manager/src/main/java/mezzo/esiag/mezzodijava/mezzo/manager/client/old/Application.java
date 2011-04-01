package mezzo.esiag.mezzodijava.mezzo.manager.client.old;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.esiag.mezzodijava.mezzo.manager.client.monitoring.MonitoringPanel;




/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application
    implements EntryPoint
{
	private AdminPanel adminPanel = new AdminPanel();
	private String adminPanelTitle ="Administration";
	private String monitoringPanelTitle ="Monitoring";
	private String homePageLabel ="Bienvenue sur la console d'administration de mezzo";
	VerticalPanel vadminPanel;
	DecoratorPanel decorationAdminPanel;
	
  /**
   * This is the entry point method.
   */
	
  public void onModuleLoad()
  {
	  	final Label label = new Label (homePageLabel);
	 
	  	Widget wiPanel = adminPanel.onInitialize();
	  	
		DisclosurePanel advancedAdminPanel = new DisclosurePanel(adminPanelTitle);
		advancedAdminPanel.setAnimationEnabled(true);
	    advancedAdminPanel.ensureDebugId("cwDisclosurePanel"+adminPanelTitle);
	    advancedAdminPanel.setContent(wiPanel);
	  
		DisclosurePanel advancedMonitoringPanel = new DisclosurePanel(monitoringPanelTitle);
		advancedMonitoringPanel.setAnimationEnabled(true);
		advancedMonitoringPanel.ensureDebugId("cwDisclosurePanel"+monitoringPanelTitle);
		advancedMonitoringPanel.setContent(new MonitoringPanel());
	    
	  	
		// Create a tab panel
		DecoratedTabPanel MenuPannel = new DecoratedTabPanel();
		MenuPannel.setWidth("500px");
		
		MenuPannel.add(advancedAdminPanel, adminPanelTitle);
		MenuPannel.add(advancedMonitoringPanel, monitoringPanelTitle);
	  	MenuPannel.setAnimationEnabled(true);
	  	MenuPannel.selectTab(0);
	  	

	   
	    vadminPanel = new VerticalPanel();
		vadminPanel.add(label);
		vadminPanel.add(MenuPannel);

			// Wrap the contents in a DecoratorPanel
		    decorationAdminPanel = new DecoratorPanel();
		    
		    decorationAdminPanel.setWidget(vadminPanel);
		    
		
     RootPanel.get().add(decorationAdminPanel);
     
  }
}
