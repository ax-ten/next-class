package com.example.next_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.MotionEventCompat;

import com.poliba.mylibrary.Stub;

import java.util.ArrayList;


public class Wearable_MainActivity extends WearableActivity {

    private ArrayList<Stub> stubList;
    private int tempStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stubList = new ArrayList<>();

        Double d = 11.2;
        Stub stub = getcurrentStub(stubList, d);

        setTxtView(R.id.teacherText, stub.getTeacherName());
        setTxtView(R.id.classname, stub.getCourseName());
        setTxtView(R.id.classroom, stub.getRoom());
        setTxtView(R.id.timeStart_text,  Double.toString(stub.getStartTime()));
        setTxtView(R.id.timeEnd_text,  Double.toString(stub.getEndTime()) );

        setContentView(R.layout.wearable_main_activity);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        String DEBUG_TAG = "testing_wearable";
        switch(action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default:
                return super.onTouchEvent(event);
        }

    private LinkedList<Stub> getStubList(){
        stubList.add(new Stub(1,1,"wow","wow","wowissimo",1.5,1.5));
        return new LinkedList<>();
    }

    //BUTTONS
    private void mapIntent(View v){
        Intent intent = new Intent (this, Wearable_mapActivity.class);
        startActivity(intent);
    }

    private void teacherIntent(View v){

    }

    private void nextIntent(View v){

    }

    private Integer getCurrentStubIndex(ArrayList<Stub> stublist, double cTime){
        for(int i=0; i<stublist.size();i++){
            if (cTime > stubList.get(i).getStartTime() && cTime < stubList.get(i).getEndTime())
                return i;
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
    public void teacherIntent(View view){
        NotificationManager mNotificationManager = (
                NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        Intent intent =  new Intent(this, AttendanceService.class );
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, createChannel(mNotificationManager).getId());
        builder.setContentTitle("titolo della notifica");
        builder.setContentText("contenuto della notifica");
        builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);
        builder.addAction(R.drawable.ic_check, "Present", pi);

        Notification notification = builder.build();

        NotificationManagerCompat nmc = NotificationManagerCompat.from(this);
        nmc.notify(1,notification);
    }


    private void setTxtView(int campotisesto, String valore){
        mTextView = findViewById(campotisesto);
        mTextView.setText(valore);
    }
}
