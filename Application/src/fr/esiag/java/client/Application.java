package fr.esiag.java.client;




import java.util.HashMap;
import java.util.Map;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

import fr.esiag.java.shared.ChannelInfosCollector;


public class Application implements EntryPoint{
	private final AuthenticationServiceAsync authenticationService = GWT
	.create(AuthenticationService.class);
	private TextItem login;
	private TextItem pwd;
	Map<String,View> views=new HashMap<String, View>();
	private DialogBox dialogBox = new DialogBox();
	public void showAuthentification(){
		 final Window winModal = new Window();  
		 winModal.setWidth(360);  
		                 winModal.setHeight(115);  
		                 winModal.setTitle("Authentication");  
		                 winModal.setShowMinimizeButton(false);  
		                 winModal.setIsModal(true);  
		                 winModal.setShowModalMask(true);  
		                 winModal.centerInPage();  
		                 winModal.addCloseClickHandler(new com.smartgwt.client.widgets.events.CloseClickHandler() {
							public void onCloseClick(CloseClientEvent event) {
								
							}
						});  
		                 authenticationService.authenticate("", "", new AsyncCallback<ChannelInfosCollector[]>() {
							
							@Override
							public void onSuccess(ChannelInfosCollector[] result) {
								display("homeView",result);
							}
							
							@Override
							public void onFailure(Throwable caught) {

							}
						});
//		                 DynamicForm form = new DynamicForm();  
//		                 form.setHeight100();  
//		                 form.setWidth100();  
//		                 form.setPadding(5);  
//		                 form.setLayoutAlign(VerticalAlignment.BOTTOM);
//		                 login = new TextItem();  
//		                 login.setTitle("Login");  
//		                 pwd = new TextItem();  
//		                 pwd.setTitle("Pasword");
//		                 ButtonItem valider=new ButtonItem("valider");
//		                 valider.addClickHandler(new ClickHandler() {
//							
//							public void onClick(ClickEvent event) {
//								//login.getValueAsString()
//								System.out.println("Click");
//								authenticationService.authenticate(login.getValueAsString(), pwd.getValueAsString(), new AsyncCallback<ChannelInfosCollector[]>() {
//									
//									public void onSuccess(ChannelInfosCollector[] channelInfosCollector) {
//										winModal.destroy(); 
//										System.out.println("OnSUCCESS");
//										System.out.println("La taille est = "+channelInfosCollector.length);
//										display("homeView",channelInfosCollector);
//											
//									
//									}
//									
//									public void onFailure(Throwable arg0) {
//										System.out.println("Failure");
//									}
//
//								});
//							}
//						});
//		                 form.setFields(login, pwd,valider);
//		                 winModal.addItem(form);  
//		                 winModal.show();  
	}
	private Timer timer;
	public void onModuleLoad() {
		View homeView=new HomeView(this);
		views.put("homeView", homeView);
		timer=new Timer() {
			@Override
			public void run() {
					 authenticationService.authenticate("", "", new AsyncCallback<ChannelInfosCollector[]>() {
							
							@Override
							public void onSuccess(ChannelInfosCollector[] result) {
								View v=views.get("homeView");
								v.setData(result);
								v.setContent();
							}
							
							@Override
							public void onFailure(Throwable caught) {

							}
						});				
				}
		};
		timer.scheduleRepeating(20000);
		showAuthentification();
		//display("homeView",null);
	}
	public void display(String view,ChannelInfosCollector[] channelInfosCollector) {	
		System.out.println("Display!!");
		View v=views.get(view);
		v.setData(channelInfosCollector);
		RootPanel.get("col_2").clear();
		RootPanel.get("col_2").add(v.getContent());
	}
}