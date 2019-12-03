package com.example.next_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

public class Phone_MainActivity extends AppCompatActivity
        //todo implementare librerie per Listener
        //implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
    {
    //TAG useful for debugging Logs
    private static final String TAG = "Phone_MainActivity";
    private InputStream inputStream;
    private LinkedList<Stub> stubList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_main_activity);
        this.getSupportActionBar().hide();

        //update stubList containing all stubs for the semester
        updateStubList();

        //update the LinearLayout containing all Schedules made
        updateScheduleList();

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

    public void configStart(View view){new Phone_MainActivity.SendActivityPhoneMessage(CONFIG_START,"").start();}
    public void configStop(View view){new Phone_MainActivity.SendActivityPhoneMessage(CONFIG_STOP,"").start();}

    */


    //BUTTONS
    public void onClick_addNew(View view){
        Log.v(TAG,"add new");
        //todo
    }

    public void onClick_settings(View view){
        Log.v(TAG,"settings");
        //todo
    }

    //Method called when clicked on button "parse"
    public void onClick_parse(View view){
        updateStubList();
    }


    //UPDATING METHODS
    private void updateScheduleList(){
        //linLayout is the layout where all the
        LinearLayout linLayout = findViewById(R.id.scheduleList);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view;
        for (Stub stub: stubList){
            view = inflater.inflate(R.layout.stub_view,linLayout,false);
            linLayout.addView(view);
        }
    }


    private void updateStubList(){
        Log.v(TAG, "caricamento file");
        inputStream = getStream();
        Log.v(TAG, "parse iniziato");
        stubList = parseXML(inputStream);
        Log.v(TAG, "parse terminato");
    }

    //PARSING
    public  LinkedList<Stub> parseXML(InputStream istream) {
        LinkedList<Stub> schedule = new LinkedList<>();
        XmlPullParserFactory factory;
        XmlPullParser parser;
        int eventType;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
            parser.setInput(istream, null);
            String text = "test";
            Stub stub= new Stub(1,1,null,null,null,1,1);

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

    //Returns the xml file as an InputStream
    public InputStream getStream(){
        return this.getResources().openRawResource(R.raw.schedule_stubs);
    }

}
