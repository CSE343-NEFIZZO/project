package com.example.nefizzo;

public class Member {

    /**
     * username of member
     */
    private String username;

    /**
     * Name of member
     */
    private String name;
    /**
     * Surname of member
     */
    private String surname;
    /**
     * mail address of member
     */
    private String mailAddress;
    /**
     * password of member
     */
    private String password;

    /**
     * gender of member
     */
    private String gender;


    public Member() {
    }

    public Member(String username, String name, String surname, String mailAddress, String gender) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.mailAddress = mailAddress;
        this.gender = gender;
    }


    /**
     * That method returns the gender.
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * That methods set the gender.
     * @param gender member's gender.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * That method returns the username.
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * That methods set the name.
     * @param username member's username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * That method returns the name.
     * @return name
     */
    public String getName() {
        return name;
    }


    /**
     * That methods set the name.
     * @param name member's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * That method returns the surname.
     * @return surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * That method set the surname.
     * @param surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * That method returns the mail address.
     * @return mail address of member.
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     *  That method sets the mail address.
     * @param mailAddress member's mail address.
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }



}
