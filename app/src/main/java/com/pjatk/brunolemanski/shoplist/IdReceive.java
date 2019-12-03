package com.pjatk.brunolemanski.shoplist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class IdReceive extends BroadcastReceiver {

    public static String SHOP_ACTION_PRODUCT = "com.pjatk.brunolemanski.NEW_PRODUCT";
    public static String receivedId = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(SHOP_ACTION_PRODUCT.equals(intent.getAction())) {
            receivedId = intent.getStringExtra("com.pjatk.brunolemanski.NEW_PRODUCT.ID");

            Log.i("--------- id from receiver: ", receivedId);
        }
    }
}
