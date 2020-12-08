package com.example.nefizzo;

import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4ClassRunner.class)
public class RecipeListActivityTest {
    @Test
    public void test_isActivityInView() {
        ActivityScenario<RecipeListActivity> activityScenario = ActivityScenario.launch(RecipeListActivity.class);
        onView(withId(R.id.activity_recipe_list)).check(matches(isDisplayed()));
    }

    @Test
    public void test_visibilityOfInterfaceElements() {
        ActivityScenario<RecipeListActivity> activityScenario = ActivityScenario.launch(RecipeListActivity.class);
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));

        // onView(withId(R.id.inst_text)).check(matches(isDisplayed()));
       // onView(withId(R.id.recipe_name)).check(matches(isDisplayed()));
       // onView(withId(R.id.image_view_upload)).check(matches(isDisplayed()));
       // onView(withId(R.id.prep_time)).check(matches(isDisplayed()));
       // onView(withId(R.id.cook_time)).check(matches(isDisplayed()));
       // onView(withId(R.id.serving_num)).check(matches(isDisplayed()));
    }
}