package com.example.nefizzo;

import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4ClassRunner.class)
public class OuterForumActivityTest {

    @Test
    public void testIsActivityInView(){
        ActivityScenario<OuterForumActivity> activityScenario = ActivityScenario.launch(OuterForumActivity.class);
        onView(withId(R.id.outerForumLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void test_visibility_of_interface_elements() {
        ActivityScenario<OuterForumActivity> activityScenario = ActivityScenario.launch(OuterForumActivity.class);
        onView(withId(R.id.forumTitle)).check(matches(isDisplayed()));
        //test add forum button
        onView(withId(R.id.addForumBtn)).check(matches(isDisplayed()));
        //test search edit text
        onView(withId(R.id.searchEditTxt)).check(matches(isDisplayed()));
        //test search button
        onView(withId(R.id.searchBtn)).check(matches(isDisplayed()));
        //test forum list
        onView(withId(R.id.forumList)).check(matches(isDisplayed()));

    }

}