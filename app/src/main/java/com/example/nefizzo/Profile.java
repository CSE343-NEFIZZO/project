package com.example.nefizzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    TextView mail,name,surname,username,gender;
    Button recipeButton;
    FirebaseAuth firebaseAuth;
    DatabaseReference usersRef;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        defineValues();
        readData();
        recipesButton();
    }

    public void recipesButton(){
        recipeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecipeListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void defineValues(){
        username = findViewById(R.id.usernameProfil);
        name = findViewById(R.id.nameProfil);
        surname = findViewById(R.id.surnameProfil);
        mail = findViewById(R.id.mailProfil);
        gender = findViewById(R.id.genderProfil);
        recipeButton = findViewById(R.id.recipes);
    }

    public void readData() {
        usersRef = FirebaseDatabase.getInstance().getReference("Members");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String BufferUserName,bufferName,bufferSurname,BufferGender;
                for (DataSnapshot ds: snapshot.getChildren()){
                    if(user.getEmail().equals(ds.child("mailAddress").getValue().toString())){
                        BufferUserName = ds.child("username").getValue().toString();
                        bufferName = ds.child("name").getValue().toString();
                        bufferSurname = ds.child("surname").getValue().toString();
                        BufferGender = ds.child("gender").getValue().toString();
                        username.setText(BufferUserName);
                        name.setText(bufferName);
                        surname.setText(bufferSurname);
                        mail.setText(user.getEmail());
                        gender.setText(BufferGender);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}