package com.example.nefizzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.io.Serializable;
import java.util.Random;

public class MainScreen extends AppCompatActivity {

    Button addButon,listButton,forumButton,searchButton,profileButton,dailyRecipeButton;
    Dialog myDialog;
    String name,url;
    DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        myDialog = new Dialog(this);
        define();
        send();
    }

    public void showDetails(){
        usersRef = FirebaseDatabase.getInstance().getReference("Recipes");

        usersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String cookingHour,cookingMin,foodName,ingredients,instructions,itemImage,preparationHour,preparationMin,servingNumber;
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        Log.i("asd",ds.child("foodName").getValue().toString());
                        if(name.equals(ds.child("foodName").getValue().toString())) {
                            cookingHour = ds.child("cookingHour").getValue().toString();
                            cookingMin = ds.child("cookingMin").getValue().toString();
                            foodName = ds.child("foodName").getValue().toString();
                            ingredients = ds.child("ingredients").getValue().toString();
                            instructions = ds.child("instructions").getValue().toString();
                            itemImage = ds.child("itemImage").getValue().toString();
                            preparationHour = ds.child("preparationHour").getValue().toString();
                            preparationMin = ds.child("preparationMin").getValue().toString();
                            servingNumber = ds.child("servingNumber").getValue().toString();

                            Recipe newRecipe = new Recipe(foodName,servingNumber,Integer.parseInt(preparationHour) ,Integer.parseInt(preparationMin),Integer.parseInt(cookingHour),Integer.parseInt(cookingMin),ingredients,instructions,itemImage);
                            Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
                            intent.putExtra("recipe", (Serializable) newRecipe);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            return;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    public void loadDetails(){
        usersRef = FirebaseDatabase.getInstance().getReference("Recipes");
        ImageView recipePhoto;
        TextView recipeName;
        recipeName = myDialog.findViewById(R.id.recipeName);
        recipePhoto = myDialog.findViewById(R.id.recipePhoto);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String BufferName,ImgUrl;
                Random rand = new Random();
                int randNum = rand.nextInt(3);
                int counter = 0;
                for (DataSnapshot ds: snapshot.getChildren()){
                    if(counter == randNum){
                        BufferName = ds.child("foodName").getValue().toString();
                        ImgUrl = ds.child("itemImage").getValue().toString();
                        name = BufferName;
                        url = ImgUrl;

                        recipeName.setText(BufferName);
                        Picasso.get()
                                .load(ImgUrl)
                                .fit()
                                .centerCrop()
                                .into(recipePhoto);
                        return;
                    }
                    counter++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void showPopUp(){
        TextView txtClose;
        Button showDetailsButton;
        myDialog.setContentView(R.layout.dailyrecipepopup);
        txtClose = myDialog.findViewById(R.id.txtclose);
        showDetailsButton = myDialog.findViewById(R.id.showDetailsButton);
        loadDetails();
        showDetailsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetails();
            }
        });

        txtClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    public void define(){
        forumButton = findViewById(R.id.forumButton);
        addButon = findViewById(R.id.addfoodButton);
        listButton = findViewById(R.id.listFoodButton);
        searchButton = findViewById(R.id.searchRecipeBtn);
        profileButton = findViewById(R.id.profileButton);
        dailyRecipeButton = findViewById(R.id.dailyRecipe);
    }

    public void send(){
        forumButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, OuterForumActivity.class);
                startActivity(intent);
            }
        });
        addButon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, AddRecipeActivity.class);
                startActivity(intent);
            }
        });
        listButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, RecipeListActivity.class);
                startActivity(intent);
            }
        });
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, Profile.class);
                startActivity(intent);
            }
        });

        dailyRecipeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"toast message",Toast.LENGTH_SHORT).show();
                showPopUp();
            }
        });
    }

}