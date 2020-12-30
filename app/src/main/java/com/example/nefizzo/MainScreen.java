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

public class MainScreen extends AppCompatActivity {

    Button addButon,listButton,forumButton,searchButton,profileButton,dailyRecipeButton;
    Dialog myDialog;
    DatabaseReference usersRef;
    boolean hasRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        myDialog = new Dialog(this);
        define();
        send();
    }

    public void showDetails(){

    }

    public void showPopUp(){
        TextView txtClose;
        Button showDetailsButton;
        myDialog.setContentView(R.layout.dailyrecipepopup);
        txtClose = myDialog.findViewById(R.id.txtclose);
        showDetailsButton = myDialog.findViewById(R.id.showDetailsButton);
        Load();
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

    public void Load(){
        usersRef = FirebaseDatabase.getInstance().getReference("Members");
        ImageView recipePhoto;
        TextView recipeName;
        recipeName = myDialog.findViewById(R.id.recipeName);
        recipePhoto = myDialog.findViewById(R.id.recipePhoto);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String BufferUsername;
                for (DataSnapshot ds: snapshot.getChildren()){
                    BufferUsername = ds.child("username").getValue().toString();
                    //Log.i("aaa",BufferUsername);
                    hasRecipe = ds.hasChild("Recipes");
                    //Log.i("aaa",hasRecipe + "");
                    //Recipe rec = (Recipe) ds.child(BufferUsername).child("Recipes").getValue();
                    //Log.i("aaa",rec.getFoodName());

                    if(hasRecipe){
                        String Buffer =  ds.child("Recipes").getValue().toString();
                        int index1 = Buffer.indexOf("="); //foodname 'in sonu
                        int index2 = Buffer.indexOf("itemImage=") +9; // url'nin başı
                        int index3 = Buffer.indexOf(", ingr"); //url nin sonu

                        Log.i("aaaa", Buffer.substring(1,index1)+" ");
                        Log.i("aaaa", Buffer.substring(index2+1,index3)+" ");
                        recipeName.setText(Buffer.substring(1,index1));
                        Picasso.get()
                                .load(Buffer.substring(index2+1,index3))
                                .fit()
                                .centerCrop()
                                .into(recipePhoto);
                    }
                }
                return;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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