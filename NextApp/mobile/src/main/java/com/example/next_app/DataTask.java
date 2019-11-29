package com.example.next_app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

class DataTask  extends AsyncTask<Node, Void, Void> {

    private final String[] contents;
    Context c;
    private GoogleApiClient gAPI;

    public DataTask (Context c, String [] contents,  GoogleApiClient gAPI) {
        this.c = c;
        this.contents = contents;
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
