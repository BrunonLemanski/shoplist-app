package com.pjatk.brunolemanski.shoplist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class EditActivity extends AppCompatActivity {

    public static String SHOP_ACTION_PRODUCT = "com.pjatk.brunolemanski.NEW_PRODUCT";
    private IntentFilter filter = new IntentFilter("com.pjatk.brunolemanski.NEW_PRODUCT");
    public static String receivedId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_product);
    }
/*
    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(broadcastReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);
    }
*/

}
