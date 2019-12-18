package com.example.next_app;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class MessageService extends WearableListenerService {
    Intent messageIntent;
    String storedMessage;
    final String refreshPath = "/refreshSchedule";
    final String refreshPayloadName = "payload";

    public MessageService() {
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent){
        if (refreshPath.equals(messageEvent.getPath())) {
            storedMessage = new String(messageEvent.getData());
            messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SYNC);
            messageIntent.putExtra(refreshPayloadName, storedMessage);
            Toast.makeText(getApplicationContext(),"messaggio ricevuto!",Toast.LENGTH_SHORT);

            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }
}
