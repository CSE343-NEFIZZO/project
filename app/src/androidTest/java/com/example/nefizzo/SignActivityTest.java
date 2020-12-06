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
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class SignActivityTest {
    List<Member> list = new ArrayList<>();

    public void defineList(){
        Member mem1 = new Member("Nesli","Nesli","Nesli","nesli@hotmail.com","456456","female");
        Member mem2 = new Member("Levo","Levo","Levo","levo@hotmail.com","111111","male");
        Member mem3 = new Member("elif","elif","elif","elif@hotmail.com","789789","female");
        list.add(mem1);
        list.add(mem2);
        list.add(mem3);
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

    public boolean isUsernameExist(String username){
        defineList();
        for(int i = 0 ; i < list.size() ; i++){
            if(list.get(i).getUsername().equals(username))
                return true;
        }
        return false;
    }

    @Test
    public void testIsActivityInView() {
        ActivityScenario<SignActivity> activitySenario = ActivityScenario.launch(SignActivity.class);
        // it checks aktvity is opened or not.
        onView(withId(R.id.sign)).check(matches(isDisplayed()));
    }

    @Test
    public void test_visibility_of_interface_elements() {
        ActivityScenario<SignActivity> activitySenario = ActivityScenario.launch(SignActivity.class);
        //it controls interface elements' visibility

        //test usernameEditText
        onView(withId(R.id.usernameEditText)).check(matches(isDisplayed()));

        // test nameEditText
        onView(withId(R.id.nameEditText)).check(matches(isDisplayed()));

        //test surnameEditText
        onView(withId(R.id.surnameEditText)).check(matches(isDisplayed()));

        //test mailEditText
        onView(withId(R.id.mailEditText)).check(matches(isDisplayed()));

        //test password1
        onView(withId(R.id.password1)).check(matches(isDisplayed()));

        //test password2
        onView(withId(R.id.password2)).check(matches(isDisplayed()));

        // test signButton
        onView(withId(R.id.signButton)).check(matches(isDisplayed()));

        //test genderText
        onView(withId(R.id.genderText)).check(matches(isDisplayed()));

        //test gender radiogroup
        onView(withId(R.id.gender)).check(matches(isDisplayed()));

        //test gender maleButton
        onView(withId(R.id.maleButton)).check(matches(isDisplayed()));

        //test gender femaleButton
        onView(withId(R.id.femaleButton)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isButttonTitleTextDisplayed() {
        ActivityScenario<SignActivity> activitySenario = ActivityScenario.launch(SignActivity.class);
        // it checks buton's name is true or not.
        onView(withId(R.id.signButton)).check(matches(withText("Join the Nefizzo World")));

        // it checks radio buton's name is true or not.
        onView(withId(R.id.maleButton)).check(matches(withText("Male")));
        onView(withId(R.id.femaleButton)).check(matches(withText("Female")));

    }

    public void control(String username,String name,String surname,String mail,String pass1,String pass2){
        try{
            if(username.length() == 0){
                throw new Exception(String.format("empty username"));
            }
            if(name.length() == 0){
                throw new Exception(String.format("empty name"));
            }
            if(surname.length() == 0){
                throw new Exception(String.format("empty surname"));
            }
            if(mail.length() == 0){
                throw new Exception(String.format("empty mail"));
            }
            if(pass1.length() == 0){
                throw new Exception(String.format("empty pass1"));
            }
            if(pass2.length() == 0){
                throw new Exception(String.format("empty pass2"));
            }
            if(pass1 != pass2){
                throw new Exception(String.format("passwords are not equal"));
            }
        }
        catch(Exception e){
            Log.i("asd",e.getMessage());
        }
    }

    @Test
    public void test_empty_username(){
        String username = "";
        String name = "name";
        String surname = "surname";
        String mail = "mail@hotmail.com";
        String password1 = "password";
        String password2 = "password";
        test_navigate_login_part_and_control(username,name,surname,mail,password1,password2);
    }

    @Test
    public void test_empty_name(){
        String username = "username";
        String name = "";
        String surname = "surname";
        String mail = "mail@hotmail.com";
        String password1 = "password";
        String password2 = "password";
        test_navigate_login_part_and_control(username,name,surname,mail,password1,password2);
    }

    @Test
    public void test_empty_surname(){
        String username = "username";
        String name = "name";
        String surname = "";
        String mail = "mail@hotmail.com";
        String password1 = "password";
        String password2 = "password";
        test_navigate_login_part_and_control(username,name,surname,mail,password1,password2);
    }

    @Test
    public void test_empty_mail(){
        String username = "username";
        String name = "name";
        String surname = "surname";
        String mail = "";
        String password1 = "password";
        String password2 = "password";
        test_navigate_login_part_and_control(username,name,surname,mail,password1,password2);
    }

    @Test
    public void test_bad_format_mail(){
        String username = "username";
        String name = "name";
        String surname = "surname";
        String mail = "mail";
        String password1 = "password";
        String password2 = "password";
        test_navigate_login_part_and_control(username,name,surname,mail,password1,password2);
    }

    @Test
    public void test_empty_password1(){
        String username = "username";
        String name = "name";
        String surname = "surname";
        String mail = "mail@hotmail.com";
        String password1 = "";
        String password2 = "password";
        test_navigate_login_part_and_control(username,name,surname,mail,password1,password2);
    }

    @Test
    public void test_empty_password2(){
        String username = "username";
        String name = "name";
        String surname = "surname";
        String mail = "mail@hotmail.com";
        String password1 = "password";
        String password2 = "";
        test_navigate_login_part_and_control(username,name,surname,mail,password1,password2);
    }

    @Test
    public void test_not_equal_pass(){
        String username = "username";
        String name = "name";
        String surname = "surname";
        String mail = "mail@hotmail.com";
        String password1 = "password";
        String password2 = "pass";
        test_navigate_login_part_and_control(username,name,surname,mail,password1,password2);
    }

    @Test
    public void test_username_exist(){
        String username = "elif";
        String name = "name";
        String surname = "surname";
        String mail = "mail@hotmail.com";
        String password1 = "password";
        String password2 = "password";
        test_navigate_login_part_and_control(username,name,surname,mail,password1,password2);
    }

    @Test
    public void test_correct_input(){
        String username = "username";
        String name = "name";
        String surname = "surname";
        String mail = "mail@hotmail.com";
        String password1 = "password1";
        String password2 = "password1";
        test_navigate_login_part_and_control(username,name,surname,mail,password1,password2);
    }

    public void test_navigate_login_part_and_control(String username,String name, String surname,String mail, String password1,String password2) {
        //it checks the true input is sended or not when send button clicked.

        ActivityScenario<SignActivity> activitySenario = ActivityScenario.launch(SignActivity.class);
        onView(withId(R.id.sign)).check(matches(isDisplayed()));

        try{
            if(isMail(mail) != true && mail.length() != 0){
                throw new Exception(String.format("badly format mail"));
            }
            if(isUsernameExist(username) == true){
                throw new Exception(String.format("username exist"));
            }
            control(username,name,surname,mail,password1,password2);
            Espresso.onView(withId(R.id.usernameEditText)).perform(typeText(username));
            Espresso.onView(withId(R.id.nameEditText)).perform(typeText(name));
            Espresso.onView(withId(R.id.surnameEditText)).perform(typeText(surname));
            Espresso.onView(withId(R.id.mailEditText)).perform(typeText(mail));
            Espresso.onView(withId(R.id.password1)).perform(typeText(password1));
            Espresso.onView(withId(R.id.password2)).perform(typeText(password2));
            Intents.init();
            onView(withId(R.id.signButton)).perform(scrollTo()).perform(click());
            Intents.release();
        }
        catch(Exception e){
            Log.i("error",e.getMessage());
        }
    }


}