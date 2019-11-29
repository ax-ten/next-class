package com.example.next_app;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhoneActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "PhoneActivity";
    public static final String CONFIG_START = "config/start";
    public static final String CONFIG_STOP= "config/stop";

    GoogleApiClient mGoogleApiClient;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);



        if(null == mGoogleApiClient) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Wearable.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            Log.v(TAG, "GoogleApiClient created");
        }

        if(!mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();
            Log.v(TAG, "Connecting to GoogleApiClient..");
        }

        startService(new Intent(this, PhoneService.class));
        setContentView(R.layout.activity_phone);
        try {
            readFeed(this.getResources().getXml(R.xml.schedule_stubs));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List readFeed(XmlResourceParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();
        int xmlTAG = parser.getEventType();

        while (xmlTAG != XmlPullParser.END_TAG){
            String name = parser.getName();
            Log.d(TAG, parser.getText() );
            parser.nextTag();
        }
        return entries;
    }

    @Override protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart called");
    }
    @Override public void onConnectionSuspended(int cause)                      {Log.v(TAG,"onConnectionSuspended called");}
    @Override public void onConnectionFailed(ConnectionResult connectionResult) {Log.v(TAG,"onConnectionFailed called");}
    @Override public void onConnected(Bundle connectionHint)                    {Log.v(TAG,"onConnected called");}

    public void configStart(View view){new SendActivityPhoneMessage(CONFIG_START,"").start();}
    public void configStop(View view){new SendActivityPhoneMessage(CONFIG_STOP,"").start();}

}
