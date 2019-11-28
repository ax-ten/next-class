package com.example.next_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.sip.SipAudioCall;
import android.net.sip.SipSession;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MyListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public GoogleApiClient googleApiClient;
    private MyListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();
        googleApiClient.connect();
        setContentView(R.layout.activity_main);
    }


    public GoogleApiClient getGAPI(){
        return googleApiClient;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        String [] myData = new String[]{"data1", "data2", "data3"};
        new DataTask (this, myData, listener, googleApiClient).execute();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void callback(View view, String result) {
        //TODO
    }
}

class DataTask  extends AsyncTask<Node, Void, Void> {

    private final String[] contents;
    private MyListener mylistener;
    Context c;
    private GoogleApiClient gAPI;

    public DataTask (Context c, String [] contents, MyListener myListener, GoogleApiClient gAPI) {
        this.c = c;
        this.contents = contents;
        this.mylistener = myListener;
        this.gAPI = gAPI;
    }

    @Override
    protected Void doInBackground(Node... nodes) {

        PutDataMapRequest dataMap = PutDataMapRequest.create ("/myapp/myevent");
        dataMap.getDataMap().putStringArray("contents", contents);

        PutDataRequest request = dataMap.asPutDataRequest();

        DataApi.DataItemResult dataItemResult = Wearable.DataApi
                .putDataItem(gAPI, request).await();


        Log.d ("[DEBUG] SendDataCoolTask - doInBackground", "/myapp/myevent status:" + getStatus().toString());
        return null;
    }
}
