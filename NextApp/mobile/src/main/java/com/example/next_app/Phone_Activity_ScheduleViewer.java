package com.example.next_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Phone_Activity_ScheduleViewer extends AppCompatActivity {

    //TODO questa activity deve caricare il fragment dailystublist
    // deve poi disporre gli item settando i margini in maniera tale che combacino con gli orari nella colonna a sx

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_activity_scheduleviewer);

    }

}
