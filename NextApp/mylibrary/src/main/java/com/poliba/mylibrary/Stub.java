package com.poliba.mylibrary;

//TODO cambiare nome libreria 'mylibrary' in 'nextapplibrary'

import android.os.Parcel;
import android.os.Parcelable;

public class Stub implements Parcelable {
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
    }

    protected Stub(Parcel in) {
        year = in.readInt();
        day = in.readInt();
        courseName = in.readString();
        teacherName = in.readString();
        room = in.readString();
        startTime = in.readDouble();
        endTime = in.readDouble();
    }

    public static final Creator<Stub> CREATOR = new Creator<Stub>() {
        //TODO : Creator<Stub>
        @Override
        public Stub createFromParcel(Parcel in) {
            return new Stub(in);
        }

        @Override
        public Stub[] newArray(int size) {
            return new Stub[size];
        }
    };

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

    @Override
    public int describeContents() {
        //TODO
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeInt(day);
        dest.writeString(courseName);
        dest.writeString(teacherName);
        dest.writeString(room);
        dest.writeDouble(startTime);
        dest.writeDouble(endTime);
    }
}
