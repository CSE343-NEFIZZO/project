package com.example.nefizzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainScreen extends AppCompatActivity {

    Button addButon,listButton,forumButton,searchButton,profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        define();
        send();
    }

    public void define(){
        forumButton = findViewById(R.id.forumButton);
        addButon = findViewById(R.id.addfoodButton);
        listButton = findViewById(R.id.listFoodButton);
        searchButton = findViewById(R.id.searchRecipeBtn);
        profileButton = findViewById(R.id.profileButton);
    }

    public void send(){
        forumButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OuterForumActivity.class);
                startActivity(intent);
            }
        });
        addButon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddRecipeActivity.class);
                startActivity(intent);
            }
        });
        listButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecipeListActivity.class);
                startActivity(intent);
            }
        });
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }

}