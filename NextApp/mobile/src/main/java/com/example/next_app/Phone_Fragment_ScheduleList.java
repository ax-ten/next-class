package com.example.next_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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

                    //todo cambiare il text field di file corrente in preferences
                }
            });

            optionsButton = itemView.findViewById(R.id.popupmenuOpener);

            optionsButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    final PopupMenu popupMenu = new PopupMenu(getContext(), optionsButton );
                    popupMenu.getMenuInflater().inflate(R.menu.schedule_popup_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.view:
                                    view(schedule);
                                case R.id.edit:
                                    edit(schedule);
                                case R.id.rename:
                                    rename(schedule);
                                case R.id.delete:
                                    delete(schedule);
                                case R.id.refresh:
                                    refresh(schedule);
                                case R.id.share:
                                    share(schedule);
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            return itemView;
        }
    }

    //MENU OPTIONS
    Intent intent;
    private void view(Schedule schedule){
        intent = new Intent(getContext(), Phone_Activity_DailySchedulePager.class);
        intent.putExtra("SCHEDULE", schedule.getSchedule());
        startActivity(intent);
    }
    private void edit(Schedule schedule){
        final String toast = "Yet to implement";
        Toast.makeText(getContext(), toast , Toast.LENGTH_SHORT).show();
        //TODO : Implement edit
    }
    private void rename(Schedule schedule){
        final String toast = "Yet to implement";
        Toast.makeText(getContext(), toast , Toast.LENGTH_SHORT).show();
        //TODO : Implement rename
    }
    private void delete(Schedule schedule){
        final String toast = "Yet to implement";
        Toast.makeText(getContext(), toast , Toast.LENGTH_SHORT).show();
        //TODO : Implement delete
    }
    private void refresh(Schedule schedule){
        final String toast = "Yet to implement";
        Toast.makeText(getContext(), toast , Toast.LENGTH_SHORT).show();
        //TODO : Implement refresh
    }
    private void share(Schedule schedule){
        final String toast = "Yet to implement";
        Toast.makeText(getContext(), toast , Toast.LENGTH_SHORT).show();
        //TODO : Implement share
    }
}


