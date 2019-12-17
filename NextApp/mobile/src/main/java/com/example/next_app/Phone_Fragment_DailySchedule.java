package com.example.next_app;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class Phone_Fragment_DailySchedule extends Fragment {
    private int layoutId = R.layout.phone_fragment_dailyschedule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(layoutId, container, false);

        return rootView;
    }
}