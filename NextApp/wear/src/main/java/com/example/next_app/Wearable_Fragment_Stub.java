package com.example.next_app;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.poliba.mylibrary.Stub;

public class Wearable_Fragment_Stub extends Fragment {
    private String teacher;
    private String course;
    private String classroom;
    private String startTime;
    private String endTime;

    private OnFragmentInteractionListener mListener;

    public Wearable_Fragment_Stub() {
        // Required empty public constructor
    }

    public static Wearable_Fragment_Stub newInstance(Stub stub) {
        Wearable_Fragment_Stub fragment = new Wearable_Fragment_Stub();
        Bundle args = new Bundle();

        args.putString("teacher", stub.getTeacherName());
        args.putString("course", stub.getCourseName());
        args.putString("classroom", stub.getRoom());
        args.putString("start time", String.valueOf(stub.getStartTime()));
        args.putString("end time", String.valueOf(stub.getEndTime()));
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            teacher = getArguments().getString("teacher");
            course = getArguments().getString("course");
            classroom = getArguments().getString("classroom");
            startTime = getArguments().getString("start time");
            endTime = getArguments().getString("end time");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wearable_fragment_stub, container, false);

        ImageButton mapButton = view.findViewById(R.id.button_map);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Wearable_Activity_Map.class);
                startActivity(intent);
            }
        });

        ImageButton teacherButton = view.findViewById(R.id.button_teacher);
        teacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Wearable_Activity_Teacher.class);
                startActivity(intent);
            }
        });


        TextView courseView = view.findViewById(R.id.course_text);
        courseView.setText(course);
        TextView classroomView = view.findViewById(R.id.classroom_text);
        classroomView.setText(classroom);
        TextView teacherView = view.findViewById(R.id.teacher_text);
        teacherView.setText(teacher);
        TextView startTimeView = view.findViewById(R.id.timeStart_text);
        startTimeView.setText(startTime);
        TextView endTimeView = view.findViewById(R.id.timeEnd_text);
        endTimeView.setText(endTime);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
