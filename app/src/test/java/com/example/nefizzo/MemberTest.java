package com.example.nefizzo;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    Member testMember = new Member("Elif","Elif","Goral","elif-goral99@hotmail.com","123456","female");

    @org.junit.jupiter.api.Test
    void getGender() {
        assertEquals("female",testMember.getGender());
    }

    @org.junit.jupiter.api.Test
    void setGender() {
        testMember.setGender("female");
        assertEquals("female",testMember.getGender());
    }

    @org.junit.jupiter.api.Test
    void getUsername() {
        assertEquals("Elif",testMember.getUsername());
    }

    @org.junit.jupiter.api.Test
    void setUsername() {
        testMember.setUsername("newUserName");
        assertEquals("newUserName",testMember.getUsername());
    }

    @org.junit.jupiter.api.Test
    void getName() {
        assertEquals("Elif",testMember.getName());
    }

    @org.junit.jupiter.api.Test
    void setName() {
        testMember.setName("newName");
        assertEquals("newName",testMember.getName());
    }

    @org.junit.jupiter.api.Test
    void getSurname() {
        assertEquals("Goral",testMember.getSurname());
    }

    @org.junit.jupiter.api.Test
    void setSurname() {
        testMember.setSurname("newSurname");
        assertEquals("newSurname",testMember.getSurname());
    }

    @org.junit.jupiter.api.Test
    void getMailAddress() {
        assertEquals("elif-goral99@hotmail.com",testMember.getMailAddress());
    }

    @org.junit.jupiter.api.Test
    void setMailAddress() {
        testMember.setMailAddress("newMail");
        assertEquals("newMail",testMember.getMailAddress());
    }

    @org.junit.jupiter.api.Test
    void getPassword() {
        assertEquals("123456",testMember.getPassword());
    }

    @org.junit.jupiter.api.Test
    void setPassword() {
        testMember.setPassword("newPass");
        assertEquals("newPass",testMember.getPassword());
    }
}