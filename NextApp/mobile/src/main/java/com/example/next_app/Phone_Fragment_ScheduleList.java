package com.example.next_app;

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

public class Phone_Fragment_ScheduleList extends ListFragment{

    //TODO deve accedere direttamente ad una cartella di file xml nel dispositivo e listare questi file
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       View view = inflater.inflate(R.layout.phone_fragment_schedulelist, container, false);
       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        CustomListAdapter cListAdapter = new CustomListAdapter(
                getActivity(),getResources().getStringArray(R.array.filenamelist));

        setListAdapter(cListAdapter);
    }
}

class CustomListAdapter extends ArrayAdapter {
    private Activity context;
    private String[] nameArray;

    public CustomListAdapter(Activity context, String[] nameArray){

        super(context,R.layout.phone_item_schedule, nameArray);
        this.context=context;
        this.nameArray = nameArray;
    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TextView scheduleNameField;
        ImageButton optionsButton;

        LayoutInflater inflater= context.getLayoutInflater();
        View itemView=inflater.inflate(R.layout.phone_item_schedule, null,true);

        scheduleNameField = itemView.findViewById(R.id.filename);
        scheduleNameField . setText(nameArray[position]);

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
