package com.example.chatapp.modules;

public class Users {
    String profilePicture, userName, mail, parrword, userId, lastMessage, status;

    public Users(){}

    public Users(String profilePicture, String userName, String mail, String parrword, String userId, String lastMessage, String status) {
        this.profilePicture = profilePicture;
        this.userName = userName;
        this.mail = mail;
        this.parrword = parrword;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.status = status;
    }

    public Users(String userName, String mail, String parrword) {
        this.userName = userName;
        this.mail = mail;
        this.parrword = parrword;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getParrword() {
        return parrword;
    }

    public void setParrword(String parrword) {
        this.parrword = parrword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
