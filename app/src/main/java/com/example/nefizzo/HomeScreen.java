package com.example.nefizzo;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.Random;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    ImageButton addRecipeButton,homeButton,forumButton,profileButton,searchButton, navButton;
    ImageView recipePhoto;
    TextView rd_name,rd_prep,rd_cook,rd_servings,rd_ing_title,rd_ingredients,rd_inst_title,rd_instructions;
    Button changeRecipeButton;
    Dialog myDialog;
    String name,url;
    FirebaseAuth firebaseAuth;
    DatabaseReference ref,usersRef;
    FirebaseUser user;
    static int amount=0;
    int randomNum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        define();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String data;
        data = bundle.getString("flag");
        if(data.equals("false")){
             showPopUp();
        }
        setRandomPhoto();
        click();
    }

    public void showDetails(){
        usersRef = FirebaseDatabase.getInstance().getReference("Recipes");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String cookingHour,cookingMin,foodName,ingredients,instructions,itemImage,preparationHour,preparationMin,servingNumber,category;
                for (DataSnapshot ds: snapshot.getChildren()) {
                    if(name.equals(ds.child("foodName").getValue().toString())) {
                        cookingHour = ds.child("cookingHour").getValue().toString();
                        cookingMin = ds.child("cookingMin").getValue().toString();
                        foodName = ds.child("foodName").getValue().toString();
                        ingredients = ds.child("ingredients").getValue().toString();
                        instructions = ds.child("instructions").getValue().toString();
                        itemImage = ds.child("itemImage").getValue().toString();
                        preparationHour = ds.child("preparationHour").getValue().toString();
                        preparationMin = ds.child("preparationMin").getValue().toString();
                        servingNumber = ds.child("servingNumber").getValue().toString();
                        category = ds.child("category").getValue().toString();

                        Recipe newRecipe = new Recipe(foodName,servingNumber,category,Integer.parseInt(preparationHour) ,Integer.parseInt(preparationMin),Integer.parseInt(cookingHour),Integer.parseInt(cookingMin),ingredients,instructions,itemImage);
                        Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
                        intent.putExtra("recipe", (Serializable) newRecipe);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadDetails(){
        usersRef = FirebaseDatabase.getInstance().getReference("Recipes");
        ImageView recipePhoto;
        TextView recipeName;
        recipeName = myDialog.findViewById(R.id.recipeName);
        recipePhoto = myDialog.findViewById(R.id.recipePhoto);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String BufferName,ImgUrl;
                Random rand = new Random();
                int randNum = rand.nextInt(3);
                int counter = 0;
                for (DataSnapshot ds: snapshot.getChildren()){
                    if(counter == randNum){
                        BufferName = ds.child("foodName").getValue().toString();
                        ImgUrl = ds.child("itemImage").getValue().toString();
                        name = BufferName;
                        url = ImgUrl;

                        recipeName.setText(BufferName);
                        Picasso.get()
                                .load(ImgUrl)
                                .fit()
                                .centerCrop()
                                .into(recipePhoto);
                        return;
                    }
                    counter++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void showPopUp(){
        TextView txtClose;
        Button showDetailsButton;
        myDialog.setContentView(R.layout.dailyrecipepopup);
        txtClose = myDialog.findViewById(R.id.txtclose);
        showDetailsButton = myDialog.findViewById(R.id.showDetailsButton);
        loadDetails();
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

    private void define() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navmenu);
        navigationView.setItemIconTintList(null);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();

        addRecipeButton = (ImageButton) findViewById(R.id.add_recipe_image_button);
        homeButton = (ImageButton) findViewById(R.id.homeButton);
        forumButton = (ImageButton) findViewById(R.id.forumButton);
        profileButton = (ImageButton) findViewById(R.id.profileButton);
        searchButton = (ImageButton) findViewById(R.id.search_recipe_image_button);

        recipePhoto = (ImageView) findViewById(R.id.recipePhoto);
        rd_name = (TextView) findViewById(R.id.rd_name);
        rd_prep = (TextView) findViewById(R.id.rd_prep);
        rd_cook = (TextView) findViewById(R.id.rd_cook);
        rd_servings = (TextView) findViewById(R.id.rd_servings);
        rd_ing_title = (TextView) findViewById(R.id.rd_ing_title);
        rd_ingredients = (TextView) findViewById(R.id.rd_ingredients);
        rd_inst_title = (TextView) findViewById(R.id.rd_inst_title);
        rd_instructions = (TextView) findViewById(R.id.rd_instructions);
        changeRecipeButton = (Button) findViewById(R.id.changeRecipeButton);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        myDialog = new Dialog(this);


    }

    private void setRandomPhoto(){
        amount = 0;
        ref = FirebaseDatabase.getInstance().getReference("Recipes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    amount++;
                }
                Random rand=new Random();
                randomNum = rand.nextInt(amount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Recipes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 0;
                for (DataSnapshot ds : snapshot.getChildren()){
                    if(i == randomNum){
                        rd_name.setText(ds.child("foodName").getValue().toString());
                        rd_prep.setText("Prep : "+
                                ds.child("preparationHour").getValue().toString()+"h "+
                                ds.child("preparationMin").getValue().toString()+"m");
                        rd_cook.setText("Cook : "+
                                ds.child("cookingHour").getValue().toString()+"h "+
                                ds.child("cookingMin").getValue().toString()+"m");
                        rd_servings.setText("Servings : "+ds.child("servingNumber").getValue().toString());
                        rd_ingredients.setText(ds.child("ingredients").getValue().toString());
                        rd_instructions.setText(ds.child("instructions").getValue().toString());
                        if(!(ds.child("itemImage").getValue().toString()).isEmpty())
                            Picasso.get().load(ds.child("itemImage").getValue().toString()).into(recipePhoto);
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void click(){
        changeRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRandomPhoto();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                intent.putExtra("flag","true");
                startActivity(intent);
            }
        });
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null){
                    Toast.makeText(getApplicationContext(), "You need to log in to add recipe.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), AddRecipeActivity.class);
                    startActivity(intent);
                }
            }
        });
        forumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OuterForumActivity.class);
                startActivity(intent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user == null){
                    Toast.makeText(getApplicationContext(), "You need to log in to enter to profile.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                    startActivity(intent);
                }
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id) {
            case R.id.appetizers:
                Intent appetizer = new Intent(HomeScreen.this, CategoryActivity.class);
                String appetizers = "Appetizers";
                appetizer.putExtra("CATEGORY", appetizers);
                startActivity(appetizer);
                break;
            case R.id.soups:
                Intent soups = new Intent(HomeScreen.this, CategoryActivity.class);
                String soup = "Soups";
                soups.putExtra("CATEGORY", soup);
                startActivity(soups);
                break;
            case R.id.vegetables:
                Intent vegetables = new Intent(HomeScreen.this, CategoryActivity.class);
                String vegetable = "Vegetables";
                vegetables.putExtra("CATEGORY", vegetable);
                startActivity(vegetables);
                break;
            case R.id.main_dishes:
                Intent main_dishes = new Intent(HomeScreen.this, CategoryActivity.class);
                String main_dish = "Main Dishes";
                main_dishes.putExtra("CATEGORY", main_dish);
                startActivity(main_dishes);
                break;
            case R.id.breads:
                Intent breads = new Intent(HomeScreen.this, CategoryActivity.class);
                String bread = "Breads";
                breads.putExtra("CATEGORY", bread);
                startActivity(breads);
                break;
            case R.id.desserts:
                Intent desserts = new Intent(HomeScreen.this, CategoryActivity.class);
                String dessert = "Desserts";
                desserts.putExtra("CATEGORY", dessert);
                startActivity(desserts);
                break;
            case R.id.miscellaneous:
                Intent miscellaneous = new Intent(HomeScreen.this, CategoryActivity.class);
                String misc = "Miscellaneous";
                miscellaneous.putExtra("CATEGORY", misc);
                startActivity(miscellaneous);
                break;
            case R.id.signout:
                if(firebaseAuth.getCurrentUser() != null){
                    firebaseAuth.signOut();
                }

                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
                break;

        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}