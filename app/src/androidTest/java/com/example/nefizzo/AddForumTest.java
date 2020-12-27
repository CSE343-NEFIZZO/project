package com.example.nefizzo;

import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4ClassRunner.class)
public class AddForumTest {

    @Test
    public void testIsActivityInView(){
        ActivityScenario<AddForum> activityScenario = ActivityScenario.launch(AddForum.class);
        onView(withId(R.id.addForumLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void test_visibility_of_interface_elements() {
        ActivityScenario<AddForum> activityScenario = ActivityScenario.launch(AddForum.class);
        //test forum title edit text
        onView(withId(R.id.forumTitleEdtTxt)).check(matches(isDisplayed()));
        //test forum caption edit text
        onView(withId(R.id.forumCaptionEdtTxt)).check(matches(isDisplayed()));
        //test add photo image button
        onView(withId(R.id.addPhotoBtn)).check(matches(isDisplayed()));
        //test add new forum button
        onView(withId(R.id.addNewFrmBtn)).check(matches(isDisplayed()));
        //test add new forum button
        onView(withId(R.id.addNewFrmBtn)).check(matches(isDisplayed()));
        //test info1 text view
        onView(withId(R.id.info1)).check(matches(isDisplayed()));
        //test info2 text view
        onView(withId(R.id.info2)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isButtonDisplayedCorrectly() {
        ActivityScenario<AddForum> activityScenario = ActivityScenario.launch(AddForum.class);
        // it checks button's name is true or not.
        //add new forum button
        onView(withId(R.id.addNewFrmBtn)).check(matches(withText("Add")));

    }

    @Test
    public void test_emptyForumTitle(){
        String forumTitle = "";
        String forumCaption = "caption";
        test_editTexts(forumTitle, forumCaption);
    }

    @Test
    public void test_emptyForumCaption(){
        String forumTitle = "forum title";
        String forumCaption = "";
        test_editTexts(forumTitle, forumCaption);
    }

    @Test
    public void test_restrictedForumTitle(){
        String forumTitle = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String forumCaption = "a";
        test_editTexts(forumTitle, forumCaption);
    }

    @Test
    public void test_restrictedForumCaption(){
        String forumTitle = "forum title";
        String forumCaption = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        test_editTexts(forumTitle, forumCaption);
    }

    public void test_editTexts(String forumTitle, String forumCaption) {
        try {
            if(forumTitle.length() == 0){
                throw new Exception(String.format("Empty Forum Title"));
            }
            if(forumCaption.length() == 0){
                throw new Exception(String.format("Empty Forum Caption"));
            }
            ActivityScenario<AddForum> activityScenario = ActivityScenario.launch(AddForum.class);
            onView(withId(R.id.addForumLayout)).check(matches(isDisplayed()));

            Espresso.onView(withId(R.id.forumTitleEdtTxt)).perform(typeText(forumTitle));
            Espresso.onView(withId(R.id.forumCaptionEdtTxt)).perform(typeText(forumCaption));

            Intents.init();
            onView(withId(R.id.addNewFrmBtn)).perform(scrollTo()).perform(click());
            Intents.release();
        }
        catch(Exception e){
            Log.i("exc", e.getMessage());
        }
    }


}