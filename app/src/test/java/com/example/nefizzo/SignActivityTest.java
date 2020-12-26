package com.example.nefizzo;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SignActivityTest {

    Member mem = new Member("username","name","surname","mail@hotmail.com","female");

    @Test
    public void test_empty_username(){
        mem.setUsername("");
        String username = "";
        assertEquals(username,mem.getUsername());
    }

    @Test
    public void test_empty_name(){
        mem.setName("");
        String name = "";
        assertEquals(name,mem.getName());
    }

    @Test
    public void test_empty_surname(){
        mem.setSurname("");
        String surname = "";
        assertEquals(surname,mem.getSurname());
    }

    @Test
    public void test_empty_mail(){
        mem.setMailAddress("");
        String mail = "";
        assertEquals(mail,mem.getMailAddress());
    }

    @Test
    public void test_true_format_mail(){
        String mail = mem.getMailAddress();
        int flag = 0;
        if(mail.contains("@hotmail.com")){
            flag = 1;
            assert(true);
        }
        if(mail.contains("@gmail.com")){
            flag = 1;
            assert(true);
        }
        assertEquals(1,flag);
    }
    @Test
    public void test_bad_format_mail(){
        mem.setMailAddress("mail");
        int flag = 0;
        if(mem.getMailAddress().contains("@hotmail.com")){
            flag = 1;
            assert(true);
        }
        if(mem.getMailAddress().contains("@gmail.com")){
            flag = 1;
            assert(true);
        }
        assertEquals(0,flag);
    }
    @Test
    public void test_empty_password1(){
        String password1 = "";
        String password2 = "pass";
        assertNotEquals(password2,password1);
    }

    @Test
    public void test_empty_password2(){
        String password1 = "pass";
        String password2 = "";
        assertNotEquals(password2,password1);
    }

    @Test
    public void test_not_equal_password(){
        String password1 = "pass";
        String password2 = "aaa";
        assertNotEquals(password2,password1);
    }

    @Test
    public void test_equal_password(){
        String password1 = "password";
        String password2 = "password";
        assertEquals(password2,password1);
    }

    @Test
    public void test_username_exist(){
        String username = "username";
        assertEquals(username,mem.getUsername());
    }

    @Test
    public void test_username_not_exist(){
        String username = "aa";
        assertNotEquals(username,mem.getUsername());
    }

}