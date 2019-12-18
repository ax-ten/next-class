package com.example.next_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Phone_Activity_AddNewSchedule extends AppCompatActivity {
    private String TAG = "testing";
    private String[] CdL; //TODO ottenerle da internet
    private int[] years; //TODO ottenerle da CdL
    private String pathName;
    private InputStream tempResource;

    //TODO implementare priorità di inserimento (cdl->anni)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pathName = getApplicationContext().getFilesDir() + "next";
        tempResource = getResources().openRawResource(R.raw.schedule_stubs);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_activity_addnewschedule);
        final TextView textField = findViewById(R.id.nameFileField);

        Button button = findViewById(R.id.createButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textField.getText() != null) {
                    String fileName = (String) textField.getText();
                    createFile(tempResource, fileName);
                    try {
                        tempResource.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "File created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), Phone_Activity_Main.class);
                    intent.putExtra("REFRESH", true);
                    //TODO payload put in intent should refresh scheduleList
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Insert file name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private File createFile(InputStream iStream, String fileName){
        File pathFile = new File(pathName);
        FileOutputStream oStream;
        fileName = pathName + File.separator + fileName + ".xml";
        File newFile = null;
        
        if (!pathFile.isDirectory()){
            pathFile.mkdir();
            Log.v(TAG,"directory creata");
        }
        
        try {
            newFile = new File(fileName);
            if(!newFile.isFile()) {
                newFile.createNewFile();
                Log.v(TAG, "file creato");

                oStream = new FileOutputStream(newFile);
                IOUtils.copyStream(iStream, oStream);

                oStream.flush();
                oStream.close();
            } else {
                Toast.makeText(getApplicationContext(), "File already existing, try using another name", Toast.LENGTH_SHORT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFile;
    }

    class Downloader extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String[] f_url) {
            try {
                URL url = new URL(f_url[0]);
                url.openConnection().connect();

                InputStream input = new BufferedInputStream(url.openStream(),8192);
                //TODO salvare input

                input.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


    }
}
