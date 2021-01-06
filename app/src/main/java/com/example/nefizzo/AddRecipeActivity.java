package com.example.nefizzo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddRecipeActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference usersRef,addRecipeRef;
    FirebaseUser user;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    EditText foodName, preparationHour, preparationMin, cookingHour, cookingMin;
    EditText ingredients, instructions;
    Spinner spinner,spinner2;
    ImageView recipeImage;
    Uri uri;
    String imageUrl = "";
    String username;
    Button mChooseImageBtn;
    String[] servingCountings = {"Choose", "1-2", "3-4", "5-6", "7-8", "9-10", "11-12", "13-14"};
    String[] categories = {"Choose","Appetizers","Soups","Vegetables","Main Dishes","Breads","Desserts","Miscellaneous"};
    ArrayAdapter<String> arrayAdapter,categoryAdapter;
    int checkImage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipe_layout);
        defineEverything();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

    private void defineEverything() {
        foodName = findViewById(R.id.recipeName);
        spinner = findViewById(R.id.servings);
        spinner2 = findViewById(R.id.category);
        preparationHour = findViewById(R.id.prepHour);
        preparationMin = findViewById(R.id.prepMin);
        cookingHour = findViewById(R.id.cookHour);
        cookingMin = findViewById(R.id.cookMin);
        ingredients = findViewById(R.id.ingredientsString);
        instructions = findViewById(R.id.instructionsString);
        recipeImage = findViewById(R.id.image_view);
        mChooseImageBtn = findViewById(R.id.chooseImageButton);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        createAdapter();
        setAdapterToSpinner();
    }

    public void SelectImageBtn(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1907);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1907 && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            recipeImage.setImageURI(uri);
            checkImage = 1;
        }
    }


    public void createAdapter() {
        arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, servingCountings);
        categoryAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, categories);
    }

    public void setAdapterToSpinner() {
        spinner.setAdapter(arrayAdapter);
        spinner2.setAdapter(categoryAdapter);
    }

    public void UploadRecipeBtn(View view) {
        //uploadImage();
        if (user == null) {
            Toast.makeText(getApplicationContext(), "You need to sign in to add recipe!", Toast.LENGTH_LONG).show();
        }
        else if(foodName.length() == 0){
            foodName.setError("Please enter a food name ! ");
        }
        else if(spinner.getSelectedItem().toString().equals("Choose")){
            Toast.makeText(AddRecipeActivity.this,"Please choose a serving number!",Toast.LENGTH_LONG).show();
        }
        else if(spinner2.getSelectedItem().toString().equals("Choose")){
            Toast.makeText(AddRecipeActivity.this,"Please choose a category!",Toast.LENGTH_LONG).show();
        }
        else if(preparationHour.length() == 0 || (Integer.parseInt(preparationHour.getText().toString()) > 24)){
            preparationHour.setError("Please enter an appropriate preparation hour ! ");
        }
        else if(preparationMin.length() == 0 || (Integer.parseInt(preparationMin.getText().toString()) > 59)){
            preparationMin.setError("Please enter an appropriate preparation minute ! ");
        }
        else if(cookingHour.length() == 0 || (Integer.parseInt(cookingHour.getText().toString()) > 24)){
            cookingHour.setError("Please enter an appropriate cooking hour ! ");
        }
        else if(cookingMin.length() == 0 || (Integer.parseInt(cookingMin.getText().toString()) > 59)){
            cookingMin.setError("Please enter an appropriate cooking minute ! ");
        }
        else if(ingredients.length() == 0){
            ingredients.setError("Please enter some ingredients !");
        }
        else if(instructions.length() == 0){
            instructions.setError("Please enter instructions ! ");
        }
        else if(checkImage == 0){
            Toast.makeText(AddRecipeActivity.this,"Please pick an image ! ",Toast.LENGTH_LONG).show();
        }
        else{
            addRecipeRef = FirebaseDatabase.getInstance().getReference("Members");
            addRecipeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userName2;
                    userName2 = foodName.getText().toString();
                    userName2 = userName2.replaceAll(" ", "-");
                    StorageReference ref = storageReference.child("RecipeImages").child(userName2 + ".jpg");
                    ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddRecipeActivity.this, "Recipe uploaded!", Toast.LENGTH_SHORT).show();
                            Task<Uri> temp = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            while (!temp.isComplete()) ;
                            imageUrl= temp.getResult().toString();
                            usersRef = FirebaseDatabase.getInstance().getReference("Members");
                            usersRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        if (user.getEmail().toString().equals(ds.child("mailAddress").getValue().toString())) {
                                            username = ds.child("username").getValue().toString();
                                            DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference("Recipes").child(foodName.getText().toString());
                                            Recipe recipeData = new Recipe(foodName.getText().toString(),
                                                    spinner.getSelectedItem().toString(),
                                                    spinner2.getSelectedItem().toString(),
                                                    Integer.parseInt(preparationHour.getText().toString()),
                                                    Integer.parseInt(preparationMin.getText().toString()),
                                                    Integer.parseInt(cookingHour.getText().toString()),
                                                    Integer.parseInt(cookingMin.getText().toString()),
                                                    ingredients.getText().toString(),
                                                    instructions.getText().toString(),
                                                    imageUrl);
                                            usersRef.child(username).child("Recipes").child(foodName.getText().toString()).setValue(recipeData);
                                            myRef2.setValue(recipeData);
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Intent intent = new Intent(AddRecipeActivity.this, HomeScreen.class);
            String flag = "true";
            intent.putExtra("flag",flag);
            startActivity(intent);
        }
    }

}

