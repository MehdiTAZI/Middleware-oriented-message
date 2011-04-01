package fr.esiag.mezzodijava.mezzo.manager.client;




import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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


public class Test2 implements EntryPoint {
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
		                 DynamicForm form = new DynamicForm();  
		                 form.setHeight100();  
		                 form.setWidth100();  
		                 form.setPadding(5);  
		                 form.setLayoutAlign(VerticalAlignment.BOTTOM);
		                 login = new TextItem();  
		                 login.setTitle("Login");  
		                 pwd = new TextItem();  
		                 pwd.setTitle("Pasword");
		                 ButtonItem valider=new ButtonItem("valider");
		                 valider.addClickHandler(new ClickHandler() {
							
							public void onClick(ClickEvent event) {
								//login.getValueAsString()
								System.out.println("Click");
								authenticationService.authenticate(login.getValueAsString(),pwd.getValueAsString() , new AsyncCallback<Boolean>() {
									public void onFailure(Throwable caught) {
										System.out.println("Failure");
									}
									public void onSuccess(Boolean result) {
										if(result==true){
											winModal.destroy(); 
											display("homeView");
										}
									}
								
								});	
							}
						});
		                 form.setFields(login, pwd,valider);
		                 winModal.addItem(form);  
		                 winModal.show();  
	}
	public void onModuleLoad() {
		View homeView=new HomeView(this);
		views.put("homeView", homeView);
		showAuthentification();
	}
	public void display(String view) {	
		View v=views.get(view);
		RootPanel.get("col_2").clear();
		RootPanel.get("col_2").add(v.getContent());
	}
}