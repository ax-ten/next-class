package com.example.next_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Phone_Activity_AddNewSchedule extends AppCompatActivity {
    private String[] CdL; //TODO ottenerle da internet
    private String[] years; //TODO ottenerle da CdL
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_activity_addnewschedule);
    }

}
