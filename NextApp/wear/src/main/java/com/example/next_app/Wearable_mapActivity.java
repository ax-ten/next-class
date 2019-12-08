package com.example.next_app;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Wearable_mapActivity extends AppCompatActivity {
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updatePinPosition();
        setContentView(R.layout.wearable_map_activity);

    }

    private void updatePinPosition(){
        iv = findViewById(R.id.pin);
        //todo get iv x and y values by values.room_coordinates.xml
    }
}
