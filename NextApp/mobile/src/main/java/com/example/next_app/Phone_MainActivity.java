package com.example.next_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Phone_MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "Phone_MainActivity";
    public static final String CONFIG_START = "config/start";
    public static final String CONFIG_STOP= "config/stop";
    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_main_activity);

        /**
         if(null == mGoogleApiClient) {
         mGoogleApiClient = new GoogleApiClient.Builder(this)
         .addApi(Wearable.API)
         .addConnectionCallbacks(this)
         .addOnConnectionFailedListener(this)
         .build();
         Log.v(TAG, "GoogleApiClient created");
         }

         if(!mGoogleApiClient.isConnected()){
         mGoogleApiClient.connect();
         Log.v(TAG, "Connecting to GoogleApiClient..");
         }
         */

        //startService(new Intent(this, PhoneService.class));
        //todo riattivare lo start del service

        //GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        //builder.addApi(Wearable.API);

        //PARSE
        AssetManager am = this.getAssets();
        InputStream istream = null;
        try {
            Log.v(TAG, "caricamento file");
            istream = am.open("schedule_stubs.xml");
            Log.v(TAG, "parse iniziato");
            LinkedList<Stub> stubList = parseXML(istream);
            Log.v(TAG, "parse terminato");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //NOTIFICATIONS
    class SendActivityPhoneMessage extends Thread {
        String path;
        String message;

        // Constructor to send a message to the data layer
        SendActivityPhoneMessage(String p, String msg) {
            path = p;
            message = msg;
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
            List<Node> nodeList = nodes.getNodes();
            for (Node node:nodeList){
                Log.v(TAG, "Activity Node is : "+node.getId()+ " - " + node.getDisplayName());
                MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(), path, message.getBytes()).await();
                if (result.getStatus().isSuccess()) {
                    Log.v(TAG, "Activity Message: {" + message + "} sent to: " + node.getDisplayName());
                }
                else {
                    // Log an error
                    Log.v(TAG, "ERROR: failed to send Activity Message");
                }
            }
        }
    }

    public void notifyme(View view){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, createChannel(mNotificationManager).getId());
        builder.setContentTitle("titolo della notifica");
        builder.setContentText("contenuto della notifica");
        builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);

        Notification notification = builder.build();

        NotificationManagerCompat nmc = NotificationManagerCompat.from(this);
        nmc.notify(1,notification);
    }

    private NotificationChannel createChannel(NotificationManager nManager) {
        String id = "my_channel_01";
        CharSequence name = "channel-name";
        int importance = nManager.IMPORTANCE_DEFAULT;

        NotificationChannel nChannel = new NotificationChannel(id, name, importance);

        String description = "description";
        nChannel.setDescription(description);
        nChannel.enableLights(true);
        nChannel.enableVibration(true);

        nManager.createNotificationChannel(nChannel);

        return nChannel;
    }
    @Override protected void onStart() {
        super.onStart();
        //Log.v(TAG, "onStart called");
    }
    @Override public void onConnectionSuspended(int cause)
    {Log.v(TAG,"onConnectionSuspended called");}
    @Override public void onConnectionFailed(ConnectionResult connectionResult)
    {Log.v(TAG,"onConnectionFailed called");}
    @Override public void onConnected(Bundle connectionHint)
    {Log.v(TAG,"onConnected called");}

    //BUTTONS
    public void onClick_addNew(View view){
        Log.v(TAG,"addnew")
        //todo
    }

    public void onClick_settings(View view){
        //todo
    }

    public void onClick_parse(View view){
        AssetManager am = this.getAssets();
        InputStream istream = null;
        try {
            Log.v(TAG, "caricamento file");
            istream = am.open("schedule_stubs.xml");
            Log.v(TAG, "parse iniziato");
            LinkedList<Stub> stubList = parseXML(istream);
            Log.v(TAG, "parse terminato");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //PARSING
    public  LinkedList<Stub> parseXML(InputStream istream){
        LinkedList<Stub> stubList = new LinkedList<>();
        try{
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(istream);

            NodeList courses = doc.getElementsByTagName("class");
            for(int i =0;i<courses.getLength();i++){
                Element elm = (Element)courses.item(i);
                Stub stub = new Stub(
                        /**Integer.parseInt(getNodeValue("year",elm)),
                        Integer.parseInt(getNodeValue("day",elm)),
                        getNodeValue("name",elm),
                        getNodeValue("teacher",elm),
                        getNodeValue("room",elm),
                        Double.parseDouble(getNodeValue("startTime",elm)),
                        Double.parseDouble(getNodeValue("endTime",elm))
                         */
                        i,i,getNodeValue("name",elm),null, null,i,i
                );
                stubList.add(stub);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return stubList;
    }
    protected String getNodeValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        org.w3c.dom.Node node = nodeList.item(0);
        if(node!=null) {
            if (node.hasChildNodes()) {
                org.w3c.dom.Node child = node.getFirstChild();
                while (child != null) {
                    if (child.getNodeType() == org.w3c.dom.Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public void configStart(View view){new Phone_MainActivity.SendActivityPhoneMessage(CONFIG_START,"").start();}
    public void configStop(View view){new Phone_MainActivity.SendActivityPhoneMessage(CONFIG_STOP,"").start();}


}
