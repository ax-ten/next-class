package com.example.next_app;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.next_app", appContext.getPackageName());

        //PARSE
        String TAG = "TESTING >>>>>>>";

        Log.v(TAG, "caricamento file");
        InputStream resource = appContext.getResources().openRawResource(R.xml.schedule_stubs);
        Log.v(TAG, "parse iniziato");
        LinkedList<Stub> stubList = parseXML(resource);
        Log.v(TAG, "parse terminato");

    }

    //PARSING
    public  LinkedList<Stub> parseXML(InputStream istream){
        LinkedList<Stub> stubList = new LinkedList<>();
        try{
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(istream);

            NodeList courses = doc.getElementsByTagName("class");
            for(int i =0;i<courses.getLength();i++){
                Element elm = (Element)courses.item(i);
                Stub stub = new Stub(
                         Integer.parseInt(getNodeValue("year",elm)),
                         Integer.parseInt(getNodeValue("day",elm)),
                         getNodeValue("name",elm),
                         getNodeValue("teacher",elm),
                         getNodeValue("room",elm),
                         Double.parseDouble(getNodeValue("startTime",elm)),
                         Double.parseDouble(getNodeValue("endTime",elm))
                );
                stubList.add(stub);

            }
        }
        catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return stubList;
    }

    protected String getNodeValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        org.w3c.dom.Node node = nodeList.item(0);
        if(node!=null) {
            if (node.hasChildNodes()) {
                org.w3c.dom.Node child = node.getFirstChild();
                while (child != null) {
                    if (child.getNodeType() == org.w3c.dom.Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }


}
