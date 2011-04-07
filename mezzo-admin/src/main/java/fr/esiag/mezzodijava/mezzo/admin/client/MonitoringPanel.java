package fr.esiag.mezzodijava.mezzo.admin.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;


public class MonitoringPanel extends Composite {
	private VerticalPanel verticalPanel;
	private ListBox channelList;
	private DisclosurePanel disclosurePanel;
	private Grid grid;
	private Label topicLabel;
	private TextBox topicText;
	private TextBox capacityText;
	private Label capacityLabel;
	private ListBox suppliersList;
	private ListBox consumersSubscribedList;
	private ListBox consumersConnectedList;
	private ListBox messageList;
	private DisclosurePanel listChannelPanel;
	private VerticalPanel verticalPanel_1;
	private DisclosurePanel supplierListPanel;
	private DisclosurePanel consumerSubListPanel;
	private DisclosurePanel ListMessageConsumerPannel;
	private DisclosurePanel ConsumerconnecteListPannel;

	public MonitoringPanel() {
		
		disclosurePanel = new DisclosurePanel("Monitoring Panel", true);
		disclosurePanel.setAnimationEnabled(true);
		initWidget(disclosurePanel);
		
		verticalPanel = new VerticalPanel();
		
		disclosurePanel.setContent(verticalPanel);
		
		listChannelPanel = new DisclosurePanel("Liste des Channel", true);
		
		listChannelPanel.setAnimationEnabled(true);
		verticalPanel.add(listChannelPanel);
		
		verticalPanel_1 = new VerticalPanel();
		
		listChannelPanel.setContent(verticalPanel_1);
		verticalPanel_1.setSize("5cm", "107px");
		
		channelList = new ListBox();
		verticalPanel_1.add(channelList);
		channelList.setSize("435px", "96px");
		channelList.setVisibleItemCount(5);
		
		grid = new Grid(1, 4);
		verticalPanel.add(grid);
		grid.setWidth("435px");
		
		topicLabel = new Label("Topic : ");
		grid.setWidget(0, 0, topicLabel);
		
		topicText = new TextBox();
		grid.setWidget(0, 1, topicText);
		
		capacityLabel = new Label("Capacity : ");
		grid.setWidget(0, 2, capacityLabel);
		
		capacityText = new TextBox();
		grid.setWidget(0, 3, capacityText);
		
		supplierListPanel = new DisclosurePanel("Liste des Suppliers", true);
		supplierListPanel.setAnimationEnabled(true);
		verticalPanel.add(supplierListPanel);
		supplierListPanel.setWidth("451px");
		
		suppliersList = new ListBox();

		supplierListPanel.setContent(suppliersList);
		suppliersList.setVisibleItemCount(5);
		suppliersList.setSize("435px", "96px");
		
		consumerSubListPanel = new DisclosurePanel("Liste des consumers abonnés", true);
		consumerSubListPanel.setAnimationEnabled(true);
		verticalPanel.add(consumerSubListPanel);
		consumerSubListPanel.setWidth("454px");
		
		consumersSubscribedList = new ListBox();
		consumerSubListPanel.setContent(consumersSubscribedList);
		consumersSubscribedList.setVisibleItemCount(5);
		consumersSubscribedList.setSize("435px", "96px");
		
		ConsumerconnecteListPannel = new DisclosurePanel("Liste des consumers connecté", true);
		ConsumerconnecteListPannel.setAnimationEnabled(true);
		verticalPanel.add(ConsumerconnecteListPannel);
		ConsumerconnecteListPannel.setWidth("456px");
		
		consumersConnectedList = new ListBox();
		ConsumerconnecteListPannel.setContent(consumersConnectedList);
		consumersConnectedList.setVisibleItemCount(5);
		consumersConnectedList.setSize("435px", "96px");
		
		ListMessageConsumerPannel = new DisclosurePanel("Liste des Messages du Consumer", true);
		ListMessageConsumerPannel.setAnimationEnabled(true);
		verticalPanel.add(ListMessageConsumerPannel);
		ListMessageConsumerPannel.setSize("459px", "128px");
		
		messageList = new ListBox();
		ListMessageConsumerPannel.setContent(messageList);
		messageList.setVisibleItemCount(5);
		messageList.setSize("435px", "96px");
	}

}
