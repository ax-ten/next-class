package com.example.next_app;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.support.wearable.activity.WearableActivity;
import android.view.View;

import java.util.Objects;

public class Wearable_Activity_Teacher extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wearable_activity_teacher);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}
