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

    public MessageService() {
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent){
        if (messageEvent.getPath().equals(attendancePath)){

            final String message = new String(messageEvent.getData());
            messageIntent= new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra(payloadName, message);

            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);


        } else if (messageEvent.getPath().equals(refreshPath)) {
            //TODO : implementare richiesta di aggiornamento

        } else{
            super.onMessageReceived(messageEvent);
        }
    }
}
