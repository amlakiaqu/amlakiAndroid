package com.example.hamzawy.amlaki.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hamzawy.amlaki.R;
import com.example.hamzawy.amlaki.Utils.CustomSharedPreferences;
import com.example.hamzawy.amlaki.fragments.PostDetails;
import com.example.hamzawy.amlaki.fragments.SellPosts;

import java.util.Locale;

public class CoreActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);

        Configuration config = getBaseContext().getResources().getConfiguration();
        String lang = "ar";
        if (! "".equals(lang) && ! config.locale.getLanguage().equals(lang))
        {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

     }


    @Override
    protected void onStart() {
        super.onStart();
        openSellPosts();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(intent.hasExtra("POST_ID")) {
            openPostDetails(intent.getIntExtra("POST_ID",0));
        }
    }


    public void openPostDetails(int id) {
        PostDetails postDetails = new PostDetails();
        Bundle bundle = new Bundle();
        bundle.putInt("POST_ID", id);
        postDetails.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, postDetails, PostDetails.class.toString()).addToBackStack(PostDetails.class.toString()).commit();
    }

    private void openSellPosts() {
        SellPosts sellPosts = new SellPosts();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,sellPosts,SellPosts.class.toString()).addToBackStack(SellPosts.class.toString()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                CustomSharedPreferences.clearSession();
                openLandingActivity();
                return true;
        }
        return false;
    }

    private void openLandingActivity() {
        Intent intent = new Intent(this , LandingActivity.class);
        startActivity(intent);
        this.finish();
    }
}
