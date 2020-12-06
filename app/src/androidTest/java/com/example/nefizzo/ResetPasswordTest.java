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
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class ResetPasswordTest {

    public boolean isMail(String mail){
        if(mail.contains("@hotmail.com")){
            return true;
        }
        if(mail.contains("@gmail.com")){
            return true;
        }
        return false;
    }

    @Test
    public void testIsActivityInView() {
        ActivityScenario<ResetPassword> activitySenario = ActivityScenario.launch(ResetPassword.class);
        // it checks aktvity is opened or not.
        onView(withId(R.id.resetScreen)).check(matches(isDisplayed()));
    }

    @Test
    public void test_visibility_of_interface_elements() {
        ActivityScenario<ResetPassword> activitySenario = ActivityScenario.launch(ResetPassword.class);
        //it controls interface elements' visibility
        //test mail address edit text
        onView(withId(R.id.mailAddressEditText)).check(matches(isDisplayed()));

        // test send button
        onView(withId(R.id.sendButton)).check(matches(isDisplayed()));
    }

    @Test
    public void test_is_Button_Title_Text_Displayed() {
        ActivityScenario<ResetPassword> activitySenario = ActivityScenario.launch(ResetPassword.class);
        // it checks buton's name is true or not.
        //loginButton
        onView(withId(R.id.sendButton)).check(matches(withText("Send")));
    }

    @Test
    public void test_correct_mail(){
        String mail = "elif-goral99@hotmail.com";
        test_navigate_main_part(mail);
    }

    @Test
    public void test_empty_mail(){
        String mail = "";
        test_navigate_main_part(mail);
    }

    @Test
    public void test_bad_format_mail(){
        String mail = "elifhsdgfshdfgshdjgfdsjhgfsjhdgfj";
        test_navigate_main_part(mail);
    }

    public void test_navigate_main_part(String mail) {
        //it checks the mail is sended or not when send button clicked.
        //it checks mail input
        ActivityScenario<ResetPassword> activitySenario = ActivityScenario.launch(ResetPassword.class);
        onView(withId(R.id.resetScreen)).check(matches(isDisplayed()));
        try {
            if (mail.length() == 0) {
                throw new Exception(String.format("Empty mail"));
            }
            else if(isMail(mail) == false){
                throw new Exception(String.format("Badly format mail"));
            }
            else{
                Espresso.onView(withId(R.id.mailAddressEditText)).perform(typeText(mail));
                Intents.init();
                Espresso.onView(withId(R.id.sendButton)).perform(click());
                Intents.release();
            }
        }
        catch(Exception e){
            Log.i("error",e.getMessage());
        }
    }

}