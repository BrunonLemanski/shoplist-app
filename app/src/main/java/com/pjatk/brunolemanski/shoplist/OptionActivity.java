package com.pjatk.brunolemanski.shoplist;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OptionActivity extends AppCompatActivity {

    //UI elements
    private LinearLayout layoutOptAct;
    private SwitchCompat switchBgColor;
    private SwitchCompat switchLanguage;
    private TextView textViewOptionsTitle;
    private TextView textViewLangCard;
    private TextView textViewScreenColorCard;

    //SharedPreferences config
    private SharedPreferences preferences;
    public static final String SHARED_PREFS_BG = "settings";
    public static final String ex = "Switch";
    public static final String exL = "SwitchLang";
    public static final String bg = "Background";
    public static final String bg1 = "Background1";
    public static final String txColor = "Text";
    public static final String langTitle = "Language";
    public static final String langCard1 = "Card1Lang";
    public static final String langCard2 = "Card2Lang";
    public static final String txDescript = "Description";
    public static final String btnShop = "ShopList";
    public static final String btnOpt = "Option";
    public static final String titleShop = "ShopList";
    public static final String descShop = "ShopListDesc";

    //Variables
    public int setBgOn = R.color.secondBackground;
    public int setBgOff = R.color.background;
    public String textColorDark = "#000000";
    public String textColorLight = "#e1e0e8";
    public String setBg1 = "#ecedf1";
    public String setBg2 = "#282838";

    //Options language
    public String titleOptionEn = "Options";
    public String titleOptionPl = "Opcje";
    public String lanEn = "Language:";
    public String lanPl = "Język:";
    public String scrColorEn = "Screen color:";
    public String scrColorPl = "Kolor ekranu:";
    public String txDescrEn = "Thanks to this application you can easily organise your shopping list.";
    public String txDescrPl = "Dzięki tej aplikacji z łatwością zorganizujesz swoją listę zakupów.";
    public String btnShopEn = "Shopping List";
    public String btnShopPl = "Lista zakupów";
    public String btnOptionEn = "Options";
    public String btnOptionPl = "Opcje";
    public String titleShopEn = "Shopping List";
    public String titleShopPl = "Lista Zakupów";
    public String txDescShopEn = "Manage your daily purchases.";
    public String txDescShopPl = "Zarządzaj swoimi codziennymi wydatkami.";


    /**
     * Function creating objects in activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_activity);
        switchBgColor = findViewById(R.id.sBgColor);
        switchLanguage = findViewById(R.id.sLanguage);
        layoutOptAct = findViewById(R.id.mainLL);
        textViewOptionsTitle = findViewById(R.id.textViewTitleOption);
        textViewLangCard = findViewById(R.id.cardOptLang);
        textViewScreenColorCard = findViewById(R.id.screenColorCard);


        preferences = getSharedPreferences(SHARED_PREFS_BG, MODE_PRIVATE); // filename which store preferences
        final SharedPreferences.Editor prefsEdit = preferences.edit(); //receive access to SP editor

        switchBgColor.setChecked(preferences.getBoolean(ex, false));
        layoutOptAct.setBackgroundResource(preferences.getInt(bg, setBgOn));
        textViewOptionsTitle.setTextColor(Color.parseColor(preferences.getString(txColor, textColorDark)));

        switchLanguage.setChecked(preferences.getBoolean(exL, false));
        textViewOptionsTitle.setText(preferences.getString(langTitle, titleOptionPl));
        textViewLangCard.setText(preferences.getString(langCard1,lanPl));
        textViewScreenColorCard.setText(preferences.getString(langCard2, scrColorPl));

        /**
         * Switch button actions - changing app styles.
         */
        switchBgColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    prefsEdit.putBoolean(ex, true);
                    prefsEdit.putInt(bg, setBgOn);
                    prefsEdit.putString(bg1, setBg1);
                    prefsEdit.putString(txColor, textColorDark);
                    layoutOptAct.setBackgroundResource(R.color.secondBackground);
                    textViewOptionsTitle.setTextColor(Color.parseColor(textColorDark));

                } else {
                    prefsEdit.putBoolean(ex, false);
                    prefsEdit.putInt(bg, setBgOff);
                    prefsEdit.putString(bg1, setBg2);
                    prefsEdit.putString(txColor, textColorLight);
                    layoutOptAct.setBackgroundResource(R.color.background);
                    textViewOptionsTitle.setTextColor(Color.parseColor(textColorLight));
                }
                prefsEdit.apply();
            }
        });


        switchLanguage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    prefsEdit.putBoolean(exL, true);
                    prefsEdit.putString(langTitle, titleOptionPl);
                    prefsEdit.putString(langCard1, lanPl);
                    prefsEdit.putString(langCard2, scrColorPl);
                    prefsEdit.putString(txDescript, txDescrPl);
                    prefsEdit.putString(btnShop, btnShopPl);
                    prefsEdit.putString(btnOpt, btnOptionPl);
                    prefsEdit.putString(titleShop, titleShopPl);
                    prefsEdit.putString(descShop, txDescShopPl);

                    textViewOptionsTitle.setText(titleOptionPl);
                    textViewLangCard.setText(lanPl);
                    textViewScreenColorCard.setText(scrColorPl);

                } else {
                    prefsEdit.putBoolean(exL, false);
                    prefsEdit.putString(langTitle, titleOptionEn);
                    prefsEdit.putString(langCard1, lanEn);
                    prefsEdit.putString(langCard2, scrColorEn);
                    prefsEdit.putString(txDescript, txDescrEn);
                    prefsEdit.putString(btnShop, btnShopEn);
                    prefsEdit.putString(btnOpt, btnOptionEn);
                    prefsEdit.putString(titleShop, titleShopEn);
                    prefsEdit.putString(descShop, txDescShopEn);

                    textViewOptionsTitle.setText(titleOptionEn);
                    textViewLangCard.setText(lanEn);
                    textViewScreenColorCard.setText(scrColorEn);
                }
                prefsEdit.apply();
            }
        });
    }
}
