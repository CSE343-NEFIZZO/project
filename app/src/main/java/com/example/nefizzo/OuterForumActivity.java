package com.example.nefizzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OuterForumActivity extends AppCompatActivity {

    Button addbtn,searchBtn,showForumsBtn;
    ListView forumListView;
    OuterForumAdapter adp;
    EditText searchEditTxt;
    List<OuterForumModel> forumTitleList;

    DatabaseReference forumRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outer_forum);
        define();
        click();
    }

    private void define() {
        addbtn = (Button) findViewById(R.id.addForumBtn);
        forumListView = (ListView) findViewById(R.id.forumList);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchEditTxt = (EditText) findViewById(R.id.searchEditTxt);
        forumTitleList = new ArrayList<>();
        showForumsBtn = (Button) findViewById(R.id.showForumsBtn);
    }

    private void click() {
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddForum.class);
                startActivity(intent);
            }
        });

        forumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(OuterForumActivity.this, adp.getItem(position).getForumTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), InnerForumActivity.class);
                intent.putExtra("forumTitle", adp.getItem(position).getForumTitle());
                startActivity(intent);
                fillList("");
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchEditTxt.getText().toString();
                fillList(text);
            }
        });

        showForumsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillList("");
            }
        });
    }

    private void fillList(String text) {
        if(text.isEmpty()) {
            forumRef = FirebaseDatabase.getInstance().getReference("Forums");
            forumRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    forumTitleList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String title = "" + ds.child("forumTitle").getValue().toString();
                        OuterForumModel temp = new OuterForumModel(title);
                        forumTitleList.add(temp);
                    }
                    if (forumTitleList.isEmpty())
                        Toast.makeText(OuterForumActivity.this, "Forum not found!", Toast.LENGTH_LONG).show();
                    adp = new OuterForumAdapter(forumTitleList, OuterForumActivity.this);
                    forumListView.setAdapter(adp);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            forumRef = FirebaseDatabase.getInstance().getReference("Forums");
            forumRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    forumTitleList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if(ds.child("forumTitle").getValue().toString().contains(text)) {
                            String title = "" + ds.child("forumTitle").getValue().toString();
                            OuterForumModel temp = new OuterForumModel(title);
                            forumTitleList.add(temp);
                        }
                    }
                    if (forumTitleList.isEmpty()) {
                        Toast.makeText(OuterForumActivity.this, "Forum not found!", Toast.LENGTH_LONG).show();
                        fillList("");
                    }
                    adp = new OuterForumAdapter(forumTitleList, OuterForumActivity.this);
                    forumListView.setAdapter(adp);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}
