package com.pjatk.brunolemanski.shoplist;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.pjatk.brunolemanski.shoplist.OptionActivity.*;


public class MainActivity extends AppCompatActivity {

    //UI elements
    private TextView title;
    private TextView description;
    private Button shopList;
    private Button option;

    //SharedPreferences config
    private SharedPreferences preferences;

    //BroadcastReceiver
    private IdReceive receiver = new IdReceive();
    private IntentFilter filter = new IntentFilter("com.pjatk.brunolemanski.NEW_PRODUCT");

    //Variables
    private int setBgOn = R.color.secondBackground;
    private String textColorDark = "#000000";
    private String textDesc = "Dzięki tej aplikacji z łatwością zorganizujesz swoją listę zakupów.";
    private String btnShopEn = "Shopping List";
    private String btnOptionEn = "Options";


    /**
     * Function creating objects in activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(receiver);
    }



    /**
     * Performing operations while returning to the view.
     */
    @Override
    protected void onResume() {
        super.onResume();

        title = findViewById(R.id.textView);
        description = findViewById(R.id.textView2);
        shopList = findViewById(R.id.listButton);
        option = findViewById(R.id.optionButton);

        preferences = getSharedPreferences(SHARED_PREFS_BG, MODE_PRIVATE);
        getWindow().setBackgroundDrawableResource(preferences.getInt(bg, setBgOn));
        title.setTextColor(Color.parseColor(preferences.getString(txColor, textColorDark)));
        description.setText(preferences.getString(txDescript,textDesc));
        shopList.setText(preferences.getString(btnShop, btnShopEn));
        option.setText(preferences.getString(btnOpt, btnOptionEn));
    }



    /**
     * Function calling new screen - Options screen.
     * @param view
     */
    public void optionButtonMethod(View view) {
        Intent intent = new Intent(this, OptionActivity.class);
        startActivity(intent);
    }



    /**
     * Function calling new screen - Shopping List screen.
     * @param view
     */
    public void shopListButtonMethod(View view) {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }
}
