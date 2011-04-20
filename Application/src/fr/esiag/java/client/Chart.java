package fr.esiag.java.client;

import com.google.gwt.user.client.ui.Button;
import com.googlecode.gchart.client.GChart;

public class Chart extends GChart{
	 String[] groupLabels = null;

	final String[] legendLabel = {
			"ConsumerConnected", "ConsumerSubscribed", "SupplierConnected", "NbMessages"};
	
	final String[] barColors = {
			"red", "blue", "green", "silver"};
	final int MAX = 40; 
	final int WIDTH = 400;
	final int HEIGHT = 200;
	//private int[] valeur=new int[]{0,2,1,348};
	
	public Chart(String[]channels,int valeurs[][]){
		int j=0;
		groupLabels=new String[channels.length];
		for(String channelName :channels){
			groupLabels[j]="<html>"+channelName+"<br>";
			j++;
		}
		setChartSize(WIDTH, HEIGHT);
		setChartTitle("<b><big><big>" +
				"Stats Channels " + 
		"</big></big><br>&nbsp;</b>");
		for (int iCurve=0; iCurve < legendLabel.length; iCurve++) { 
			addCurve();     // one curve per quarter
			getCurve().getSymbol().setSymbolType(
					SymbolType.VBAR_SOUTHWEST);
			getCurve().getSymbol().setBackgroundColor(barColors[iCurve]);
			getCurve().setLegendLabel(legendLabel[iCurve]);
			getCurve().getSymbol().setHovertextTemplate(
					GChart.formatAsHovertext(legendLabel[iCurve] + "=${y}"));
			getCurve().getSymbol().setModelWidth(1.0);
			getCurve().getSymbol().setBorderColor("black");
			getCurve().getSymbol().setBorderWidth(1);
			for (int jGroup=0; jGroup < groupLabels.length; jGroup++) { 
				// the '+1' creates a bar-sized gap between groups 
				getCurve().addPoint(1+iCurve+jGroup*(4+1),
						valeurs[jGroup][iCurve]);
				
				getCurve().getPoint().setAnnotationLocation(
						AnnotationLocation.NORTH);
			}
		}
		//Pour Mettre les titres men te7te le graphique
		for (int i = 0; i < groupLabels.length; i++) {
			// formula centers tick-label horizontally on each group 
			getXAxis().addTick(
					4/2. + i*(4+1), 
					groupLabels[i]); 
		}
		//Fin
		getXAxis().setTickLabelFontSize(20);
		getXAxis().setTickLabelThickness(40);
		getXAxis().setTickLength(6);    // small tick-like gap... 
		getXAxis().setTickThickness(0); // but with invisible ticks
		getXAxis().setAxisMin(0);       // keeps first bar on chart

		getYAxis().setAxisMin(0);           // Based on sim revenue range
		getYAxis().setAxisMax(MAX); // of 0 to MAX_REVENUE.
		getYAxis().setTickCount(11);
		getYAxis().setHasGridlines(true);
		//getYAxis().setTickLabelFormat("$#,###");
	}
}
