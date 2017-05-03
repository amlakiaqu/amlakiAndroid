package com.example.hamzawy.amlaki.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;

import com.example.hamzawy.amlaki.R;
import com.example.hamzawy.amlaki.Utils.CustomSharedPreferences;
import com.example.hamzawy.amlaki.fragments.LoginFragment;
import com.example.hamzawy.amlaki.fragments.SplashScreen;

import java.util.Locale;

public class LandingActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Locale myLocale = new Locale("ar");
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        openSplashScreen();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CustomSharedPreferences.getUserData("USER",null) == null) {
                    openLoginFragment();
                } else  {
                    openCoreActivity();
                }
            }
        }, 3000);
    }

    private void openCoreActivity() {
        Intent intent = new Intent(this , CoreActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void openSplashScreen() {
        SplashScreen splashScreen = new SplashScreen();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, splashScreen).commit();
    }

    private void openLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, loginFragment).commit();
    }
}
