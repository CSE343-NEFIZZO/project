package com.example.nefizzo;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginScreenTest {

    Member mem = new Member("username","name","surname","mail@hotmail.com","123456","female");
    Member notExistMember = new Member("elif","elif","elif","elif@hotmail.com","789789","female");
    Member existMember = new Member("username","name","surname","mail@hotmail.com","123456","female");

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
    public void test_empty_password(){
        mem.setPassword("");
        String mail = "";
        assertEquals(mail,mem.getPassword());
    }

    @Test
    public void test_member_is_exist(){
        assertEquals(existMember.getPassword(),mem.getPassword());
        assertEquals(existMember.getMailAddress(),mem.getMailAddress());
    }

    @Test
    public void test_member_is_not_exist(){
        assertNotEquals(notExistMember.getPassword(),mem.getPassword());
        assertNotEquals(notExistMember.getMailAddress(),mem.getMailAddress());
    }
}