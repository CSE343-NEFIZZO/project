package com.example.addfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
        startActivity(intent);

        /*textview = (TextView) findViewById(R.id.nefizzo);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation);

        textview.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },DELAY_TIME);*/


    }


}