package com.example.next_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import com.poliba.mylibrary.Stub;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Wearable_Activity_Main extends FragmentActivity
        implements Wearable_Fragment_Stub.OnFragmentInteractionListener {


    protected Handler refreshScheduleMessageHandler;
    final String attendancePath = "/attendance";
    final String refreshSchedulePath = "/refreshSchedule";
    String TAG = "Wearable_device";
    Stub currentStub;
    Stub nextStub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wearable_activity_main);

        //test values
        nextStub    = new Stub(2,2,"2","2","2",2.2,2.2);
        currentStub = new Stub(1,1,"1","1","1",1.1,1.1);

        refreshScheduleMessageHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                final String refreshPath = "/refreshSchedule";
                Bundle stuff = msg.getData();
                sendCommunication(refreshPath, stuff.getString("update"));
                return true;
            }
        });

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment,  Wearable_Fragment_Stub.newInstance(currentStub));
        fragmentTransaction.commit();

        setBroadcasters();
        sendCommunication(attendancePath, "message1");
        sendCommunication(refreshSchedulePath, "message2");
    }

    //LAYOUT
    public void onClick_next(View view){
        sendCommunication(refreshSchedulePath, "update");
        notify(nextStub);
    }


    //COMMUNICATION
    public void sendCommunication(String path, String message){
        new MessageSenderThread(path, message).start();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private class ScheduleSyncReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String toast = "i received a sync request from the Handheld";
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();

            currentStub = nextStub;
           // currentStub = getStubFromBroadcaster()
        }
    }

    private void setBroadcasters(){
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new ScheduleSyncReceiver(),
                new IntentFilter(Intent.ACTION_SYNC)
        );
    }

    //NOTIFICHE
    private NotificationChannel createChannel(NotificationManager nManager){
        String id ="my_channel_01";
        CharSequence name =  "channel-name";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel nChannel = new NotificationChannel(id, name,importance);

        String description = "description";
        nChannel.setDescription(description);
        nChannel.enableLights(true);
        nChannel.enableVibration(true);

        nManager.createNotificationChannel(nChannel);

        return nChannel;
    }
    public void notify(Stub nextStub){
        NotificationManager mNotificationManager = (
                NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        assert mNotificationManager != null;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this, createChannel(mNotificationManager).getId());
        builder.setContentTitle(nextStub.getCourseName());
        builder.setContentText("Room: "+nextStub.getRoom() +"\n"+nextStub.getStartTime()+"0 - "+nextStub.getEndTime()+"0");
        builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);

        Notification notification = builder.build();

        NotificationManagerCompat nmc = NotificationManagerCompat.from(this);
        nmc.notify(1,notification);
    }


    class MessageSenderThread extends Thread{
        String path;
        String message;

        MessageSenderThread(String p, String m){
            path=p;
            message=m;
        }
        public void run(){
            Task<List<com.google.android.gms.wearable.Node>> nodeListTask =
                    Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();
            try{
                List<com.google.android.gms.wearable.Node> nodes = Tasks.await(nodeListTask);
                for(Node node:nodes){
                    Task<Integer> sendMessageTask=
                            Wearable.getMessageClient(Wearable_Activity_Main.this)
                                    .sendMessage(node.getId(), path, message.getBytes());
                    try {
                        Integer result = Tasks.await(sendMessageTask);
                        Log.v(TAG, String.valueOf(result));
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
