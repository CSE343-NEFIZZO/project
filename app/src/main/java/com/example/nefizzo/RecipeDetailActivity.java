package com.example.nefizzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RecipeDetailActivity extends AppCompatActivity {

    Recipe currentRecipe;
    ImageView recipe_img;
    TextView recipe_name, prep_time, cook_time, serving_number, ingredients, instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        define();
        load();
    }

    public void define()
    {
        recipe_img = (ImageView) findViewById(R.id.rd_image);
        recipe_name = (TextView) findViewById(R.id.rd_name);
        prep_time = (TextView) findViewById(R.id.rd_prep);
        cook_time = (TextView) findViewById(R.id.rd_cook);
        serving_number = (TextView) findViewById(R.id.rd_servings);
        ingredients = (TextView) findViewById(R.id.rd_ingredients);
        instructions = (TextView) findViewById(R.id.rd_instructions);
    }

    public void load()
    {
        Intent intent = getIntent();
        if (intent != null) {
            currentRecipe = (Recipe) intent.getSerializableExtra("recipe");

            Picasso.get()
                    .load(currentRecipe.getItemImage())
                    .fit()
                    .centerCrop()
                    .into(recipe_img);

            recipe_name.setText(currentRecipe.getFoodName());
        }

        if (currentRecipe.getPreparationHour() == 0) {
            prep_time.setText("Prep: " + currentRecipe.getPreparationMin() + "m");
        } else if ((currentRecipe.getPreparationHour() > 0)
                && (currentRecipe.getPreparationMin() == 0)) {
            prep_time.setText("Prep: " + currentRecipe.getPreparationHour() + "h");
        } else {
            prep_time.setText("Prep: " + currentRecipe.getPreparationHour() + "h "
                    + currentRecipe.getPreparationMin() + "m");
        }

        if (currentRecipe.getCookingHour() == 0) {
            cook_time.setText("Cook: " + currentRecipe.getCookingMin() + "m");
        } else if ((currentRecipe.getCookingHour() > 0 )
                && (currentRecipe.getCookingMin() == 0)) {
            cook_time.setText("Cook: " + currentRecipe.getCookingHour() + "h");
        } else {
            cook_time.setText("Cook: " + currentRecipe.getCookingHour() + "h "
                    + currentRecipe.getCookingMin() + "m");
        }
        serving_number.setText("Servings: " + currentRecipe.getServingNumber());
        ingredients.setText(currentRecipe.getIngredients());
        instructions.setText(currentRecipe.getInstructions());
    }
}