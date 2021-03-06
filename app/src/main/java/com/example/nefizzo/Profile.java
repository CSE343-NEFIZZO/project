
package com.example.nefizzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    Button recipeButton,logoutButton;
    FirebaseAuth firebaseAuth;
    ImageView img;
    ImageButton homeButton,forumButton,profileButton;
    DatabaseReference usersRef;
    FirebaseUser user;
    boolean hasRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        defineValues();
        readData();
        click();
    }

    public void defineValues(){
        username = findViewById(R.id.usernameProfil);
        name = findViewById(R.id.nameProfil);
        surname = findViewById(R.id.surnameProfil);
        mail = findViewById(R.id.mailProfil);
        gender = findViewById(R.id.genderProfil);
        recipeButton = findViewById(R.id.recipes);
        img = findViewById(R.id.imgview_photo);
        homeButton = (ImageButton) findViewById(R.id.homeButton);
        forumButton = (ImageButton) findViewById(R.id.forumButton);
        profileButton = (ImageButton) findViewById(R.id.profileButton);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        logoutButton = (Button) findViewById(R.id.logout);
    }

    public void click(){
        recipeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasRecipe) {
                    Intent intent = new Intent(getApplicationContext(), RecipeListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "You have no recipe.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        forumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OuterForumActivity.class);
                startActivity(intent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user == null){
                    Toast.makeText(getApplicationContext(), "You need to log in to enter to profile.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                    startActivity(intent);
                }
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                intent.putExtra("flag","true");
                startActivity(intent);
            }
        });
        logoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
            }
        });
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
                        if(BufferGender.equals("female")){
                            img.setImageResource(R.drawable.femaleicon);
                        }
                        else if(BufferGender.equals("male")){
                            img.setImageResource(R.drawable.maleicon);
                        }
                        hasRecipe = ds.hasChild("Recipes");
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