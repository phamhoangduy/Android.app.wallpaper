package com.development.duyph.androidappwallpaper.screen.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.development.duyph.androidappwallpaper.R;
import com.development.duyph.androidappwallpaper.screen.BaseActivity;
import com.development.duyph.androidappwallpaper.screen.main.fragment.ProfileFragment;
import com.development.duyph.androidappwallpaper.screen.main.fragment.gallery.GalleryFragment;

public class MainActivity extends BaseActivity {
    ViewPager viewPager;
    protected TabData[] TAB_DATA = new TabData[]{
            new TabData(R.drawable.device_normal,R.drawable.device_active,"DEVICE"),
            new TabData(R.drawable.gallery_normal,R.drawable.gallery_active,"GALLERY")
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up toolbar
//        initToolbar();

        //set up tab with custom tab item
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        setupTabs(tabLayout, viewPager);
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
        }
    }

    public void goToTab(int pos){
        viewPager.setCurrentItem(pos);
    }

    /**
     * Set up tab layout
     *
     * @param tabLayout the tab layout
     */
    private void setupTabs(TabLayout tabLayout, final ViewPager pager) {
        for (int i = 0; i < TAB_DATA.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(R.layout.layout_custom_tab);
                View view = tab.getCustomView();
                if (view != null) {
                    TextView tv = ((TextView) view.findViewById(R.id.tab));
                    tv.setText(TAB_DATA[i].name);
                    if (tab.isSelected()) {
                        tv.setTextColor(ContextCompat.getColor(this, R.color.text_header_active));
                        tv.setCompoundDrawablesWithIntrinsicBounds(0, TAB_DATA[i].selectedIconId, 0, 0);
                    } else {
                        tv.setTextColor(ContextCompat.getColor(this, R.color.text_header_normal));
                        tv.setCompoundDrawablesWithIntrinsicBounds(0, TAB_DATA[i].iconId, 0, 0);
                    }
                }
            }
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                View view = tab.getCustomView();
                if (view != null) {
                    TextView tv = (TextView) view.findViewById(R.id.tab);
                    tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_header_active));
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, TAB_DATA[pos].selectedIconId, 0, 0);
                }
                pager.setCurrentItem(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                View view = tab.getCustomView();
                if (view != null) {
                    TextView tv = (TextView) view.findViewById(R.id.tab);
                    tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_header_normal));
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, TAB_DATA[pos].iconId, 0, 0);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    private class MyAdapter extends FragmentStatePagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return ProfileFragment.newInstance(position+1);
                case 1:
                    return GalleryFragment.newInstance(position+1);
            }
            return null;
        }

        @Override
        public int getCount() {
            return TAB_DATA.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_DATA[position].name;
        }
    }

}
