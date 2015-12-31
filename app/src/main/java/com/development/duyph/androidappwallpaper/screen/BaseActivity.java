package com.development.duyph.androidappwallpaper.screen;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by nguoi on 12/19/2015.
 */
public class BaseActivity extends AppCompatActivity {

    public static class TabData {
        public int iconId;
        public int selectedIconId;
        public String name;

        public TabData(int id, int selId, String name) {
            iconId = id;
            selectedIconId = selId;
            this.name = name;
        }
    }


}
