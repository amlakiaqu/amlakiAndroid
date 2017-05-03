package com.example.hamzawy.amlaki.connection;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HamzawY on 4/19/17.
 */


public class VolleyRequest<T> extends JsonRequest<T> {
    Class <T> responseType;
    private Response.Listener<T> listener;
    private Gson gson = new Gson();


    public VolleyRequest(int method, String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener , Class <T> response) {
        super(method, url, requestBody, listener, errorListener);
        this.responseType = response;
        this.listener = listener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, responseType),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        Log.d("", "deliverResponse: ");
        listener.onResponse(response);
    }

//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        Map<String , String> headers = new HashMap<>();
////        headers.put("Accept","application/json; charset=utf-8");
//        headers.put("Content-Type", "application/json; charset=utf-8");
//
//        return headers;
//    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }
}
