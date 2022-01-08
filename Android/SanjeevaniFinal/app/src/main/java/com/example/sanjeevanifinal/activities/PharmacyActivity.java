package com.example.sanjeevanifinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sanjeevanifinal.R;
import com.example.sanjeevanifinal.utils.BottomNavigationHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

public class PharmacyActivity extends AppCompatActivity {

    public int ActivityNum = 2;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);
        setUpBottomNavigationView();
        initialiseMapinWebview();
    }

    private void initialiseMapinWebview() {
        webView = findViewById(R.id.webview);
        String url = "https://www.google.com/maps/search/pharmacies+near+me/@25.2951328,82.9966111,16z/data=!3m1!4b1";
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
    }

    private void setUpBottomNavigationView(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavViewBar);
        bottomNavigationView.setItemIconTintList(null);
        BottomNavigationHelper.enableNavigation(PharmacyActivity.this,bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ActivityNum);
        menuItem.setChecked(true);
    }

}