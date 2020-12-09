package com.example.nefizzo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference usersRef,userRef;
    FirebaseUser user;
    SearchAdapter adapter;
    RecyclerView recyclerView;
    EditText searchTxt;
    List<Recipe> recipeList;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        define();
        loadRecipes();
        searchTxt = findViewById(R.id.searchRecipeText);

        searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String textArray = s.toString();
                filter(textArray);

            }
        });


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
            recyclerView = findViewById(R.id.search_recycler_view);
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
            adapter = new SearchAdapter(recipeList, getApplicationContext(), username);
            recyclerView.setAdapter(adapter);
            userRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Recipe rec = snapshot.getValue(Recipe.class);
                    adapter.notifyDataSetChanged();
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


    public void define()
    {
        user = FirebaseAuth.getInstance().getCurrentUser();
        recipeList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("Members");
    }

    private void filter(String givenText) {
        ArrayList<Recipe> filterList = new ArrayList<>();
        String[] splitWords = givenText.split("\\s+");
        boolean flag = true;

        if(splitWords.length == 1){
            for(Recipe item: recipeList){
                if(item.getFoodName().toLowerCase().contains(givenText.toLowerCase()) ||
                        item.getIngredients().toLowerCase().contains(givenText.toLowerCase()))
                    filterList.add(item);
            }
        }
        else if(splitWords.length > 1){
            for(Recipe item: recipeList){
                flag =true;
                for(int i=0;i<splitWords.length && flag != false;++i){

                    if(item.getFoodName().toLowerCase().contains(splitWords[i]) ||
                            item.getIngredients().toLowerCase().contains(splitWords[i]))
                        flag = true;
                    else flag = false;
                }
                if(flag == true) filterList.add(item);
            }
        }
        adapter.filteredList(filterList);
    }
}