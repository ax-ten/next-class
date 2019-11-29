package com.example.next_app;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.icu.util.Calendar;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Stub {
    private int id;
    private int year;
    private int day;
    private String courseName;
    private String teacherName;
    private String room;
    private double startTime;
    private double endTime;

    public Stub(Context context, int y, int d, int id){
        this.id = id;
        this.year = y;
        this.day = d;


        /** todo implement these
        this.courseName = ;
        this.startTime = ;
        this.endTime = ;
        this.room = ;
        this.teacherName = ;
         */

        XmlResourceParser xml = context.getResources().getXml(R.xml.schedule_stubs);
        String relativePath = "mobile/src/main/java/com/example/next_app";
        Path path = FileSystems.getDefault().getPath(relativePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(path+"/schedule_stubs.xml");
            NodeList schedule =  doc.getElementsByTagName("day");
            for (int i=0; i<schedule.getLength();i++){
                Node node = schedule.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element stub =(Element) node;
                    String day = stub.getAttribute("day");


                    String[] days = getDays(context);
                    System.out.println("day: " + days[Integer.parseInt(day)]);

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

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException{
        List entries = new ArrayList();
        while (parser.next() != XmlPullParser.END_TAG){
            String name = parser.getName();
            if (name.equals("year"))
                entries.add(parser.getText());
            Log.d("roba",parser.getText() );
            parser.nextTag();
        }
        return entries;
    }

    private String[] getDays(Context context){
        Resources resources = context.getResources();
        String[] days = new String[]
                {resources.getString(R.string.monday),
                resources.getString(R.string.tuesday),
                resources.getString(R.string.wednesday),
                resources.getString(R.string.thursday),
                resources.getString(R.string.friday)};
        return days;
    }
}
