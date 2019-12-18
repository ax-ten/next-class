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
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.poliba.mylibrary.Schedule;
import com.poliba.mylibrary.Stub;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class Wearable_Activity_Main extends FragmentActivity
        implements Wearable_Fragment_Stub.OnFragmentInteractionListener {


    //TODO Implementare swipe per passare tra le attività
    //TODO passare tramite intento l'aula che si vuole visualizzare sulla mappa
    //Todo passare tramite intent il professore di cui si vuole visualizzare il profilo
    //todo trovare un modo per accedere a tutti i profili dei professori e listarne almeno le mail
    private Schedule dailySchedule;
    String path = "/my_path";
    String TAG = "Wearable_device";
    Stub currentStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new Receiver(),
                new IntentFilter(Intent.ACTION_SEND)
        );

        currentStub = new Stub(1,1,"1","1","1",1.1,1.1);

        //TODO : ask daily schedule to phone
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Wearable_Fragment_Stub fragment = Wearable_Fragment_Stub.newInstance(currentStub);
        fragmentTransaction.add(R.id.fragment, fragment);
        fragmentTransaction.commit();

        setContentView(R.layout.wearable_activity_main);

    }


    //COMMUNICATION
    public void sendCommunication(){
        new MessageSenderThread(path, "I just sent the handheld a message").start();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public class Receiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG, "just received a message");
        }
    }


    //BUTTONS
    public void nextStubIntent(View v){

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
    public void getNotified(View view){
        NotificationManager mNotificationManager = (
                NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, createChannel(mNotificationManager).getId());
        builder.setContentTitle("titolo della notifica");
        builder.setContentText("contenuto della notifica");
        builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);

        // Intent intent =  new Intent(this, AttendanceService.class );
        // PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        // builder.addAction(R.drawable.ic_check, "Present", pi);

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
                            Wearable.getMessageClient(Wearable_Activity_Main.this).sendMessage(
                                    node.getId(), path, message.getBytes()
                            );
                    try {
                        Integer result = Tasks.await(sendMessageTask);
                        Log.v(TAG, String.valueOf(result));
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
