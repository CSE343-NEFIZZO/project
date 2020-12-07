package com.example.nefizzo;

import org.junit.Test;

import static org.junit.Assert.*;

public class AddForumTest {

    public OuterForumModel forum = new OuterForumModel("forum title", "username", "caption", "image url");

    @Test
    public void test_usernameExists() {
        String username = "username";
        assertEquals(username, forum.getUsername());
    }

    @Test
    public void test_usernameDoesNotExist() {
        String username = "aa";
        assertNotEquals(username, forum.getUsername());
    }

    @Test
    public void test_getUsername() {
        assertEquals("username", forum.getUsername());
    }

    @Test
    public void test_getForumTitle() {
        assertEquals("forum title", forum.getForumTitle());
    }

    @Test
    public void test_getCaption() {
        assertEquals("caption", forum.getCaption());
    }

    @Test
    public void test_getImageUrl() {
        assertEquals("image url", forum.getImageUrl());
    }

    @Test
    public void test_setUsername() {
        forum.setUsername("new user");
        assertEquals("new user", forum.getUsername());
    }

    @Test
    public void test_setForumTitle() {
        forum.setForumTitle("new title");
        assertEquals("new title", forum.getForumTitle());
    }

    @Test
    public void test_setCaption() {
        forum.setCaption("new caption");
        assertEquals("new caption", forum.getCaption());
    }

    @Test
    public void test_setImageUrl() {
        forum.setImageUrl("new url");
        assertEquals("new url", forum.getImageUrl());
    }

}