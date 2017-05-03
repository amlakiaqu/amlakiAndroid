package com.example.hamzawy.amlaki.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by HamzawY on 4/25/17.
 */

public class Pivot implements Serializable{
    @SerializedName("post_id")
    private int postId;

    @SerializedName("property_id")
    private int propertyId;

    private int value;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
