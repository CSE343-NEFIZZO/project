package com.example.addfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
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