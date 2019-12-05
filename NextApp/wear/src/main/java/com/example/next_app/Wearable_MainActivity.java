package com.example.next_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.LinkedList;


import com.poliba.mylibrary.Stub;


public class Wearable_MainActivity extends WearableActivity {

    private TextView mTextView;
    private LinkedList<Stub> stubList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stubList = getStubList();

        Double d = 11.2;
        Stub stub = getcurrentStub(stubList, d);

        setTxtView(R.id.teacherText, stub.getTeacherName());
        setTxtView(R.id.classname, stub.getCourseName());
        setTxtView(R.id.classroom, stub.getRoom());
        setTxtView(R.id.timeStart_text,  Double.toString(stub.getStartTime()));
        setTxtView(R.id.timeEnd_text,  Double.toString(stub.getEndTime()) );

        setContentView(R.layout.wearable_main_activity);

    }

    private LinkedList<Stub> getStubList(){
        stubList.add(new Stub(1,1,"wow","wow","wowissimo",1.5,1.5));
        return new LinkedList<>();
    }

    //BUTTONS
    private void mapIntent(View v){

    }

    private void teacherIntent(View v){

    }

    private void nextIntent(View v){

    }

    private Stub getcurrentStub(LinkedList<Stub> stublist, double cTime){
        for(Stub stub: stublist){
            if (cTime > stub.getStartTime() && cTime < stub.getEndTime())
                return stub;
        }
        return null;
        //todo
    }



    public void getNextStub(View view){
        //todo
    }


    //NOTIFICHE
    private NotificationChannel createChannel(NotificationManager nManager){
        String id ="my_channel_01";
        CharSequence name =  "channel-name";
        int importance = nManager.IMPORTANCE_DEFAULT;

        NotificationChannel nChannel = new NotificationChannel(id, name,importance);

        String description = "description";
        nChannel.setDescription(description);
        nChannel.enableLights(true);
        nChannel.enableVibration(true);

        nManager.createNotificationChannel(nChannel);

        return nChannel;
    }
    public void notifyme(View view){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, createChannel(mNotificationManager).getId());
        builder.setContentTitle("titolo della notifica");
        builder.setContentText("contenuto della notifica");
        builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);

        Notification notification = builder.build();

        NotificationManagerCompat nmc = NotificationManagerCompat.from(this);
        nmc.notify(1,notification);
    }


    private void setTxtView(int campotisesto, String valore){
        mTextView = findViewById(campotisesto);
        mTextView.setText(valore);
    }
}
