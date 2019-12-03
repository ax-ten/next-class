package com.example.next_app;

import android.app.Notification;
import android.os.Bundle;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.content.Context;
import android.support.v4.os.IResultReceiver;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.util.LinkedList;


public class Wearable_MainActivity extends WearableActivity implements MyListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private TextView mTextView;
    private GoogleApiClient googleApiClient;
    private MyListener myListener;
    private LinkedList<IResultReceiver.Stub> stubList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wearable_main_activity);

        mTextView = findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();
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

    public void getNextStub(View view){
        //todo
        //todo getstub
    }

    public void open_menu(View view){
        //todo
    }

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

    @Override
    public void onConnected(Bundle bundle) {

        Wearable.DataApi.addListener(googleApiClient, (DataApi.DataListener) this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void onDataChanged(DataEventBuffer dataEvents) {

        for (DataEvent event: dataEvents) {

            Log.d("[DEBUG] DeviceService - onDataChanged",
                    "Event received: " + event.getDataItem().getUri());

            String eventUri = event.getDataItem().getUri().toString();

            if (eventUri.contains ("/myapp/myevent")) {

                DataMapItem dataItem = DataMapItem.fromDataItem (event.getDataItem());
                String[] data = dataItem.getDataMap().getStringArray("contents");

                Log.d("[DEBUG] DeviceService - onDataChanged", "Sending timeline to the listener");

                myListener.onDataReceived(data);
            }
        }
    }

    @Override
    public void callback(View view, String result) {
    }

    @Override
    public void onDataReceived(String[] data) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
