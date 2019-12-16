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
    private class Classroom{
        private int x;
        private int y;
        private String name;

        public Classroom(){
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getX() {
            return x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getY() {
            return y;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
