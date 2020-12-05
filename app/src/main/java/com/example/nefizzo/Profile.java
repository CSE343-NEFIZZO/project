package com.example.nefizzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    TextView mail,name,surname,username,gender,password;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        defineValues();
    }

    public void defineValues(){
        username = findViewById(R.id.usernameProfil);
        name = findViewById(R.id.nameProfil);
        surname = findViewById(R.id.surnameProfil);
        password = findViewById(R.id.passwordProfil);
        mail = findViewById(R.id.mailProfil);
        gender = findViewById(R.id.genderProfil);
    }

    public void readData() {
        userRef = FirebaseDatabase.getInstance().getReference("Members");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}