package com.example.next_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.ListFragment;

import com.poliba.mylibrary.Schedule;

import java.io.File;
import java.util.Objects;

import static com.example.next_app.R.layout.phone_item_schedule;

public class Phone_Fragment_ScheduleList extends ListFragment{
    private File folder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        String path = Objects.requireNonNull(getContext()).getFilesDir() + "next";
        if (folder == null)
            folder = new File(path);
        return inflater.inflate(R.layout.phone_fragment_schedulelist, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        String[] filenames;
        filenames = getFilenames();
        if (filenames != null) {
            CustomListAdapter cListAdapter = new CustomListAdapter(
                    getActivity(), getFilenames());
            setListAdapter(cListAdapter);
        }
    }

    private String[] getFilenames(){
        File[] files = folder.listFiles();
        if (files == null)
            return null;

        String[] filenames = new String[files.length];
        for (int i=0; i< files.length; i++){
            filenames[i] = files[i].getName();
        }

        return filenames;
    }

//TODO cambia in recyclerView
    class CustomListAdapter extends ArrayAdapter {
        private Activity context;
        private String[] nameArray;

        CustomListAdapter(Activity context, String[] nameArray){
            super(context, phone_item_schedule, nameArray);
            this.context=context;
            this.nameArray = nameArray;
        }

        @SuppressLint({"ViewHolder","InflateParams"})
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            TextView scheduleNameField;
            final ImageButton optionsButton;
            final Schedule schedule = new Schedule(Objects.requireNonNull(folder.listFiles())[position]);

            LayoutInflater inflater= context.getLayoutInflater();
              View itemView=inflater.inflate(phone_item_schedule, null,true);

            scheduleNameField = itemView.findViewById(R.id.filename);
            scheduleNameField . setText(nameArray[position]);
            scheduleNameField . setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Phone_Activity_Main) Objects.requireNonNull(getActivity())).setCurrentSchedule(schedule);
                    Toast.makeText(getContext(), "Set as current schedule", Toast.LENGTH_SHORT).show();
                }
            });

            optionsButton = itemView.findViewById(R.id.popupmenuOpener);
            PopupMenu popupMenu = new PopupMenu(getContext(), optionsButton );
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Toast.makeText(getContext(), "Yet to implement", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
            popupMenu.show();

            return itemView;
        }

    }
}


