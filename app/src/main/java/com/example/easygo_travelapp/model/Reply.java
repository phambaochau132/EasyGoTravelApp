package com.example.easygo_travelapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Reply implements Serializable {
    private int idReview;
    private String avatar;
    private String fullName;
    private String time;
    private String content;

    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Reply() {
    }

    public Reply(int idReview, String avatar, String fullName, String time, String content) {
        this.idReview = idReview;
        this.avatar = avatar;
        this.fullName = fullName;
        this.time = time;
        this.content = content;}

    @Override
    public String toString() {
        return "Reply{" +
                "idReview=" + idReview +
                ", avatar='" + avatar + '\'' +
                ", fullName='" + fullName + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' + '}';
    }
}
