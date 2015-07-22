package com.example.user.servicesdemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by User on 7/15/2015.
 */
public class MyIntentService extends IntentService {

    public MyIntentService(){
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        try {
            downloadFile(new URL("http://www.example.com/downloads/somefile.pdf"));
            Log.d("MyIntentService", "Download completed");

            Intent broadcastIntent = new Intent("FILE_DOWNLOAD_ACTION");
            getBaseContext().sendBroadcast(broadcastIntent);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    private void downloadFile(URL url){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
