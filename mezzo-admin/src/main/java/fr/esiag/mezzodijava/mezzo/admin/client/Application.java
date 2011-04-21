package fr.esiag.mezzodijava.mezzo.admin.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.user.client.ui.DecoratedTabPanel;
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
	private AdminEventPanel adminPanel = new AdminEventPanel();
	private AdminTimePanel adminPanelTime = new AdminTimePanel();
	
	private String adminPanelTitle ="Administration Serveur D'evenement";
	private String adminPanelTitleTime ="Administration Serveur de temps";
	
	private String homePageLabel ="Bienvenue sur la console d'administration de mezzo\n\n";
	
	VerticalPanel vadminPanel;
	VerticalPanel vadminPanelTime;
	
	DecoratorPanel decorationAdminPanel;
	DecoratorPanel decorationAdminPanelTime;
	
  /**
   * This is the entry point method.
   */
	
  public void onModuleLoad()
  {
	  	final Label label = new Label (homePageLabel);
	 
	  	Widget wiPanel = adminPanel.onInitialize();
	  	Widget wiPanelTime = adminPanelTime.onInitialize();
	  	
		DisclosurePanel advancedAdminPanel = new DisclosurePanel(adminPanelTitle);
		advancedAdminPanel.setAnimationEnabled(true);
	    advancedAdminPanel.ensureDebugId("cwDisclosurePanel"+adminPanelTitle);
	    advancedAdminPanel.setContent(wiPanel);
	    
	    DisclosurePanel advancedAdminPanelTime = new DisclosurePanel(adminPanelTitleTime);
		advancedAdminPanelTime.setAnimationEnabled(true);
	    advancedAdminPanelTime.ensureDebugId("cwDisclosurePanel"+adminPanelTitleTime);
	    advancedAdminPanelTime.setContent(wiPanelTime);
	  
		/*DisclosurePanel advancedMonitoringPanel = new DisclosurePanel(monitoringPanelTitle);
		advancedMonitoringPanel.setAnimationEnabled(true);
		advancedMonitoringPanel.ensureDebugId("cwDisclosurePanel"+monitoringPanelTitle);
		advancedMonitoringPanel.setContent(new MonitoringPanel());*/
	    
	  	
		// Create a tab panel
		DecoratedTabPanel MenuPannel = new DecoratedTabPanel();
		
		MenuPannel.setWidth("500px");
		
		MenuPannel.add(advancedAdminPanel, adminPanelTitle);
		//MenuPannel.add(advancedMonitoringPanel, monitoringPanelTitle);
	  	MenuPannel.setAnimationEnabled(true);
	  	MenuPannel.selectTab(0);
	  	
	  	
	 // Create a tab panel
		DecoratedTabPanel MenuPannelTime = new DecoratedTabPanel();
		
		MenuPannelTime.setWidth("500px");
		
		MenuPannelTime.add(advancedAdminPanelTime, adminPanelTitleTime);
		//MenuPannel.add(advancedMonitoringPanel, monitoringPanelTitle);
		MenuPannelTime.setAnimationEnabled(true);
		MenuPannelTime.selectTab(0);
	  	

	   
	    vadminPanel = new VerticalPanel();
		vadminPanel.add(label);
		vadminPanel.add(MenuPannel);
		vadminPanel.add(MenuPannelTime);
		
		
		

			// Wrap the contents in a DecoratorPanel
		    decorationAdminPanel = new DecoratorPanel();
		    
		    decorationAdminPanel.setWidget(vadminPanel);
		    
		
     RootPanel.get().add(decorationAdminPanel);
     
  }
}
