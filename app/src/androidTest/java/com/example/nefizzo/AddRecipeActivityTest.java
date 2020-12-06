package com.example.nefizzo;

import android.widget.Toast;

import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotEquals;


@RunWith(AndroidJUnit4ClassRunner.class)
public class AddRecipeActivityTest {

    @Test
    public void testIsActivityInView(){
        ActivityScenario<AddRecipeActivity> activityScenario = ActivityScenario.launch(AddRecipeActivity.class);
        onView(withId(R.id.addRecipeLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void test_visibility_of_interface_elements() {
        ActivityScenario<AddRecipeActivity> activityScenario = ActivityScenario.launch(AddRecipeActivity.class);

        //it controls interface elements' visibility
        //test recipe name
        onView(withId(R.id.recipeName)).check(matches(isDisplayed()));

        // test recipe name text view
        onView(withId(R.id.recipeNameTextView)).check(matches(isDisplayed()));

        //test servings text view
        onView(withId(R.id.servingsTextView)).check(matches(isDisplayed()));

        //test servings spinner
        onView(withId(R.id.servings)).check(matches(isDisplayed()));

        //test preparation time text view
        onView(withId(R.id.preparationTimeTextView)).check(matches(isDisplayed()));

        //test preparation hour edit text
        onView(withId(R.id.prepHour)).check(matches(isDisplayed()));

        //test preparation min edit text
        onView(withId(R.id.prepMin)).check(matches(isDisplayed()));

        //test cooking time text view
        onView(withId(R.id.cookingTimeTextView)).check(matches(isDisplayed()));

        //test cooking hour edit text
        onView(withId(R.id.cookHour)).check(matches(isDisplayed()));

        //test cooking min edit text
        onView(withId(R.id.cookMin)).check(matches(isDisplayed()));

        //test ingredients text view
        onView(withId(R.id.ingredientsTextview)).check(matches(isDisplayed()));

        //test instructions text view
        onView(withId(R.id.instructionsTextView)).check(matches(isDisplayed()));

        //test image view
        onView(withId(R.id.image_view)).check(matches(isDisplayed()));


    }

    @Test
    public void test_is_Button_Title_Text_Displayed() {
        ActivityScenario<AddRecipeActivity> activityScenario = ActivityScenario.launch(AddRecipeActivity.class);
        // it checks button's name is true or not.
        //upload recipe button
        onView(withId(R.id.uploadRecipe)).check(matches(withText("Send Recipe")));

        //choose image button
        onView(withId(R.id.chooseImageButton)).check(matches(withText("Choose image")));

    }

    public void test_editTexts(){
        ActivityScenario<AddRecipeActivity> activityScenario = ActivityScenario.launch(AddRecipeActivity.class);
        onView(withId(R.id.addRecipeLayout)).check(matches(isDisplayed()));

        String recipeName = "Sunny Side Up";
        String preparationHour = "2";
        String preparationMin = "30";
        String cookingHour = "1";
        String cookingMin = "45";
        String ingredients = "Egg olive oil pepper";
        String instructions = "Heat the oil in a medium nonstick skillet over low heat until slightly shimmering, about 5 minutes. ";

        onView(withId(R.id.recipeName)).perform(typeText(recipeName));
        onView(withId(R.id.prepHour)).perform(typeText(preparationHour));
        onView(withId(R.id.prepMin)).perform(typeText(preparationMin));
        onView(withId(R.id.cookHour)).perform(typeText(cookingHour));
        onView(withId(R.id.cookMin)).perform(typeText(cookingMin));
        onView(withId(R.id.ingredientsString)).perform(typeText(ingredients));
        onView(withId(R.id.instructionsString)).perform(typeText(instructions));
    }

    public void control_inputs(){
        ActivityScenario<AddRecipeActivity> activityScenario = ActivityScenario.launch(AddRecipeActivity.class);

        Recipe testFood = new Recipe("testname","4-6",5,40,
                2,30,"ingredientsForTest","tomato pepper","test.jpg");

        if(testFood.getFoodName().length() != 0){
            assertNotEquals(0,testFood.getFoodName().length());
        }
        if(testFood.getPreparationHour() != 0){
            assertNotEquals(0,testFood.getPreparationHour());
        }
        if(testFood.getPreparationMin() != 0){
            assertNotEquals(0,testFood.getPreparationMin());
        }
        if(testFood.getCookingHour() != 0){
            assertNotEquals(0,testFood.getCookingHour());
        }
        if(testFood.getCookingMin() != 0){
            assertNotEquals(0,testFood.getCookingMin());
        }
        if(testFood.getIngredients().length() != 0){
            assertNotEquals(0,testFood.getIngredients().length());
        }
        if(testFood.getInstructions().length() != 0){
            assertNotEquals(0,testFood.getInstructions().length());
        }

    }

}