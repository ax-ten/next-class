package com.example.next_app;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.poliba.mylibrary.Stub;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import static com.example.next_app.R.layout.phone_item_dailystub;
import static com.example.next_app.R.layout.phone_item_schedule;


public class Phone_Fragment_DailySchedule extends ListFragment {
    private final Random colorGen = new Random(Calendar.getInstance().getTimeInMillis());
    private ArrayList<Stub> dailySchedule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = R.layout.phone_fragment_dailyschedule;
        ViewGroup rootView = (ViewGroup) inflater.inflate(layoutId, container, false);

        //test values
        dailySchedule = new ArrayList<>();
        dailySchedule.add(new Stub(2,2,"2","2","2",8.3,2.2));
        dailySchedule.add(new Stub(1,1,"1","1","1",11.0,1.1));
        dailySchedule.add(new Stub(1,1,"1","1","1",14,1.1));
        dailySchedule.add(new Stub(1,1,"1","1","1",11.0,1.1));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        CustomListAdapter cListAdapter = new CustomListAdapter(getActivity(), dailySchedule);
        setListAdapter(cListAdapter);
    }

    class CustomListAdapter extends ArrayAdapter {
        Activity context;
        ArrayList<Stub> dailySchedule;

        CustomListAdapter(Activity context, ArrayList<Stub> dailySchedule){
            super(context, phone_item_schedule, dailySchedule);
            this.context = context;
            this.dailySchedule = dailySchedule;
        }

        @SuppressLint({"ViewHolder","InflateParams"})
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            int guidelineID;
            double stubTime = dailySchedule.get(position).getStartTime();

            TextView courseName = context.findViewById(R.id.coursename_field);
            TextView teacherName = context.findViewById(R.id.teachername_field);
            TextView room = context.findViewById(R.id.classname_field);
            TextView startTime = context.findViewById(R.id.startTime);
            TextView endTime = context.findViewById(R.id.endTime);

            courseName.setText(dailySchedule.get(position).getCourseName());
            teacherName.setText(dailySchedule.get(position).getTeacherName());
            room.setText(dailySchedule.get(position).getRoom());
            startTime.setText(String.valueOf(dailySchedule.get(position).getStartTime()));
            endTime.setText(String.valueOf(dailySchedule.get(position).getEndTime()));

            ConstraintLayout cLayout = context.findViewById(R.id.stubBackground);
            cLayout.setBackgroundColor(generatePastelColor());


            guidelineID = R.id.guideline0;
            if (stubTime == 11.00)
                guidelineID = R.id.guideline22;
            else if (stubTime == 14.00)
                guidelineID = R.id.guideline55;
            else if (stubTime == 16.30)
                guidelineID = R.id.guideline77;
            ConstraintSet cSet = new ConstraintSet();
            cSet.connect(R.id.stubBackground, ConstraintSet.TOP, guidelineID, ConstraintSet.BOTTOM);
            cLayout.setConstraintSet(cSet);

            View itemView = context.getLayoutInflater().inflate(
                    phone_item_dailystub, null,true);

            return itemView;
        }
    }


    public int generatePastelColor() {
        final int baseColor = Color.WHITE;

        final int baseRed = Color.red(baseColor);
        final int baseGreen = Color.green(baseColor);
        final int baseBlue = Color.blue(baseColor);

        final int red = (baseRed + colorGen.nextInt(256)) / 2;
        final int green = (baseGreen + colorGen.nextInt(256)) / 2;
        final int blue = (baseBlue + colorGen.nextInt(256)) / 2;

        return Color.rgb(red, green, blue);
    }
}