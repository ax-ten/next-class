package com.poliba.mylibrary;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Schedule {
    private ArrayList<Stub> schedule;
    public String name;
    private HashMap<String, Double> attendance;

    public Schedule(File file) {
        InputStream input = null;
        try {
            input = new FileInputStream(file);
            name = file.getName();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        parseXML(input);
    }

    public Schedule(ArrayList<Stub> schedule){
        this.schedule = schedule;
    }

    public Schedule(){

    }


    public ArrayList<Stub> getSchedule() {
        return schedule;
    }

    //PARSING
    private void parseXML(InputStream istream) {
        if (schedule != null)
            schedule.clear();
        else
            schedule = new ArrayList<>();
        XmlPullParserFactory factory;
        String text = null;
        Stub stub= new Stub();
        XmlPullParser parser;
        int eventType;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            parser = factory.newPullParser();
            parser.setInput(istream, null);

            eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("course")) {
                            stub = new Stub();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        switch (tagname){
                            case "course":
                                this.getSchedule().add(stub);
                                break;
                            case "teacher":
                                stub.setTeacherName(text);
                                break;
                            case "name":
                                stub.setCourseName(text);
                                break;
                            case "day":
                                stub.setDay(Integer.parseInt(text));
                                break;
                            case "year":
                                stub.setYear(Integer.parseInt(text));
                                break;
                            case "room":
                                stub.setRoom(text);
                                break;
                            case "startTime":
                                stub.setStartTime(Double.parseDouble(text));
                                break;
                            case "endTime":
                                stub.setEndTime(Double.parseDouble(text));
                                break;
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

    public Schedule getDailySchedule(int day){
       Schedule schedule = new Schedule(new ArrayList<Stub>());
        for (Stub stub : schedule.getSchedule()){
            if (stub.getDay() == day)
                schedule.getSchedule().add(stub);
        }
        return schedule;
    }
}
