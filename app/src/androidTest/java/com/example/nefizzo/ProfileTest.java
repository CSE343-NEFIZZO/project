package com.example.nefizzo;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class ProfileTest {

    @Test
    public void testIsActivityInView() {
        ActivityScenario<Profile> activitySenario = ActivityScenario.launch(Profile.class);
        // it checks aktvity is opened or not.
        onView(withId(R.id.profilelayout)).check(matches(isDisplayed()));
    }

    @Test
    public void test_visibility_of_interface_elements() {
        ActivityScenario<Profile> activitySenario = ActivityScenario.launch(Profile.class);
        //it controls interface elements' visibility

        //test usernameEditText
        onView(withId(R.id.usernameProfil)).check(matches(isDisplayed()));

        // test image view for photo
        onView(withId(R.id.imgview_photo)).check(matches(isDisplayed()));

        //test mail layout
        onView(withId(R.id.mailLayout)).check(matches(isDisplayed()));

        //test mail text
        onView(withId(R.id.mailProfil)).check(matches(isDisplayed()));

        //test name layout
        onView(withId(R.id.nameLayout)).check(matches(isDisplayed()));

        //test name text
        onView(withId(R.id.nameProfil)).check(matches(isDisplayed()));

        // test surname layout
        onView(withId(R.id.surnameLayout)).check(matches(isDisplayed()));

        //test surname
        onView(withId(R.id.surnameProfil)).check(matches(isDisplayed()));

        //test gender layout
        onView(withId(R.id.genderLayout)).check(matches(isDisplayed()));

        //test gender text
        onView(withId(R.id.genderProfil)).check(matches(isDisplayed()));

        //test recipes button
        onView(withId(R.id.recipes)).check(matches(isDisplayed()));


    }


    @Test
    public void test_is_Button_Title_Text_Displayed() {
        ActivityScenario<Profile> activitySenario = ActivityScenario.launch(Profile.class);
        // it checks buton's name is true or not.
        onView(withId(R.id.recipes)).check(matches(withText("Recipes")));


    }

}