package com.example.nefizzo;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InnerForumActivityTest {
    
    InnerForumModel comment = new InnerForumModel("username","comment");

    @Test
    public void test_getUsername(){
        assertEquals("username",comment.getName());
    }

    @Test
    public void test_getComment(){
        assertEquals("comment",comment.getComment());
    }

    @Test
    public void test_setUsername(){
        comment.setName("new username");
        assertEquals("new username",comment.getName());
    }

    @Test
    public void test_setComment(){
        comment.setComment("new comment");
        assertEquals("new comment",comment.getComment());
    }

}