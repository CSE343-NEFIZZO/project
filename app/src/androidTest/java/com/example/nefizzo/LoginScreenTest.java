package com.example.nefizzo;

import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class LoginScreenTest {

    List<Member> list = new ArrayList<>();

    public void defineList(){
        Member mem1 = new Member("Nesli","Nesli","Nesli","nesli@hotmail.com","456456","female");
        Member mem2 = new Member("Levo","Levo","Levo","levo@hotmail.com","111111","male");
        Member mem3 = new Member("elif","elif","elif","elif@hotmail.com","789789","female");
        list.add(mem1);
        list.add(mem2);
        list.add(mem3);
    }

    public boolean isMember(String mail,String pass){
        defineList();
        for(int i = 0 ; i < list.size(); i++){
            if(list.get(i).getMailAddress()==mail && list.get(i).getPassword()== pass)
                return true;
        }
        return false;
    }

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
            ActivityScenario<LoginScreen> activitySenario = ActivityScenario.launch(LoginScreen.class);
            // that tests the acitvity is displayed or not.
            onView(withId(R.id.loginScreen)).check(matches(isDisplayed()));
    }

    @Test
    public void test_visibility_of_interface_elements() {
            ActivityScenario<LoginScreen> activitySenario = ActivityScenario.launch(LoginScreen.class);
            //it controls interface elements' visibility
            //test imagewiew
            onView(withId(R.id.nefizzologo)).check(matches(isDisplayed()));

            // test mail address edit text
            onView(withId(R.id.mailEditText)).check(matches(isDisplayed()));

            //test password edit text
            onView(withId(R.id.passwordEditText)).check(matches(isDisplayed()));

            //test login button
            onView(withId(R.id.loginButton)).check(matches(isDisplayed()));

            //test forgot button
            onView(withId(R.id.forgotButton)).check(matches(isDisplayed()));

            //test createAccountButton
            onView(withId(R.id.createAccountButton)).check(matches(isDisplayed()));

            // test createText
            onView(withId(R.id.createText)).check(matches(isDisplayed()));


            /*
               // alternative of onView(withId(R.id.createAccountButton)).check(matches(isDisplayed()));
                onView(withId(R.id.forgotButton)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
             */
    }

    @Test
    public void test_is_Button_Title_Text_Displayed() {
            ActivityScenario<LoginScreen> activitySenario = ActivityScenario.launch(LoginScreen.class);
            // it checks buton's name is true or not.
            //loginButton
            onView(withId(R.id.loginButton)).check(matches(withText("Logın")));

            //forgotButton
            onView(withId(R.id.forgotButton)).check(matches(withText("Forgot your Password?")));

            //createAccountButton
            onView(withId(R.id.createAccountButton)).check(matches(withText("Create your account")));
    }

    public void test_navigate_main_part(String mail,String pass) {
        //it checks it goes to main screen when login button clicked.
        ActivityScenario<LoginScreen> activitySenario = ActivityScenario.launch(LoginScreen.class);
        onView(withId(R.id.loginScreen)).check(matches(isDisplayed()));

        boolean isMem = isMember(mail,pass);
        String s1=Boolean.toString(isMem);

        try{
            if(mail.length() == 0){
                throw new Exception(String.format("empty mail"));
            }
            else if(pass.length() == 0){
                throw new Exception(String.format("empty password"));
            }
            else if(isMail(mail) == true && isMember(mail,pass) == true){
                Espresso.onView(withId(R.id.mailEditText)).perform(typeText(mail));
                Espresso.onView(withId(R.id.passwordEditText)).perform(typeText(pass));
                Intents.init();
                Espresso.onView(withId(R.id.loginButton)).perform(click());
                Intents.release();
            }
            else if(isMail(mail) == false){
                throw new Exception(String.format("Mail has badly format!"));
            }
            else if(isMember(mail,pass) == false){
                throw new Exception(String.format("user does not exist"));
            }
        }
        catch(Exception e) {
            Log.i("error",e.getMessage());
        }
    }

    @Test
    public void test_correct_input(){
        // doğru mail-pass ikilisi
        String mail = "elif@hotmail.com";
        String pass = "789789";
        test_navigate_main_part(mail,pass);
    }

    @Test
    public void test_badly_mail_format(){
        // badly mail format test
        String mail = "elif";
        String pass = "789789";
        test_navigate_main_part(mail,pass);
    }

    @Test
    public void test_user_not_exist(){
        //yanlış mail-pass ikilisi
        String mail = "elif@hotmail.com";
        String pass = "111111";
        test_navigate_main_part(mail,pass);
    }

    @Test
    public void test_empty_mail(){
        //boş mail kontrolü
        String mail = "";
        String pass = "111111";
        test_navigate_main_part(mail,pass);
    }

    @Test
    public void test_empty_password(){
        //boş pass kontrolü
        String mail = "aa@hotmail.com";
        String pass = "";
        test_navigate_main_part(mail,pass);
    }

    @Test
    public void test_navigate_reset_part() {
            ActivityScenario<LoginScreen> activitySenario = ActivityScenario.launch(LoginScreen.class);
            //forgotButton 'a tıkladığında resetScreen ' e gidiyor mu diye kontrol ediyor.
            onView(withId(R.id.loginScreen)).check(matches(isDisplayed()));
            Intents.init();
            Espresso.onView(withId(R.id.forgotButton)).perform(click());
            Intents.release();
    }

    @Test
    public void test_navigate_sign_part() {
            ActivityScenario<LoginScreen> activitySenario = ActivityScenario.launch(LoginScreen.class);
            //forgotButton 'a tıkladığında resetScreen ' e gidiyor mu diye kontrol ediyor.
            onView(withId(R.id.loginScreen)).check(matches(isDisplayed()));
            Intents.init();
            Espresso.onView(withId(R.id.createAccountButton)).perform(click());
            Intents.release();
    }

}