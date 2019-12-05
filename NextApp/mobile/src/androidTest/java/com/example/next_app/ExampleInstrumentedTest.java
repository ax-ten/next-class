package com.example.next_app;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

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
        InputStream resource = appContext.getResources().openRawResource(R.raw.schedule_stubs);
        Log.v(TAG, "parse iniziato");
        LinkedList<Stub> stubList = parseXML(resource);
        Log.v(TAG, "parse terminato");

    }

    //PARSING
    public  LinkedList<Stub> parseXML(InputStream istream) {
        LinkedList<Stub> schedule = new LinkedList<>();
        XmlPullParserFactory factory;
        XmlPullParser parser;
        int eventType;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
            parser.setInput(istream, null);
            String text = "test";
            Stub stub= new Stub(1,1,null,null,null,1,1);

            eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("course")) {
                            // create a new instance of com.poliba.mylibrary.Stub
                            stub = new Stub();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("course")) {
                            // add employee object to list
                            schedule.add(stub);
                        } else if (tagname.equalsIgnoreCase("teacher")) {
                            stub.setTeacherName(text);
                        } else if (tagname.equalsIgnoreCase("name")) {
                            stub.setCourseName(text);
                        } else if (tagname.equalsIgnoreCase("day")) {
                            stub.setDay(Integer.parseInt(text));
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return schedule;
    }
}
