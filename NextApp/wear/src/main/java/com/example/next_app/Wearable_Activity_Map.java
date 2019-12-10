package com.example.next_app;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Wearable_Activity_Map extends AppCompatActivity {
    ImageView iv;
    //TODO bisogna salvare tutte le posizioni delle aule  in values.room_coordinates.xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updatePinPosition();
        setContentView(R.layout.wearable_activity_map);

    }

    private void updatePinPosition(){
        iv = findViewById(R.id.pin);
        //todo get iv x and y values by values.room_coordinates.xml
    }
}
