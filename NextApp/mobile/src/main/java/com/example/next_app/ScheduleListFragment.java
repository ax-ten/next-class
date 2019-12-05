package com.example.next_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class ScheduleListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       View view = inflater.inflate(R.layout.schedule_list_fragment, container, false);
       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        CustomListAdapter cListAdapter = new CustomListAdapter(
                getActivity(),getResources().getStringArray(R.array.filenamelist));

        setListAdapter(cListAdapter);



    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "Item click yet to implement", Toast.LENGTH_SHORT).show();
        String s = (String) parent.getItemAtPosition(position);
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        /**Button button = (Button) parent.getItemAtPosition(position);
        PopupMenu popupMenu = new PopupMenu(getContext(), button );
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });

        popupMenu.show();
         */
    }

}

class CustomListAdapter extends ArrayAdapter {
    //to reference the Activity
    private Activity context;

    //to store the list of countries
    private  String[] infoArray;

    private String[] nameArray;


    //CONSTRUCTOR
    public CustomListAdapter(Activity context, String[] nameArrayParam){

        super(context,R.layout.schedule_list_item , nameArrayParam);
        this.context=context;
        this.nameArray = nameArrayParam;
    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        View itemView=inflater.inflate(R.layout.schedule_list_item, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) itemView.findViewById(R.id.filename);

        Button button = (Button)
        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);

        return itemView;
    }

}
