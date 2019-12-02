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
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Stub {
    private int id,year, day;
    private String courseName, teacherName, room;
    private double startTime, endTime;

    //Constructors
    public Stub(int y, int d, int id){
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


    }
    public Stub(){}


    //Public methods
    public List readFeed(InputStream is) throws XmlPullParserException, IOException{
        List entries = new ArrayList();
        XmlPullParserFactory parserFactory;

        try{
           parserFactory  = XmlPullParserFactory.newInstance();
           XmlPullParser parser = parserFactory.newPullParser();
           parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
           parser.setInput(is,null);

           processParsing(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }catch (IOException e) {
        }

        return entries;
    }



    //Other methods
    private void processParsing(XmlPullParser parser) throws  IOException, XmlPullParserException{
        ArrayList<Stub> stubs = new ArrayList<>();
        int eventType = parser.getEventType();
        Stub currentStub = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String eltName = null;

            switch (eventType){
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();
                    if ("class".equals(eltName)){
                        currentStub = new Stub();
                    }
            }
        }
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
