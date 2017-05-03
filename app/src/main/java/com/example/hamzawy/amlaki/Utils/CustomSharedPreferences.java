package com.example.hamzawy.amlaki.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.example.hamzawy.amlaki.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpCookie;
import java.util.ArrayList;

public class CustomSharedPreferences {


    private static Context context = null;
    private static SharedPreferences appSharedPrefs;
    private static Gson gson;

    private CustomSharedPreferences() {

        gson = new Gson();

    }

    public static void setContext(Context context) {
        CustomSharedPreferences.context = context.getApplicationContext();
        appSharedPrefs = context.getSharedPreferences("harri",
                Context.MODE_PRIVATE);
    }

    public static Context getApplicationContext() {
        return context;
    }

    public static Gson getGsonInstance() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static SharedPreferences getSharedPrefsInstance() {
        if (appSharedPrefs == null && CustomSharedPreferences.context != null) {
            appSharedPrefs = context.getSharedPreferences("harri",
                    Context.MODE_PRIVATE);
        }
        return appSharedPrefs;
    }

    /**
     * @param keyValue   Constant name to get/put Harri user ConstantData.HARRI_USER.
     * @param userToSave object to save it in preferences.
     */
    public static void putUserData(String keyValue, User userToSave) {
        try {
            Object obj = userToSave;

            SharedPreferences appSharedPrefs = CustomSharedPreferences
                    .getSharedPrefsInstance();
            Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = CustomSharedPreferences.getGsonInstance();

            String json = gson.toJson(obj);
            prefsEditor.putString(keyValue, json);
            prefsEditor.commit();
        } catch (Exception e) {
                Log.e("", e.getMessage(), e);
        }

    }


    /**
     * @param keyValue    Constant name to get/put user data ConstantData.HARRI_USER.
     * @param defultValue the default value to return.
     * @return
     */
    public static User getUserData(String keyValue, User defultValue) {
        try {

            SharedPreferences appSharedPrefs = CustomSharedPreferences
                    .getSharedPrefsInstance();
            Gson gson = CustomSharedPreferences.getGsonInstance();

            String json = appSharedPrefs.getString(keyValue, "");
            if (json.equals("")) {
                return defultValue;
            } else {
                User obj = gson.fromJson(json, User.class);

                return obj;
            }
        } catch (Exception e) {
                Log.e("", e.getMessage(), e);
            return defultValue;
        }

    }




    /**
     * To remove all user stored data and preferences.
     */
    public static void clearSession() {
        putUserData("USER", null);
    }
}
