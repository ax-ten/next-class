package com.example.next_app;

//TODO ci sono 2 messaggi da interpretare
// -utente presente
// -richiesta quotidiana dell'orario

import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class MessageService extends WearableListenerService {
    String path = "/my_path";
    String payloadName = "stubArrayList";

    public MessageService() {
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent){
        if (messageEvent.getPath().equals(path)){
            final String message = new String(messageEvent.getData());
            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra(payloadName, message);

            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }
}
