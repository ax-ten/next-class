package com.example.next_app;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Phone_Activity_AddNewSchedule extends AppCompatActivity {
    private String TAG = "testing";
    private String[] CdL; //TODO ottenerle da internet
    private String[] years; //TODO ottenerle da CdL
    //todo qui sarà effettuato il parse del pdf in xml
    //todo creare file xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_activity_addnewschedule);
    }

    private void createfile(){
        InputStream inputStream = getResources().openRawResource(R.raw.schedule_stubs);
        String pathName ;
        try {
            pathName = getApplicationContext().getFilesDir() + "next";
            File pathFile = new File(pathName);
            pathFile.mkdir();
            Log.v(TAG,"directory creata");
            File newfile = new File(pathName+File.separator+"filename"+ ".xml");
            Log.v(TAG, newfile.getAbsolutePath());
            newfile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(newfile);
            IOUtils.copyStream(inputStream,outputStream);
            inputStream.close();
            outputStream.close();
            Log.v(TAG,"file creato");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
