package com.example.easygo_travelapp.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Review implements Serializable {
    private String idUser;
    private String time;
    private String content;
//    private List<Review> replies;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

//    public List<Review> getReplies() {
//        return replies;
//    }
//
//    public void setReplies(List<Review> replies) {
//        this.replies = replies;
//    }

    public Review() {
    }

    public Review(String idUser, String time, String content) {
        this.idUser = idUser;
        this.time = time;
        this.content = content;
//        this.replies = replies;
    }

    @Override
    public String toString() {
        return "Review{" +
                ", idUser='" + idUser + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
//                ", replies=" + replies +
                '}';
    }

}