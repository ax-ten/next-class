package com.example.next_app;

import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class MessageService extends WearableListenerService {
    Intent messageIntent;

    final String attendancePath = "/attendance";
    final String payloadName = "attendance";

    final String refreshPath = "/refreshSchedule";
    String storedMessage;
    public MessageService() {
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent){
        switch (messageEvent.getPath()){
            case attendancePath:
                storedMessage = new String(messageEvent.getData());
                messageIntent = new Intent();
                messageIntent . setAction(Intent.ACTION_ATTACH_DATA);
                messageIntent . putExtra(payloadName, storedMessage);

                LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);

            case refreshPath:
                storedMessage = new String(messageEvent.getData());
                messageIntent = new Intent();
                messageIntent . setAction(Intent.ACTION_SYNC);
                messageIntent . putExtra(payloadName, storedMessage);

                LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);


            default:
                super.onMessageReceived(messageEvent);

        }
    }
}
