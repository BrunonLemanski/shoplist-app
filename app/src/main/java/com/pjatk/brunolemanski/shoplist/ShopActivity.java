package com.pjatk.brunolemanski.shoplist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pjatk.brunolemanski.shoplist.database.FirebaseAdapter;
import com.pjatk.brunolemanski.shoplist.database.ItemModel;

import java.util.ArrayList;

import static com.pjatk.brunolemanski.shoplist.OptionActivity.*;


/**
 * Created by brunonlemanski on 28/10/2019.
 * Class responsible for operation in ShopActivity view.
 *
 * Added connection with Firebase® on 11/12/2019.
 */
public class ShopActivity extends AppCompatActivity{

    //UI elements
    private FloatingActionButton addButton;
    private LinearLayout layoutBackground;
    private TextView title;
    private TextView description;
    private View view;

    //SharedPreferences config
    private SharedPreferences preferences;

    //Firebase configuration
    private DatabaseReference reference;
    private static String DB_NAME_FIREBASE = "ShopList";
    private FirebaseAdapter firebaseAdapter;
    private FirebaseAdapter deleteAdapter;
    private FirebaseAuth mAuth;
    private ArrayList<ItemModel> list;

    //Variables
    private String setBgOn = "#ecedf1";
    private String textColorDark = "#000000";
    private String titleShopEn = "Shopping List";
    private String txDescShopEn = "Manage your daily purchases.";


    /**
     * Function creating objects in activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);

        //Getting data from Firebase®
        final RecyclerView recyclerView = findViewById(R.id.rVItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<ItemModel>();
        mAuth = FirebaseAuth.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(ShopActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
        }
        else {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid).child(DB_NAME_FIREBASE);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //Retrive data and put in RecyclerView.
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ItemModel itemModel = dataSnapshot1.getValue(ItemModel.class);
                        list.add(itemModel);
                    }
                    firebaseAdapter = new FirebaseAdapter(ShopActivity.this, list);
                    recyclerView.setAdapter(firebaseAdapter);
                    firebaseAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
                }
            });
        }

        //Swipe to delete item from RecyclerView and database.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if(direction == ItemTouchHelper.LEFT) {
                    reference.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                            list.remove(dataSnapshot.getValue(String.class));
                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Intent intent = new Intent(ShopActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        }).attachToRecyclerView(recyclerView);

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
        Intent intent = new Intent(ShopActivity.this, AddForm.class);
        startActivity(intent);
    }
}


