package com.pjatk.brunolemanski.shoplist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.pjatk.brunolemanski.shoplist.database.ItemAdapter;
import com.pjatk.brunolemanski.shoplist.database.ItemDbAdapter;
import com.pjatk.brunolemanski.shoplist.database.ItemDbHelper;

import static com.pjatk.brunolemanski.shoplist.IdReceive.receivedId;

public class EditActivity extends AppCompatActivity {

    //Ui Elements
    private EditText productEditText;
    private EditText priceEditText;
    private EditText quantityEditText;

    //SQLite Database
    private SQLiteDatabase database;
    private ItemAdapter itemAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_product);

        //Connecting db to activity ->>  IMPORTANT
        ItemDbHelper dbHelper = new ItemDbHelper(this);
        database = dbHelper.getWritableDatabase();

        itemAdapter = new ItemAdapter(this, getAllItems());

        productEditText = findViewById(R.id.productEdit);
        priceEditText = findViewById(R.id.priceEdit);
        quantityEditText = findViewById(R.id.quanityEdit);

        putValuesToEditText();
    }


    /**
     * Saving changes.
     * @param view
     */
    public void saveEdit(View view) {
        Toast.makeText(this, "SAVING...", Toast.LENGTH_SHORT).show();
        getValuesFromEditText();
    }


    /**
     * Put values by ID exicting in database.
     */
    private void putValuesToEditText() {
        String product = "";
        String price = "";
        String quantity = "";

        String where = " WHERE _id=?";
        Cursor cursor = database.rawQuery("SELECT * FROM itemlist" + where, new String[]{receivedId});

        if(cursor.moveToFirst()) {
            product = cursor.getString(cursor.getColumnIndex("title"));
            price = cursor.getString(cursor.getColumnIndex("price"));
            quantity = cursor.getString(cursor.getColumnIndex("quantity"));
        }

        productEditText.setText(product);
        priceEditText.setText(price);
        quantityEditText.setText(quantity);
        cursor.close();
    }


    /**
     * Getting values from EditText fields in EditActivity.
     */
    private void getValuesFromEditText() {

        String where = ItemDbAdapter.ItemDbEntry._ID + "=" + receivedId;

        ContentValues cv = new ContentValues();

        cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_TITLE, productEditText.getText().toString().trim());
        cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_PRICE, priceEditText.getText().toString().trim());
        cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_QUANTITY, quantityEditText.getText().toString().trim());
        cv.put(ItemDbAdapter.ItemDbEntry.COLUMN_DONE, 1);

        database.update(ItemDbAdapter.ItemDbEntry.TABLE_NAME, cv, where, null);
        itemAdapter.swapCursor(getAllItems());
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


}
























