package com.example.oskin.lesson6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements IActivityCallback {

    public final static String ACTIVITY_STATE = "com.example.oskin.lesson6.key.ACTIVITY_STATE";

    private CustomBroadcastReceiver mCustomBroadcastReceiver;
    private IntentFilter mIntentFilter;

    private Boolean firstCreate = true;
    private Boolean stopService = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBroadcast();

        if (savedInstanceState != null){
            firstCreate = savedInstanceState.getBoolean(ACTIVITY_STATE,true);
        }

        if (firstCreate){
            startService(MyService.newIntent(MainActivity.this));
            initFragment();
            firstCreate = false;
        }


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

    private void initFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.first_frame,FirstFragment.newInstance())
                .add(R.id.second_frame,SecondFragment.newInstance())
                .commitNow();
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.second_frame);
        Boolean child = ((SecondFragment) fragment).isHasChild();
        if (child){
            ((SecondFragment) fragment).onBackPressed();
        }
        else {
        super.onBackPressed();
        }
    }

    @Override
    public String getDataFromFirstFragment() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.first_frame);
        return ((FirstFragment) fragment).getData();
    }

    private class CustomBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            FragmentManager manager = getSupportFragmentManager();
            Fragment fragment = manager.findFragmentById(R.id.first_frame);
            int i = intent.getIntExtra(MyService.NEW_STATE,0);
            ((FirstFragment) fragment).setData(i);
        }
    }
}
