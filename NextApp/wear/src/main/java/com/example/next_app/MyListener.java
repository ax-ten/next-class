package com.example.next_app;

import android.view.View;

interface MyListener {
    void callback(View view, String result);
    void onDataReceived(String[] data);
}
