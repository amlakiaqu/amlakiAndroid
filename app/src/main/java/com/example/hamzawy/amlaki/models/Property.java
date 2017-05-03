package com.example.hamzawy.amlaki.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by HamzawY on 4/25/17.
 */

public class Property implements Serializable {
    private String title;
    private String code;
    private Pivot pivot;
    private String value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
