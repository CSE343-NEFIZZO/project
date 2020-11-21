package com.example.nefizzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddForum extends AppCompatActivity {

    EditText forumTitleEdt;
    EditText captionEdt;
    Button addBtn;
    ImageButton addImageBtn;
    String forumTitle, caption, username;
    DatabaseReference forumRef;
    int isFind = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_forum);
        define();
        click();
    }

    public void define() {
        forumTitleEdt = (EditText) findViewById(R.id.forumTitleEdtTxt);
        captionEdt = (EditText) findViewById(R.id.forumCaptionEdtTxt);
        addBtn = (Button) findViewById(R.id.addNewFrmBtn);
        addImageBtn = (ImageButton) findViewById(R.id.addPhotoBtn);
        username = "halime";
    }

    public void click() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forumTitle = forumTitleEdt.getText().toString();
                caption = captionEdt.getText().toString();

                forumRef = FirebaseDatabase.getInstance().getReference("Forums");
                forumRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<String> forumTitleList = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String txt = "" + ds.child("forumTitle").getValue().toString();
                            forumTitleList.add(txt);
                        }

                        for (int i = 0; i < forumTitleList.size(); i++) {
                            if (forumTitleList.get(i).equals(forumTitle)) {
                                isFind = 1;
                            }
                        }
                        if (isFind == 0) {
                            Toast.makeText(AddForum.this, "Forum created!", Toast.LENGTH_LONG).show();
                            OuterForumModel newForum = new OuterForumModel(forumTitle, username, caption);
                            forumRef.child(forumTitle).setValue(newForum);
                            Intent intent = new Intent(AddForum.this, OuterForumActivity.class);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                if (isFind == 1) {
                    Toast.makeText(AddForum.this, "This forum title already exists!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddForum.this, AddForum.class);
                    startActivity(intent);
                }
            }
        });

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}