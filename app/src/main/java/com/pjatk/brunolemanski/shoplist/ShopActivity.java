package com.pjatk.brunolemanski.shoplist;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pjatk.brunolemanski.shoplist.database.ItemAdapter;
import com.pjatk.brunolemanski.shoplist.database.ItemDbAdapter;
import com.pjatk.brunolemanski.shoplist.database.ItemDbHelper;

import static com.pjatk.brunolemanski.shoplist.OptionActivity.*;


/**
 * Created by brunonlemanski on 28/10/2019.
 * Class responsible for operation in ShopActivity view.
 */
public class ShopActivity extends AppCompatActivity implements DialogForm.IDialogFormListener {

    //UI elements
    private FloatingActionButton addButton;
    private ItemAdapter itemAdapter;
    private LinearLayout layoutBackground;
    private TextView title;
    private TextView description;

    //SharedPreferences and db config
    private SQLiteDatabase database;
    private SharedPreferences preferences;

    //Variables
    private String setBgOn = "#ecedf1";
    private String textColorDark = "#000000";
    private String titleShopEn = "Shopping List";
    private String txDescShopEn = "Manage your daily purchases.";


    //BroadcastReceiver
    private final static String ACTION_MSG = "com.pjatk.brunolemanski.NEW_PRODUCT"; //myIntent


    /**
     * Function creating objects in activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);

        //Connecting db to activity ->>  IMPORTANT
        ItemDbHelper dbHelper = new ItemDbHelper(this);
        database = dbHelper.getWritableDatabase(); //We want to write data in database.

        //RecyclerView implement
        final RecyclerView recyclerView = findViewById(R.id.rVItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(this, getAllItems());
        recyclerView.setAdapter(itemAdapter);


        //Swipe to delete item from RecyclerView and database.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);


        //Checkbox listener.
        itemAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void setDone(RecyclerView.ViewHolder viewHolder, int position) {
                changeStatus((long) viewHolder.itemView.getTag());
            }

            @Override
            public void setUnready(RecyclerView.ViewHolder viewHolder, int position) {
                changeStatusToUnready((long) viewHolder.itemView.getTag());
            }
        });


        //Add Button listener.
        addButton = findViewById(R.id.addButton); //add functionality by ID
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }



    /**
     * Performing operations while returning to the view.
     */
    @Override
    protected void onResume() {
        super.onResume();

        title = findViewById(R.id.titlePage);
        layoutBackground = findViewById(R.id.layoutShopActivity);
        description = findViewById(R.id.descriptionPage);

        preferences = getSharedPreferences(SHARED_PREFS_BG, MODE_PRIVATE);
        layoutBackground.setBackgroundColor(Color.parseColor(preferences.getString(bg1, setBgOn)));
        title.setTextColor(Color.parseColor(preferences.getString(txColor, textColorDark)));
        title.setText(preferences.getString(titleShop, titleShopEn));
        description.setText(preferences.getString(descShop, txDescShopEn));
    }



    /**
     * Opening dialog window with form.
     */
    private void openDialog(){
        DialogForm dialogForm = new DialogForm();
        dialogForm.show(getSupportFragmentManager(), "Dialog Form");
    }



    /**
     *
     * @param nameOfProduct
     * @param price
     * @param quantity
     */
    @Override
    public void applyTexts(String nameOfProduct, String price, String quantity) {

        //3. Step
        //Build ContentValues with data from form to database. ->> IMPORTANT
        ContentValues cv = new ContentValues();

        //Uri uri = contentResolver.insert(ItemDbAdapter.AUTHORITY_URI, cv);
        //long itemId = ContentUris.parseId(uri);

        //cv.put(ItemDbAdapter.ItemDbEntry._ID, itemId);
        cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_TITLE, nameOfProduct);
        cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_PRICE, price);
        cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_QUANTITY, quantity);
        cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_DONE, 0);



        //Inserting CV data to database.
        database.insert(ItemDbAdapter.ItemDbEntry.TABLE_NAME, null, cv);
        itemAdapter.swapCursor(getAllItems());

        //Getting ID's product which is already added.
        String id = "";
        Cursor cursor = database.rawQuery("SELECT * from SQLITE_SEQUENCE", null);
        if(cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndex("seq"));
        }
        cursor.close();

        Log.i("---------id: ", id);
        sendMessage(id);
    }



    /**
     * Getting all items form db.
     * @return
     */
    private Cursor getAllItems() {
        return database.query(
                ItemDbAdapter.ItemDbEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ItemDbAdapter.ItemDbEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }



    /**
     * Removing item from Recycler View and database.
     * @param tag Id of item.
     */
    private void removeItem(long tag) {
        database.delete(
                ItemDbAdapter.ItemDbEntry.TABLE_NAME,
                ItemDbAdapter.ItemDbEntry._ID + "=" + tag, null);
    }



    /**
     * Method which changing status to "done" = 1 on line in db.
     * @param tag
     */
    private void changeStatus(long tag) {
        String where = ItemDbAdapter.ItemDbEntry._ID + "=" + tag;

        ContentValues cv = new ContentValues();
        //cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_TITLE, ItemDbAdapter.ItemDbEntry.COLUMN_TITLE);
        //cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_PRICE, ItemDbAdapter.ItemDbEntry.COLUMN_PRICE);
        //cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_QUANTITY, ItemDbAdapter.ItemDbEntry.COLUMN_QUANTITY);
        cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_DONE, 1);

        database.update(ItemDbAdapter.ItemDbEntry.TABLE_NAME, cv, where, null);
        itemAdapter.swapCursor(getAllItems());
    }



    /**
     * Method which changing status to "unready" = 0 on line in db.
     * @param tag
     */
    private void changeStatusToUnready(long tag) {
        String where = ItemDbAdapter.ItemDbEntry._ID + "=" + tag;

        ContentValues cv = new ContentValues();
        //cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_TITLE, ItemDbAdapter.ItemDbEntry.COLUMN_TITLE);
        //cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_PRICE, ItemDbAdapter.ItemDbEntry.COLUMN_PRICE);
        //cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_QUANTITY, ItemDbAdapter.ItemDbEntry.COLUMN_QUANTITY);
        cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_DONE, 0);

        database.update(ItemDbAdapter.ItemDbEntry.TABLE_NAME, cv, where, null);
    }


    /**
     * BroadcastReceiver sending message
     * @param id
     */
    private void sendMessage(String id) {
        //Sending info to BroadcastReceiver to another app can get receive.
        Intent intent = new Intent(ACTION_MSG);

        intent.setAction(ACTION_MSG);
        intent.putExtra("com.pjatk.brunolemanski.NEW_PRODUCT.ID", id); //sending values via broadcast

        sendBroadcast(intent);
    }
}