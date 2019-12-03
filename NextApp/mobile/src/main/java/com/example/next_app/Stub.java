package com.example.next_app;

import android.content.Context;
import android.content.res.Resources;

public class Stub {
    private int year, day;
    private String courseName, teacherName, room;
    private double startTime, endTime;

    //Constructors
    public Stub(int y, int d, String cN, String tN, String r, double sT, double eT){
        this.year = y;
        this.day = d;
        this.courseName = cN;
        this.teacherName = tN;
        this.room = r;
        this.startTime = sT;
        this.endTime = eT;
    }


    public Stub(){
        //empty
    }


    //Setters
    public void setYear(int year) {
        this.year = year;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getRoom() {
        return room;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    //Other
    /**
    private final String[] getDays(Context context){
        Resources resources = context.getResources();
        String[] days = new String[]
                {resources.getString(R.string.monday),
                resources.getString(R.string.tuesday),
                resources.getString(R.string.wednesday),
                resources.getString(R.string.thursday),
                resources.getString(R.string.friday)};
        return days;

    }
     */
}
