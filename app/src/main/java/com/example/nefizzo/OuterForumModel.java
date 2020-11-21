package com.example.nefizzo;

public class OuterForumModel {

    String forumTitle;
    String username;
    String caption;
    String comments=" ";

    public OuterForumModel(String forumTitle) {
        this.forumTitle = forumTitle;
    }

        public OuterForumModel(String forumTitle, String username, String caption) {
            this.forumTitle = forumTitle;
            this.username = username;
            this.caption = caption;
        }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getForumTitle() {
        return forumTitle;
    }

    public void setForumTitle(String forumTitle) {
        this.forumTitle = forumTitle;
    }

}
