package com.restaurant.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.restaurant.menu.models.Model;

import java.util.HashMap;
import java.util.Map;

public class CreateHotel extends AppCompatActivity {

    ImageView arrowBack;
    Button btnSubmit;
    EditText edName, edEmail, edMobile, edLocation, edRating;
    String name, email, address, mob, rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hotel);

        arrowBack = findViewById(R.id.arrowBack);
        btnSubmit = findViewById(R.id.submit_btn);
        edName = findViewById(R.id.ed_name);
        edEmail = findViewById(R.id.ed_email);
        edMobile = findViewById(R.id.ed_mob);
        edLocation = findViewById(R.id.ed_address);
        edRating = findViewById(R.id.ed_rating);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateHotel.this, MainActivity.class));
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 name = edName.getText().toString();
                 email = edEmail.getText().toString();
                 mob = edMobile.getText().toString();
                 address = edLocation.getText().toString();
                 rating = edRating.getText().toString();

                if(TextUtils.isEmpty(name) && TextUtils.isEmpty(email) && TextUtils.isEmpty(mob)
                && TextUtils.isEmpty(address) && TextUtils.isEmpty(rating)) {
                    Toast.makeText(CreateHotel.this, "Please add some data", Toast.LENGTH_LONG).show();
                } else {
                    addDataToFirebase();
                }
            }
        });


    }

    private void addDataToFirebase() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", edName.getText().toString());
        map.put("email", edEmail.getText().toString());
        map.put("mob", edMobile.getText().toString());
        map.put("location", edLocation.getText().toString());
        map.put("rating", edRating.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("hotels").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        edName.setText("");
                        edEmail.setText("");
                        edMobile.setText("");
                        edLocation.setText("");
                        edRating.setText("");
                        Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CreateHotel.this, MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Could not insert", Toast.LENGTH_LONG).show();
            }
        });
    }
}