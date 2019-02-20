package com.example.oskin.lesson6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements IActivityCallback {

    public final static String ACTIVITY_STATE = "com.example.oskin.lesson6.key.ACTIVITY_STATE";

    private CustomBroadcastReceiver mCustomBroadcastReceiver;
    private IntentFilter mIntentFilter;

    private Boolean firstCreate = true;
    private Boolean stopService = true;

    IFragmentCallback<String> mIFragmentCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBroadcast();

        if (savedInstanceState == null)
            startService(MyService.newIntent(MainActivity.this));

        initFragmentsCallback();
    }

    private void initFragmentsCallback() {
        mIFragmentCallback = (IFragmentCallback<String>) getSupportFragmentManager().findFragmentById(R.id.first_fragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mCustomBroadcastReceiver,mIntentFilter,null,null);
    }


    private void initBroadcast(){
        mCustomBroadcastReceiver = new CustomBroadcastReceiver();
        mIntentFilter = new IntentFilter(MyService.ACTION_CHANGE_STATE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ACTIVITY_STATE,firstCreate);
        stopService = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mCustomBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (stopService)
            stopService(MyService.newIntent(MainActivity.this));
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.second_fragment);
        if (fragment == null)
            return;
        Boolean child = ((SecondFragment) fragment).isHasChild();
        if (child){
            ((SecondFragment) fragment).onBackPressed();
        }
        else {
        super.onBackPressed();
        }
    }

    @Override
    public String getDataCallback() {
        return mIFragmentCallback.getDataCallback();
    }

    private class CustomBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(MyService.NEW_STATE);
            mIFragmentCallback.setDataCallback(data);
        }
    }
}
