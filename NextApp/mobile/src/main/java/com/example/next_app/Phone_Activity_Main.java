package com.example.next_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.poliba.mylibrary.Stub;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Phone_Activity_Main extends AppCompatActivity{

    //TODO Spostare il parsing del file in scheduleviewer
    // questo file deve più che altro far vedere la lista di schedule sul dispositivo

    //TAG useful for debugging Logs
    private static final String TAG = "Phone_Activity_Main";
    private final String path = "next";
    Schedule currentSchedule;
    
    protected Handler messageHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.phone_activity_main);
        this.getSupportActionBar().hide();

        stubList = updateStubList();
        dailyStubList = updateDailyStubList();

        messageHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Bundle stuff = msg.getData();
                messageText(stuff.getString("messageText"));
                return true;
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(
                new Receiver(),
                new IntentFilter(Intent.ACTION_SEND)
        );
    }

    

    //COMMUNICATION
    public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = "i received a message from the wearable";
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public void messageText(String message){
        new MessageThread(path,message).start();
    }

    class MessageThread extends Thread{
        String path;
        String message;

        MessageThread(String p, String m){
            path = p;
            message = m;
        }

        public void run(){
            Task<List<Node>> wearableList =
                    Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();
            try{
                List<Node> nodes = Tasks.await(wearableList);
                for (Node node : nodes){
                    Task<Integer> sendMessageTask =
                            Wearable.getMessageClient(Phone_Activity_Main.this).sendMessage(node.getId(), path, message.getBytes());

                    try{
                        Tasks.await(sendMessageTask);
                        messageText("I sent a message to the wearable");
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    //BUTTONS
    public void onClick_addNew(View view){
        //todo
        Log.v(TAG,"add new");
        Toast.makeText(this, "Yet to implement", Toast.LENGTH_SHORT).show();
    }
    public void onClick_settings(View view){
        startActivity(new Intent (this, Phone_Activity_Settings.class));
    }
    public void setCurrentSchedule(Schedule schedule) {
        this.currentSchedule = schedule;
    }
}
