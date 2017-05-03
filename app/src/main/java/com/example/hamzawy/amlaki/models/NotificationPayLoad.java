package com.example.hamzawy.amlaki.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by HamzawY on 4/25/17.
 */

public class NotificationPayLoad implements Serializable {
    private String message;

    @SerializedName("post_id")
    private int postId;


    public void setMessages(String messages) {
        this.message = messages;
    }
    public String getMessage() {
        return message;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setMessage(String message) {

        this.message = message;
    }
}
