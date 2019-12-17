package com.example.next_app;

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

        ImageButton mapButton = container.findViewById(R.id.button_map);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Wearable_Activity_Map.class));
            }
        });

        ImageButton teacherButton = container.findViewById(R.id.button_teacher);
        teacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Wearable_Activity_Teacher.class));
            }
        });

        TextView courseView = container.findViewById(R.id.course_text);
        courseView.setText(course);
        TextView classroomView = container.findViewById(R.id.classroom_text);
        classroomView.setText(classroom);
        TextView teacherView = container.findViewById(R.id.teacher_text);
        teacherView.setText(teacher);
        TextView startTimeView = container.findViewById(R.id.timeStart_text);
        startTimeView.setText(startTime);
        TextView endTimeView = container.findViewById(R.id.timeEnd_text);
        endTimeView.setText(endTime);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wearable_fragment_stub, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
