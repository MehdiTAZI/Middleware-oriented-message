package fr.esiag.mezzodijava.mezzoproto.capteur;

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class XMLFile implements Implementor{
	
	private Vector<String> data=new Vector<String>();
	
	public XMLFile() {
			
	}
	
	public void parserXML(String file){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder parser = factory.newDocumentBuilder();
			//Document document = parser.parse(new File(file));
			Document document = parser.parse(this.getClass().getResourceAsStream (file));

			Node root = document.getFirstChild();
			while (root.getNodeType()!=Node.ELEMENT_NODE) root = root.getNextSibling();
			
			NodeList list = root.getChildNodes();
			
			for (int i=0; i<list.getLength(); i++) {
				Node node = list.item(i);				
				String row="";				
				if (node.getNodeName().equals("message")) {					
					NodeList nl=node.getChildNodes();
									
					for (int j = 0; j < nl.getLength(); j++) {
						Node n=nl.item(j);						
						if(n.getNodeName().equals("code"))
							row+=n.getTextContent();
						if(n.getNodeName().equals("type"))
							row+=" "+n.getTextContent();
						if(n.getNodeName().equals("data"))
							row+=" "+n.getTextContent();					
						
					}
					data.add(row);
					row="";
			}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Vector<String> getData() {
			
		parserXML("/data.xml");
		return data;
	}
	
	
}
