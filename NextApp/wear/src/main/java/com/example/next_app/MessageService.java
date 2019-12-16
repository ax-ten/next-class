package com.example.next_app;

import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class MessageService extends WearableListenerService {
    final String refreshPath = "/refreshSchedule";

    public MessageService() {
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent){
        switch (messageEvent.getPath()){
            case refreshPath:
                //TODO : Implementare richiesta di aggiornamento Orario
                final String message = new String(messageEvent.getData());
                Intent messageIntent = new Intent();
                messageIntent.setAction(Intent.ACTION_SEND);
                messageIntent.putExtra("message", message);

                LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
                break;
            default:
                super.onMessageReceived(messageEvent);
        }
    }
}
