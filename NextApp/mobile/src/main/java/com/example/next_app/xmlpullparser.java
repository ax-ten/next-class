package com.example.next_app;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.nio.file.Path;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.FileSystems;


public class xmlpullparser {

    public static void main(String[] args) {
        String relativePath = "mobile/src/main/java/com/example/next_app";
        Path path = FileSystems.getDefault().getPath(relativePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(path+"/scheduleStubs.xml");
            NodeList schedule =  doc.getElementsByTagName("class");
            for (int i=0; i<schedule.getLength();i++){
               Node node = schedule.item(i);
               if (node.getNodeType() == Node.ELEMENT_NODE){
                   Element stub =(Element) node;
                   System.out.println("id: " +stub.getAttribute("id"));
                   NodeList attributes = stub.getChildNodes();
                   for(int j=0; j<attributes.getLength();j++){
                       if( attributes.item(j).getNodeType() == Node.ELEMENT_NODE){
                           Element classElement = (Element) attributes.item(j);
                           System.out.println(classElement.getTagName()+ ": " + classElement.getTextContent());
                       }
                   }
               }
            }
        } catch (ParserConfigurationException e ){
            e.printStackTrace();
        } catch (SAXException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
