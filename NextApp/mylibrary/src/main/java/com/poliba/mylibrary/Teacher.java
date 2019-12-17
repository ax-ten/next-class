package com.poliba.mylibrary;

public class Teacher {
    private String name;
    private String email;
    private String office;
    private boolean[] meetingDays;
    private double[][] meetinghours;

    public Teacher(){

    }
    public Teacher(String n, String e){
        name = n;
        email = e;
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
}
