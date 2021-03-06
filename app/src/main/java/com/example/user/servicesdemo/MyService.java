package com.example.user.servicesdemo;

import android.app.Service;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
import android.widget.Toast;

import java.net.URL;
import java.net.MalformedURLException;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by User on 7/15/2015.
 */
public class MyService extends Service {

    private int  counter = 0;
    private static final int UPDATDE_INTERVAL = 1000;
    private Timer timer = new Timer();

    URL[] urls;
    private final IBinder binder = new MyBinder();

    public class MyBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }
    @Override
    public IBinder onBind(Intent arg0){
        return binder;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //doRepeatedly();

        new DownloadTask().execute(urls);
        return START_STICKY;

    }

    private void doRepeatedly (){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Log.d("TimerTask","Counter"+counter);
            }
        };
        timer.scheduleAtFixedRate(task,0,UPDATDE_INTERVAL);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

        if(timer != null){
            timer.cancel();
        }
        Toast.makeText(this,"Service Destroyed",Toast.LENGTH_LONG).show();
    }

    public class DownloadTask extends AsyncTask<URL,Integer,String> {
        @Override
        protected String doInBackground(URL... params){
            for(int i = 1; i<= 10; i++){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                publishProgress(i*10);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);
            Log.d("DownloadTask",values[0]+"% downloaded");
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Toast.makeText(getBaseContext(),"Download Complete",Toast.LENGTH_LONG).show();

            stopSelf();
        }

    }
}
