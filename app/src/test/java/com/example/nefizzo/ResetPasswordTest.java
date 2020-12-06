package com.example.nefizzo;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResetPasswordTest {

    @Test
    public void test_empty_mail(){
        String mail = "";
        assertEquals(mail,"");
    }

    @Test
    public void test_true_format_mail(){
        String mail = "mail@hotmail.com";
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
        String mail = "mail";
        int flag = 0;
        if(mail.contains("@hotmail.com")){
            flag = 1;
            assert(true);
        }
        if(mail.contains("@gmail.com")){
            flag = 1;
            assert(true);
        }
        assertEquals(0,flag);
    }


}