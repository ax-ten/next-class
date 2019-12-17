package com.example.next_app;

import android.content.Intent;

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
        switch (messageEvent.getPath()){
            case refreshPath:
                storedMessage = new String(messageEvent.getData());
                messageIntent = new Intent();
                messageIntent.setAction(Intent.ACTION_SYNC);
                messageIntent.putExtra(refreshPayloadName, storedMessage);

                LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
                break;
            default:
                super.onMessageReceived(messageEvent);
        }
    }
}
