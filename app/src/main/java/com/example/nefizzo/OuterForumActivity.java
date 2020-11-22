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

    Button addbtn;
    ListView forumListView;
    OuterForumAdapter adp;
    Button searchBtn;
    EditText searchEditTxt;
    List<OuterForumModel> forumTitleList;

    String userName;
    DatabaseReference forumRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outer_forum);
        define();
        fillList();
        click();
    }

    private void define() {
        addbtn = (Button) findViewById(R.id.addForumBtn);
        forumListView = (ListView) findViewById(R.id.forumList);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchEditTxt = (EditText) findViewById(R.id.searchEditTxt);
        forumTitleList = new ArrayList<>();
        userName = "halime";
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
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
    }

    private void fillList() {
        forumRef = FirebaseDatabase.getInstance().getReference("Forums");

        forumRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    String title = "" + ds.child("forumTitle").getValue().toString();
                    OuterForumModel temp = new OuterForumModel(title);
                    forumTitleList.add(temp);
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