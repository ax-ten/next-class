package com.example.next_app;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.ImageView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Wearable_Activity_Map extends WearableActivity {
    ImageView iv;
    HashMap<String, int[]> positions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        updatePinPosition();
        setContentView(R.layout.wearable_activity_map);

    }

    private void updatePinPosition(){
        iv = findViewById(R.id.pin);
        //todo get iv x and y values by values.room_coordinates.xml
    }

    private void parseXML(){
        InputStream in = null;
        if (positions != null)
            positions.clear();
        else
            positions = new HashMap<>();

        String tagname;
        XmlPullParserFactory factory;
        XmlPullParser parser;
        Classroom classroom = new Classroom();
        int eventType;
        String text = null;

        try{
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            parser = factory.newPullParser();
            parser.setInput(in,null);

            eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                tagname = parser.getName();

                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("classroom")){
                            classroom = new Classroom();
                        }

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        switch (tagname){
                            case "classroom":
                                int[] coords = {classroom.getX(), classroom.getY()};
                                positions.put(classroom.getName(), coords );
                            case "name":
                                classroom.setName(text);
                                break;
                            case "x":
                                classroom.setX(Integer.parseInt(text));
                                break;
                            case "y":
                                classroom.setY(Integer.parseInt(text));
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + tagname);
                        }
                    default:
                        break;

                }
                eventType = parser.next();
            }


        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

    }
    private class Classroom{
        private int x;
        private int y;
        private String name;

        public Classroom(){
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getX() {
            return x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getY() {
            return y;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
