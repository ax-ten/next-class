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
    EditText bt_number;
    Spinner bt_spinner;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_menu_activity);

        bt_switch = (Switch) findViewById(R.id.notifySwitch);
        bt_number = (EditText) findViewById(R.id.minutesEarly);
        bt_spinner = (Spinner) findViewById(R.id.languageSelector);

        String[] lingua = {"Italiano","English","Español","Francais"};

        bt_spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lingua));


    }



}

