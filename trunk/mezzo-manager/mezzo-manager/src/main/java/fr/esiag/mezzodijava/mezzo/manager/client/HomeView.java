package fr.esiag.mezzodijava.mezzo.manager.client;



import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;



public class HomeView extends View{
	private Widget content=null;
	private ListGrid messageGrid;
	public HomeView(Test2 view) {
		super(view);
	}

	@Override
	public Widget getContent() {
		if(content==null)
			content= build();
		return content;
	}

	private Widget build() {
		 ListGrid ChannelGrid = new ListGrid() {  
            @Override  
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {  
           	 String fieldName = this.getFieldName(colNum);  
                 if (fieldName.equals("buttonInfo")) {  
                    IButton button = new IButton();  
                    button.setHeight(18);  
                    button.setWidth(65);                      
                    button.setTitle("Info");  
                    button.addClickHandler(new ClickHandler() {  
      

						public void onClick(
								com.smartgwt.client.widgets.events.ClickEvent event) {
							messageGrid.setData(MessageData.getRecords()); 
						}

                    });  
                    return button;  
                } else  if (fieldName.equals("iconField")) {    
                	ImgButton chartImg=new ImgButton(); 
                	chartImg.setSrc("charts.png");
                	 chartImg.setHeight(16);  
                     chartImg.setWidth(16);  
                	chartImg.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							SC.say("Chart Icon Clicked for channel : " + record.getAttribute("topic"));
						}
					});
                	return chartImg;  
                }
                else return null;
            }  
        };  
        ChannelGrid.setShowRecordComponents(true);          
        ChannelGrid.setShowRecordComponentsByCell(true);  
        ChannelGrid.setCanRemoveRecords(true);  
        ChannelGrid.setWidth(785);  
        ChannelGrid.setHeight(230);  
        ChannelGrid.setShowAllRecords(true);  
        ListGridField topic = new ListGridField("topic", "Topic");
        topic.setAlign(Alignment.CENTER);
        ListGridField capacity = new ListGridField("capacity", "Capacity");  
        ListGridField nbConsumer = new ListGridField("nbConsumerConnected", "ConsumersConnected");  
        nbConsumer.setWidth(120);
        nbConsumer.setAlign(Alignment.CENTER);
        capacity.setAlign(Alignment.CENTER);
        ListGridField nbQueueEvents = new ListGridField("nbQeueMessage", "NbMessages");
        nbQueueEvents.setAlign(Alignment.CENTER);
        ListGridField nbConsumersSubscribed =new ListGridField("nbConsumersSubscribed", "ConsumersSubscribed"); 
        nbConsumersSubscribed.setWidth(120);
        nbConsumersSubscribed.setAlign(Alignment.CENTER);
        ListGridField buttonField = new ListGridField("buttonInfo", "Info");  
        buttonField.setAlign(Alignment.CENTER);
        ListGridField iconField = new ListGridField("iconField", "Stats");
        iconField.setAlign(Alignment.CENTER);
        ListGridField nbSupplier = new ListGridField("nbSupplierConnected", "SuppliersConnected");  
        nbSupplier.setWidth(120);
        nbSupplier.setAlign(Alignment.CENTER);
        
        ChannelGrid.setFields(topic, capacity,nbConsumer,nbConsumersSubscribed, nbSupplier,nbQueueEvents,buttonField,iconField);  
        ChannelGrid.setCanResizeFields(true);  
        ChannelGrid.setData(ChannelData.getRecords()); 
        
        
        messageGrid = new ListGrid() {  
            @Override  
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {  
           	 String fieldName = this.getFieldName(colNum);  
                 
                    return null;   		           
            }  
        };  
        messageGrid.setShowRecordComponents(true);          
        messageGrid.setShowRecordComponentsByCell(true);  
        messageGrid.setCanRemoveRecords(true);  
  
        messageGrid.setWidth(785);  
        messageGrid.setHeight(220);  
        messageGrid.setShowAllRecords(true);  
        ListGridField time = new ListGridField("time", "Time");
        time.setAlign(Alignment.CENTER);
        ListGridField code = new ListGridField("code", "Code");  
        ListGridField type = new ListGridField("type", "Type");  
        type.setAlign(Alignment.CENTER);
        code.setAlign(Alignment.CENTER);
 
        
        ListGridField data = new ListGridField("data", "Data");  
        data.setAlign(Alignment.CENTER);
  
        messageGrid.setFields(time, code,type, data);  
        messageGrid.setCanResizeFields(true);  
        
      
        
        VerticalPanel principal=new VerticalPanel();
        //Window channelWindow=createWin("ListChannel", true, 796, 250, 0);
        //channelWindow.addItem(ChannelGrid);
        //Window messageWindow=createWin("ListMessage", true, 796, 240, 0);
        //messageWindow.addItem(messageGrid);
        HTML html1=new HTML("ListChannel");
        principal.add(html1);
        principal.add(ChannelGrid);
        HTML html2=new HTML("ListMessage");
        principal.add(html2);
        principal.add(messageGrid);
		return principal;
	}
	
	

	 public  Window createWin(String title, boolean autoSizing, int width, int height, int offsetLeft) {  
		 
		         Window window = new Window();  
		         window.setAutoSize(autoSizing);  
		         window.setTitle(title);  
		         window.setWidth(width);  
		         window.setHeight(height);
		         window.setLeft(offsetLeft);  
		         window.setCanDragReposition(false);  
		         window.setCanDragResize(false);
		         window.setShowCloseButton(false);
		         window.setShowMinimizeButton(false);
		         return window;  
		     }  
}
