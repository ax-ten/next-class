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

    //TAG useful for debugging Logs
    private static final String TAG = "Phone_Activity_Main";
    private InputStream inputStream;
    private ArrayList<Stub> stubList;
    private ArrayList<Stub> dailyStubList;
    private int current_file_id = R.raw.schedule_stubs;
    protected Handler messageHandler;
    String path = "/my_path";


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
        new NewThread(path,message).start();
    }

    class NewThread extends Thread{
        String path;
        String message;

        NewThread(String p, String m){
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
                        Integer result = Tasks.await(sendMessageTask);
                        messageText("I sent a message to the wearable");
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


    /**
    //NOTIFICATIONS
    class SendActivityPhoneMessage extends Thread {
        String path;
        String message;

        // Constructor to send a message to the data layer
        SendActivityPhoneMessage(String p, String msg) {
            path = p;
            message = msg;
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
            List<Node> nodeList = nodes.getNodes();
            for (Node node:nodeList){
                Log.v(TAG, "Activity Node is : "+node.getId()+ " - " + node.getDisplayName());
                MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(), path, message.getBytes()).await();
                if (result.getStatus().isSuccess()) {
                    Log.v(TAG, "Activity Message: {" + message + "} sent to: " + node.getDisplayName());
                }
                else {
                    // Log an error
                    Log.v(TAG, "ERROR: failed to send Activity Message");
                }
            }
        }
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

    private NotificationChannel createChannel(NotificationManager nManager) {
        String id = "my_channel_01";
        CharSequence name = "channel-name";
        int importance = nManager.IMPORTANCE_DEFAULT;

        NotificationChannel nChannel = new NotificationChannel(id, name, importance);

        String description = "description";
        nChannel.setDescription(description);
        nChannel.enableLights(true);
        nChannel.enableVibration(true);

        nManager.createNotificationChannel(nChannel);

        return nChannel;
    }
    @Override protected void onStart() {
        super.onStart();
        //Log.v(TAG, "onStart called");
    }
    @Override public void onConnectionSuspended(int cause)
    {Log.v(TAG,"onConnectionSuspended called");}
    @Override public void onConnectionFailed(ConnectionResult connectionResult)
    {Log.v(TAG,"onConnectionFailed called");}
    @Override public void onConnected(Bundle connectionHint)
    {Log.v(TAG,"onConnected called");}

    public void configStart(View view){new Phone_Activity_Main.SendActivityPhoneMessage(CONFIG_START,"").start();}
    public void configStop(View view){new Phone_Activity_Main.SendActivityPhoneMessage(CONFIG_STOP,"").start();}

    */


    //BUTTONS
    public void onClick_addNew(View view){
        //todo
        Log.v(TAG,"add new");
        Toast.makeText(this, "Yet to implement", Toast.LENGTH_SHORT).show();
    }

    public void onClick_settings(View view){
        Intent intent = new Intent (this, Phone_Activity_Settings.class);
        startActivity(intent);
    }

    private ArrayList<Stub> updateDailyStubList(){
        if (dailyStubList == null)
            dailyStubList = new ArrayList<>();
        else
            dailyStubList.clear();

        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        for (Stub stub : stubList){
            if (stub.getDay() == day)
                dailyStubList.add(stub);
        }

        return dailyStubList;
    }

    private ArrayList<Stub> updateStubList(){
        Log.v(TAG, "caricamento file");
        inputStream = this.getResources().openRawResource(current_file_id);
        Log.v(TAG, "parse iniziato");
        stubList = parseXML(inputStream);
        Log.v(TAG, "parse terminato");
        return stubList;
    }

    //PARSING
    public ArrayList<Stub> parseXML(InputStream istream) {
        ArrayList<Stub> schedule = new ArrayList<>();
        XmlPullParserFactory factory;
        String text = "";
        Stub stub= new Stub();
        XmlPullParser parser;
        int eventType;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            parser = factory.newPullParser();
            parser.setInput(istream, null);

            eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("course")) {
                            // create a new instance of Stub
                            stub = new Stub();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("course")) {
                            // add employee object to list
                            schedule.add(stub);

                        } else if (tagname.equalsIgnoreCase("teacher")) {
                            stub.setTeacherName(text);

                        } else if (tagname.equalsIgnoreCase("name")) {
                            stub.setCourseName(text);

                        } else if (tagname.equalsIgnoreCase("day")) {
                            stub.setDay(Integer.parseInt(text));
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return schedule;
    }
}
