package com.example.nefizzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
import com.squareup.picasso.Picasso;

import java.util.Random;

public class HomeScreen extends AppCompatActivity {

    ImageButton addRecipeButton,homeButton,forumButton,profileButton,searchButton;
    ImageView recipePhoto;
    TextView rd_name,rd_prep,rd_cook,rd_servings,rd_ing_title,rd_ingredients,rd_inst_title,rd_instructions;
    Button changeRecipeButton;

    FirebaseAuth firebaseAuth;
    DatabaseReference ref;
    FirebaseUser user;
    static int amount=0;
    int randomNum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        define();
        setRandomPhoto();
        click();
    }


    private void define() {
        addRecipeButton = (ImageButton) findViewById(R.id.addRecipeButton);
        homeButton = (ImageButton) findViewById(R.id.homeButton);
        forumButton = (ImageButton) findViewById(R.id.forumButton);
        profileButton = (ImageButton) findViewById(R.id.profileButton);
        searchButton = (ImageButton) findViewById(R.id.searchButton);
        recipePhoto = (ImageView) findViewById(R.id.recipePhoto);
        rd_name = (TextView) findViewById(R.id.rd_name);
        rd_prep = (TextView) findViewById(R.id.rd_prep);
        rd_cook = (TextView) findViewById(R.id.rd_cook);
        rd_servings = (TextView) findViewById(R.id.rd_servings);
        rd_ing_title = (TextView) findViewById(R.id.rd_ing_title);
        rd_ingredients = (TextView) findViewById(R.id.rd_ingredients);
        rd_inst_title = (TextView) findViewById(R.id.rd_inst_title);
        rd_instructions = (TextView) findViewById(R.id.rd_instructions);
        changeRecipeButton = (Button) findViewById(R.id.changeRecipeButton);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

    private void setRandomPhoto(){
        amount = 0;
        ref = FirebaseDatabase.getInstance().getReference("Recipes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    amount++;
                }
                Random rand=new Random();
                randomNum = rand.nextInt(amount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Recipes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 0;
                for (DataSnapshot ds : snapshot.getChildren()){
                    if(i == randomNum){
                        rd_name.setText(ds.child("foodName").getValue().toString());
                        rd_prep.setText("Prep : "+
                                ds.child("preparationHour").getValue().toString()+"h "+
                                ds.child("preparationMin").getValue().toString()+"m");
                        rd_cook.setText("Cook : "+
                                ds.child("cookingHour").getValue().toString()+"h "+
                                ds.child("cookingMin").getValue().toString()+"m");
                        rd_servings.setText("Servings : "+ds.child("servingNumber").getValue().toString());
                        rd_ingredients.setText(ds.child("ingredients").getValue().toString());
                        rd_instructions.setText(ds.child("instructions").getValue().toString());
                        if(!(ds.child("itemImage").getValue().toString()).isEmpty())
                            Picasso.get().load(ds.child("itemImage").getValue().toString()).into(recipePhoto);
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void click(){
        changeRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRandomPhoto();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                startActivity(intent);
            }
        });
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null){
                    Toast.makeText(getApplicationContext(), "You need to log in to add recipe.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), AddRecipeActivity.class);
                    startActivity(intent);
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
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}