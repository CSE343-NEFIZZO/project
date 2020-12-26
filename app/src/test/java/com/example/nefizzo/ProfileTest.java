package com.example.nefizzo;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileTest {
    Member mem = new Member("username","name","surname","mail@hotmail.com","female");

    @Test
    public void test_getUsername(){
        assertEquals("username",mem.getUsername());
    }

    @Test
    public void test_getName(){
        assertEquals("name",mem.getName());
    }

    @Test
    public void test_getSurname(){
        assertEquals("surname",mem.getSurname());
    }

    @Test
    public void test_getMail(){
        assertEquals("mail@hotmail.com",mem.getMailAddress());
    }

    @Test
    public void test_getGender(){
        assertEquals("female",mem.getGender());
    }


}