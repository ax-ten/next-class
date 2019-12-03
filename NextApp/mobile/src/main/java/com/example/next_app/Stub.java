package com.example.next_app;

import android.content.Context;
import android.content.res.Resources;

public class Stub {
    public int year, day;
    public String courseName, teacherName, room;
    public double startTime, endTime;

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

    //Other methods
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
}
