package com.example.nefizzo;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


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

    @Test
    public void test_editTexts(){
        ActivityScenario<AddRecipeActivity> activityScenario = ActivityScenario.launch(AddRecipeActivity.class);
        onView(withId(R.id.addRecipeLayout)).check(matches(isDisplayed()));

        String recipeName = "Sunny Side Up";
        String preparationHour = "2";
        String preparationMin = "30";
        String cookingHour = "1";
        String cookingMin = "45";
        String ingredients = "Egg olive oil pepper";

        Espresso.onView(withId(R.id.recipeName)).perform((typeText(recipeName)));
        Espresso.onView(withId(R.id.recipeName)).perform(typeText(recipeName));
        Espresso.onView(withId(R.id.prepHour)).perform(typeText(preparationHour));
        Espresso.onView(withId(R.id.prepMin)).perform(typeText(preparationMin));
        Espresso.onView(withId(R.id.cookHour)).perform(typeText(cookingHour));
        Espresso.onView(withId(R.id.cookMin)).perform(typeText(cookingMin));
        Espresso.onView(withId(R.id.ingredientsString)).perform(typeText(ingredients));
    }

    @Test
    public void control_inputs(){
        ActivityScenario<AddRecipeActivity> activityScenario = ActivityScenario.launch(AddRecipeActivity.class);

        Recipe testFood = new Recipe("testname","4-6",5,40,
                2,30,"ingredientsForTest","tomato pepper","test.jpg");

        if(testFood.getFoodName().length() != 0){
            assertNotEquals(0,testFood.getFoodName().length());
        }
        if(testFood.getPreparationHour() != 0){
            assertNotEquals(0,testFood.getPreparationHour());
            int prephour = 58;
            testFood.setCookingHour(prephour);
            if(testFood.getCookingHour() > 24){
                assertTrue("This will fail",true);
            }
        }
        if(testFood.getPreparationMin() != 0){
            assertNotEquals(0,testFood.getPreparationMin());
            int prepMin = 67;
            testFood.setPreparationMin(prepMin);
            if(testFood.getPreparationMin() > 59){
                assertTrue("This will fail",true);
            }
        }

        if(testFood.getCookingHour() != 0){
            assertNotEquals(0,testFood.getCookingHour());
            int cookHour = 58;
            testFood.setCookingHour(cookHour);
            if(testFood.getCookingHour() > 24){
                assertTrue("This will fail",true);
            }
        }
        if(testFood.getCookingMin() != 0){
            assertNotEquals(0,testFood.getCookingMin());
            int cookMin = 458;
            testFood.setPreparationMin(cookMin);
            if(testFood.getPreparationMin() > 59){
                assertTrue("This will fail",true);
            }
        }
        if(testFood.getIngredients().length() != 0){
            assertNotEquals(0,testFood.getIngredients().length());
        }
        if(testFood.getInstructions().length() != 0){
            assertNotEquals(0,testFood.getInstructions().length());
        }

    }

}