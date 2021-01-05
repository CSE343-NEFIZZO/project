package com.example.nefizzo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference usersRef,userRef;
    FirebaseUser user;
    SearchAdapter adapter;
    RecyclerView recyclerView;
    List<Recipe> recipeList;
    String username;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            category = null;
        } else {
            category = extras.getString("CATEGORY");
        }
        define();
        loadRecipes();
    }

    public void define()
    {
        user = FirebaseAuth.getInstance().getCurrentUser();
        recipeList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("Members");
    }

    public void loadRecipes()
    {
        usersRef = FirebaseDatabase.getInstance().getReference("Members");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    username = ds.child("username").getValue().toString();
                    userRef = database.getReference("Members").child(username).child("Recipes");
                    load(username);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void load(String username)
    {
        recyclerView = findViewById(R.id.category_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        adapter = new SearchAdapter(recipeList, getApplicationContext(), username);
        recyclerView.setAdapter(adapter);
        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe rec = snapshot.getValue(Recipe.class);
                adapter.notifyDataSetChanged();
                if (rec.getCategory() != null && rec.getCategory().equals(category)) {
                    recipeList.add(rec);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}