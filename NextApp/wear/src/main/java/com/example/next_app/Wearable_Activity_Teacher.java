package com.example.next_app;

import android.os.Bundle;

import android.support.wearable.activity.WearableActivity;
public class Wearable_Activity_Teacher extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wearable_activity_teacher);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}
