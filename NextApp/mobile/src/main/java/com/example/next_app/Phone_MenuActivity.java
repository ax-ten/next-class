package com.example.next_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class Phone_MenuActivity extends AppCompatActivity {

    Switch bt_switch;
    EditText minuteEarly;
    Spinner bt_spinner;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_menu_activity);
        this.getSupportActionBar().hide();

        bt_switch =  findViewById(R.id.notifySwitch);

        minuteEarly =  findViewById(R.id.minutesEarly);
        minuteEarly.setHint(R.string.minutes_early);

        bt_spinner = findViewById(R.id.languageSelector);

        String[] lingua = {"Italiano","English","Español","Francais"};

        bt_spinner.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lingua));


    }



}

