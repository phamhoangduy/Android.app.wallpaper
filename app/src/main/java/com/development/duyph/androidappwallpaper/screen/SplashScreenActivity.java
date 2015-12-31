package com.development.duyph.androidappwallpaper.screen;

import android.content.Intent;
import android.os.Bundle;

import com.development.duyph.androidappwallpaper.R;
import com.development.duyph.androidappwallpaper.app.AppController;
import com.development.duyph.androidappwallpaper.common.CategoryManager;
import com.development.duyph.androidappwallpaper.screen.main.MainActivity;

/**
 * Created by nguoi on 12/27/2015.
 */
public class SplashScreenActivity extends BaseActivity implements CategoryManager.CategoriesEvents {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //pBar = (MyProgressBar) findViewById(R.id.progressBar);
        //pBar.startAnimation();

        String url = getString(R.string.base_url) + AppController.getInstance()
                .getPrefManger().getGoogleUserName() + "?" + getString(R.string.picasa_albums);
        CategoryManager.getInstance().loadingCategoriesFromServer(this, url);
        CategoryManager.getInstance().registerCategoriesEventCallback(this);

    }

    @Override
    public void onSuccessResponse() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        // closing splash activity
        finish();
    }

    @Override
    public void onErrorResponse(boolean hasCategories) {
        Intent intent;
        if (hasCategories) {
            intent = new Intent(this, MainActivity.class);
        } else {
            //in this case, we show MainActivity temp.
            intent = new Intent(this, MainActivity.class);
            // clear all the activities
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(intent);
        // closing splash activity
        finish();
    }
}
