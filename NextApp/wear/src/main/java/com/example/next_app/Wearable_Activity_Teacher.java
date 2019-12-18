package com.example.next_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Wearable_Activity_Teacher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        //updatePinPosition();
        setContentView(R.layout.wearable_activity_teacher);
    }

}
