package com.example.hamzawy.amlaki.controllers;


import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hamzawy.amlaki.R;
import com.example.hamzawy.amlaki.Utils.CustomSharedPreferences;
import com.example.hamzawy.amlaki.activities.CoreActivity;
import com.example.hamzawy.amlaki.activities.MyApp;
import com.example.hamzawy.amlaki.fragments.PostDetails;
import com.example.hamzawy.amlaki.fragments.SellPosts;
import com.example.hamzawy.amlaki.models.Post;
import com.example.hamzawy.amlaki.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HamzawY on 4/25/17.
 */

public class GetPostDetails {
    private PostDetails postDetails;
    private ProgressDialog loader;
    private Activity activity;

    public GetPostDetails(PostDetails postDetails, Activity activity) {
        this.postDetails = postDetails;
        this.activity = activity;
    }


    private void showDialog(boolean show) {
        if (loader == null) {
            loader = new ProgressDialog(activity);
            loader.setCancelable(false);
            loader.setTitle(activity.getString(R.string.loading));
            loader.setIndeterminate(true);
        }
        if (show) {
            loader.show();
        } else {
            loader.dismiss();
        }
    }

    public void execute(int postId) {
        showDialog(true);
        Gson gson = new Gson();

        final String url = "http://amlaki.herokuapp.com/api/v1/posts/" + postId;
        RequestQueue requestQueue = Volley.newRequestQueue(MyApp.getAppContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showDialog(false);
                Log.i("VOLLEY", response);
                Gson gson = new Gson();
                Post post = gson.fromJson(response, Post.class);
                PostDetails postDetails = (PostDetails) ((CoreActivity)activity).getSupportFragmentManager().findFragmentByTag(PostDetails.class.toString());
                if (postDetails != null){
                    postDetails.setPostDetails(post);
                }
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showDialog(false);
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                String AUTH = "";
                if (CustomSharedPreferences.getUserData("USER", null) != null) {
                    User user = CustomSharedPreferences.getUserData("USER", null);
                    AUTH = user.getApiToken();
                }
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json; charset=utf-8");
                map.put("Authorization", "Bearer {" + AUTH + "}");

                return super.getHeaders();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    try {
                        responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }
}
