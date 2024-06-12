package com.example.easygo_travelapp.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String idUser;
    private String urlAvatar;
    private String userName;
    private String email;
    private String phone;
    private String gender;
    private String birthday;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public User(String idUser, String urlAvatar, String userName, String email, String phone, String gender, String birthday) {
        this.idUser = idUser;
        this.urlAvatar = urlAvatar;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birthday = birthday;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser='" + idUser + '\'' +
                ", urlAvatar='" + urlAvatar + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
