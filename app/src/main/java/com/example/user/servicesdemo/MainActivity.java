package com.example.user.servicesdemo;

import android.app.Activity;
import java.net.MalformedURLException;
import java.net.URL;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.widget.Toast;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

    IntentFilter intentFilter;

    MyService serviceBinder;
    Intent intent;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceBinder = ((MyService.MyBinder)service).getService();

            try{
                URL[] urls = new URL[]{
                    new URL("http://www.example.com/downloads/file1.pdf"),
                    new URL("http://www.example.com/downloads/file2.pdf"),
                        new URL("http://www.example.com/downloads/file3.pdf")
                };

                serviceBinder.urls = urls;
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            startService(intent);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBinder = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();

        intentFilter = new IntentFilter();
        intentFilter.addAction("FILE_DOWNLOAD_ACTION");

        registerReceiver(receiver, intentFilter);
    }

    protected void onPause(){
        super.onPause();

        unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getBaseContext(),"File download completed",Toast.LENGTH_LONG).show();
        }
    };

    public void startService(View v){
       // Intent intent = new Intent(this, MyService.class);
        //startService(intent);
        startService(new Intent(getBaseContext(), MyIntentService.class));
    }



    public void stopService(View v){
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
    }


}
