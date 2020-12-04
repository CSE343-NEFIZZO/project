package com.example.addfood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static java.sql.DriverManager.println;

public class MainActivity2 extends AppCompatActivity {

    EditText foodName, preparationHour, preparationMin, cookingHour, cookingMin;
    EditText ingredients, instructions;
    Spinner spinner;
    ImageView recipeImage;
    Uri uri;
    String imageUrl = "";
    Button mChooseImageBtn, sendRecipeBtn;
    String[] servingCountings = {"Choose", "1-2", "3-4", "5-6", "7-8", "9-10", "11-12", "13-14"};
    ArrayAdapter arrayAdapter;
    int checkImage = 0;
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        defineEverything();
    }

    private void defineEverything() {
        foodName = (EditText)findViewById(R.id.recipeName);
        spinner = (Spinner) findViewById(R.id.servings);
        preparationHour = (EditText)findViewById(R.id.prepHour);
        preparationMin = (EditText)findViewById(R.id.prepMin);
        cookingHour = (EditText)findViewById(R.id.cookHour);
        cookingMin = (EditText)findViewById(R.id.cookMin);
        ingredients = (EditText)findViewById(R.id.ingredientsString);
        instructions = (EditText)findViewById(R.id.instructionsString);
        recipeImage = (ImageView) findViewById(R.id.image_view);
        mChooseImageBtn = (Button)findViewById(R.id.chooseImageButton);
        createAdapter();
        setAdapterToSpinner();
    }

    public void SelectImageBtn(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            uri = data.getData();
            //set image to image view
            recipeImage.setImageURI(uri);
            checkImage = 1;
        } else Toast.makeText(this, "You have to pick an image!", Toast.LENGTH_LONG).show();

    }


    public void createAdapter() {
        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, servingCountings);
    }

    public void setAdapterToSpinner() {
        spinner.setAdapter(arrayAdapter);
    }

    public void uploadImage() {
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("RecipeImages").child(uri.getLastPathSegment());

        mStorageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                uploadRecipe();
                Toast.makeText(MainActivity2.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void UploadRecipeBtn(View view) {
        //uploadImage();
        if(foodName.length() == 0){
            foodName.setError("Please enter a food name ! ");
        }
        else if(spinner.getSelectedItem().toString() == "Choose"){
            Toast.makeText(MainActivity2.this,"Please choose a serving number!",Toast.LENGTH_LONG).show();
        }
        else if(preparationHour.length() == 0){
            preparationHour.setError("Please enter a preparation hour ! ");
        }
        else if(preparationMin.length() == 0){
            preparationMin.setError("Please enter a preparation minute ! ");
        }
        else if(cookingHour.length() == 0){
            cookingHour.setError("Please enter a cooking hour ! ");
        }
        else if(cookingMin.length() == 0){
            cookingMin.setError("Please enter a cooking minute ! ");
        }
        else if(ingredients.length() == 0){
            ingredients.setError("Please enter some ingredients !");
        }
        else if(instructions.length() == 0){
            instructions.setError("Please enter instructions ! ");
        }
        else if(checkImage == 0){
            Toast.makeText(MainActivity2.this,"Please pick an image ! ",Toast.LENGTH_LONG).show();
        }
        else uploadImage();


    }

    public void uploadRecipe() {

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Recipe Uploading...");
            progressDialog.show();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Members").child("Emir").child("Recipes").child(foodName.getText().toString());


            Food foodData = new Food(foodName.getText().toString(),
                    spinner.getSelectedItem().toString(),
                    Integer.parseInt(preparationHour.getText().toString()),
                    Integer.parseInt(preparationMin.getText().toString()),
                    Integer.parseInt(cookingHour.getText().toString()),
                    Integer.parseInt(cookingMin.getText().toString()),
                    ingredients.getText().toString(),
                    instructions.getText().toString(),
                    imageUrl);

            myRef.setValue(foodData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity2.this,"Recipe Uploaded",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        finish();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity2.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                }
            });
        }





}