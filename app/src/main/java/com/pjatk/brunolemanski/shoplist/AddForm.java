package com.pjatk.brunolemanski.shoplist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class AddForm extends Activity {

    //UI elements
    private TextInputLayout inputProduct;
    private TextInputLayout inputPrice;
    private TextInputLayout inputQuantity;
    private Button save;
    private Button cancel;

    //fields references
    private String titleRef = "title";
    private String priceRef = "price";
    private String quanityRef = "quantity";
    private String itemIdRef = "id";

    //Firebase config
    private DatabaseReference reference;
    private static String DB_NAME_FIREBASE = "ShopList";
    private Integer itemNum = new Random().nextInt(1000000);
    private String itemId = Integer.toString(itemNum);
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item_form);

        inputProduct = findViewById(R.id.upProduct);
        inputPrice = findViewById(R.id.upPrice);
        inputQuantity = findViewById(R.id.upQuantity);
        reference = FirebaseDatabase.getInstance().getReference();
        save = findViewById(R.id.save);

        //Save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    Toast.makeText(AddForm.this, "Authentication failed", Toast.LENGTH_LONG).show();
                }
                else{
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid).child(DB_NAME_FIREBASE)
                            .child("Item" + itemNum);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataSnapshot.getRef().child(titleRef).setValue(inputProduct.getEditText().getText().toString().trim());
                            dataSnapshot.getRef().child(priceRef).setValue(inputPrice.getEditText().getText().toString().trim());
                            dataSnapshot.getRef().child(quanityRef).setValue(inputQuantity.getEditText().getText().toString().trim());
                            //dataSnapshot.getRef().child(itemIdRef).setValue(itemId);

                            Intent intent = new Intent(AddForm.this, ShopActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                mAuth = FirebaseAuth.getInstance();
                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        Toast.makeText(AddForm.this, "Authentication failed", Toast.LENGTH_LONG).show();
                    }
                };
            }
        });
    }
}
