package com.example.oskin.lesson6;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    public static final String ACTION_CHANGE_STATE = "com.example.oskin.lesson4.action.CHANGE_STATE";
    public static final String NEW_STATE = "com.example.oskin.lesson4.key.NEW_STATE";

    private boolean isInterrupted = false;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                for(int i = 0; ; i++) {

                    if (isInterrupted)
                        return;

                    Intent intent = new Intent(ACTION_CHANGE_STATE);
                    intent.putExtra(NEW_STATE, String.valueOf(i));
                    sendBroadcast(intent);
                    TimeUnit.SECONDS.sleep(1);
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        isInterrupted = true;
        super.onDestroy();
    }

    public static final Intent newIntent(Context context){
        return new Intent(context, MyService.class);
    }
}
