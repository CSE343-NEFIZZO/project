package com.example.nefizzo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference usersRef;
    DatabaseReference userRef;
    RecyclerView recyclerView;
    List<Recipe> recipeList;
    RecipeAdapter adapter;
    FirebaseUser user;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
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
                    if(user.getEmail().equals(ds.child("mailAddress").getValue().toString())){
                        username = ds.child("username").getValue().toString();
                        userRef = database.getReference("Members").child(username).child("Recipes");
                        load(username);
                        return;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void load(String username)
    {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        adapter = new RecipeAdapter(recipeList, getApplicationContext(), username);
        recyclerView.setAdapter(adapter);
        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe rec = snapshot.getValue(Recipe.class);
                adapter.notifyDataSetChanged();
                Log.i("getRecipe", rec.toString());
                recipeList.add(rec);
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