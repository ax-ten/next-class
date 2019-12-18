package com.example.next_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.poliba.mylibrary.Schedule;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Phone_Activity_Main extends AppCompatActivity{

    //TODO richiedi permessi di storage SE non sono già concessi
    Schedule currentSchedule;
    final int layoutId = R.layout.phone_activity_main;
    protected Handler attendanceMessageHandler;
    protected Handler refreshScheduleMessageHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layoutId);
        Objects.requireNonNull(this.getSupportActionBar()).hide();

        attendanceMessageHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Bundle stuff = msg.getData();
                final String attendancePath = "/attendance";
                messageText(attendancePath, stuff.getString("messageText"));
                return true;
            }
        });

        refreshScheduleMessageHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Bundle stuff = msg.getData();
                final String refreshPath = "/refreshSchedule";
                messageText(refreshPath, stuff.getString("messageText"));
                return true;
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(
                new AttendanceReceiver(),
                new IntentFilter(Intent.ACTION_ATTACH_DATA)
        );

        LocalBroadcastManager.getInstance(this).registerReceiver(
                new ScheduleSyncReceiver(),
                new IntentFilter(Intent.ACTION_SYNC)
        );
    }

    public class AttendanceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO : Implementa azione da compiere una volta ricevuto aggiornamento di attendance
            String toast = "i received a message from the wearable";
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
        }
    }

    public class ScheduleSyncReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO : Implementa azione da compiere una volta ricevuta la richiesta di sync
            String toast = "i received a sync request from the wearable";
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
        }
    }

    public void messageText(String path, String message){
        new MessageThread(path,message).start();
    }

    private void setBroadcasters(){
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new AttendanceReceiver(),
                new IntentFilter(Intent.ACTION_ATTACH_DATA)
        );

        LocalBroadcastManager.getInstance(this).registerReceiver(
                new ScheduleSyncReceiver(),
                new IntentFilter(Intent.ACTION_SYNC)
        );
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
                        messageText(path,message);
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
        startActivity(new Intent(this, Phone_Activity_AddNewSchedule.class));
    }
    public void onClick_settings(View view){
        startActivity(new Intent (this, Phone_Activity_Settings.class));
    }
    public void onClick_sync(View view){
        String path = "/refreshSchedule";
        String msg = "";
        messageText(path, msg);
        Toast.makeText(this,"Message sent",Toast.LENGTH_SHORT).show();
    }

    public void setCurrentSchedule(Schedule schedule) {
        this.currentSchedule = schedule;
        SharedPreferences preferences = getSharedPreferences("currentSchedule", Context.MODE_PRIVATE);
        preferences.edit().putString("currentSchedule",currentSchedule.name);
    }
}
