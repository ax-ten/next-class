package com.poliba.mylibrary;

public class Teacher {
    private String name;
    private String email;
    private String office;
    private boolean[] meetingDays;
    private double[][] meetingHours;

    public Teacher(){

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public boolean[] getMeetingDays() {
        return meetingDays;
    }

    public void setMeetingDays(boolean[] meetingDays) {
        this.meetingDays = meetingDays;
    }

    public double[][] getMeetingHours() {
        return meetingHours;
    }

    public void setMeetingHours(double[][] meetingHours) {
        this.meetingHours = meetingHours;
    }
}
