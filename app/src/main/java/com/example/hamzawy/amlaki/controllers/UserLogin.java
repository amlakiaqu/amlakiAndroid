package com.example.hamzawy.amlaki.controllers;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hamzawy.amlaki.R;
import com.example.hamzawy.amlaki.activities.MyApp;
import com.example.hamzawy.amlaki.fragments.LoginFragment;
import com.example.hamzawy.amlaki.models.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HamzawY on 4/25/17.
 */

public class UserLogin {
    private User user;
    private LoginFragment loginFragment;
    private ProgressDialog loader;
    private Activity activity;
    public UserLogin(User user , LoginFragment loginFragment , Activity activity){
        this.user = user;
        this.loginFragment = loginFragment;
        this.activity = activity;
    }

    private void showDialog(boolean show){
        if (loader == null){
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

    public void execute(){
        showDialog(true);
        Gson gson = new Gson();

        final String reqBody =  gson.toJson(user);
        String url = "http://amlaki.herokuapp.com/api/v1/login";
        RequestQueue requestQueue = Volley.newRequestQueue(MyApp.getAppContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
                Gson gson = new Gson();
                User user = gson.fromJson(response , User.class);
                loginFragment.loginSuccess(user);
                showDialog(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                showDialog(false);
                AlertDialog alertDialog = new AlertDialog.Builder(MyApp.getAppContext()).create();
                alertDialog.setTitle(activity.getString(R.string.error));
                alertDialog.setMessage(activity.getString(R.string.invalid_username_or_password));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, activity.getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return reqBody == null ? null : reqBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", reqBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String , String>map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("application/json", "application/json; charset=utf-8");
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
