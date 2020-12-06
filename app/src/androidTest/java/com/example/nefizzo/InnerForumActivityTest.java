package com.example.nefizzo;

import android.util.Log;
import android.widget.ImageView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;

import org.junit.Test;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class InnerForumActivityTest {
/*
    @Test
    public void testIsActivityInView(){
        ActivityScenario<InnerForumActivity> activityScenario = ActivityScenario.launch(InnerForumActivity.class);
        onView(withId(R.id.innerForumLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void test_visibility_of_interface_elements() {
        ActivityScenario<InnerForumActivity> activityScenario = ActivityScenario.launch(InnerForumActivity.class);
        //test forum title text view
        onView(withId(R.id.forumTitle)).check(matches(isDisplayed()));
        //test forum layout
        onView(withId(R.id.forumView)).check(matches(isDisplayed()));
        //test like button
        onView(withId(R.id.likeButton)).check(matches(isDisplayed()));
        //test dislike button
        onView(withId(R.id.dislikeButton)).check(matches(isDisplayed()));
        //test forum image view
        onView(withId(R.id.forumImage)).check(matches(isDisplayed()));
        //test caption text view
        onView(withId(R.id.caption)).check(matches(isDisplayed()));
        //test forum list view
        onView(withId(R.id.forumList)).check(matches(isDisplayed()));
        //test comment edit text
        onView(withId(R.id.comment)).check(matches(isDisplayed()));
        //test send button
        onView(withId(R.id.sendButton)).check(matches(isDisplayed()));
    }

    @Test
    public void test_emptyComment(){
        String comment = "";
        test_editText(comment);
    }

    public void test_editText(String comment) {
        try {
            if(comment.length() == 0){
                throw new Exception(String.format("Empty Comment"));
            }

            ActivityScenario<InnerForumActivity> activityScenario = ActivityScenario.launch(InnerForumActivity.class);
            onView(withId(R.id.innerForumLayout)).check(matches(isDisplayed()));

            Espresso.onView(withId(R.id.comment)).perform(typeText(comment));

            Intents.init();
            onView(withId(R.id.sendButton)).perform(scrollTo()).perform(click());
            Intents.release();
        }
        catch(Exception e){
            Log.i("exc", e.getMessage());
        }
    }
*/
}