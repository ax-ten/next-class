package com.example.next_app;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Wearable_Activity_Map extends WearableActivity {
    HashMap<String, int[]> positions;
    String classname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wearable_activity_map);

        int[] coords = getPinPosition("A");
        ImageView iv = findViewById(R.id.pin);

        if (coords != null){
            iv.setX(coords[0]);
            iv.setY(coords[1]);
        } else {
            iv.setVisibility(View.INVISIBLE);
            Toast.makeText(this,"Classroom not found", Toast.LENGTH_SHORT).show();
        }
    }

    private int[] getPinPosition(String classname)  {
        try{
            InputStream classroomXML = this.getResources().openRawResource(R.raw.classroom);
            parseXML(classroomXML);
            for (String iterableName : positions.keySet()){
                if (classname.equals(iterableName)){
                    return positions.get(iterableName);
                }
            }
            classroomXML.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseXML(InputStream in){
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
                                assert text != null;
                                classroom.setX(Integer.parseInt(text));
                                break;
                            case "y":
                                assert text != null;
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

        Classroom(){
        }

        void setX(int x) {
            this.x = x;
        }

        int getX() {
            return x;
        }

        void setY(int y) {
            this.y = y;
        }

        int getY() {
            return y;
        }

        void setName(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }
}
