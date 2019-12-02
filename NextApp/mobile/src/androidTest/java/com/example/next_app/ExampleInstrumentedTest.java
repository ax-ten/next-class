package com.example.next_app;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
        try {
            readFeed(appContext.getResources().getXml(R.xml.schedule_stubs));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals("com.example.next_app", appContext.getPackageName());
    }

    private List readFeed(XmlResourceParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();
        int xmlTAG = parser.getEventType();
        Log.d("test","fin qui ha funzionato sir");
        while (xmlTAG != XmlPullParser.END_TAG){
            String name = parser.getName();
            Log.d("test", parser.getText() );
            parser.nextTag();
        }
        return entries;
    }
}
