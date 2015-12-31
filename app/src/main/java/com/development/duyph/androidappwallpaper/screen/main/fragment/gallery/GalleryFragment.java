package com.development.duyph.androidappwallpaper.screen.main.fragment.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.development.duyph.androidappwallpaper.R;
import com.development.duyph.androidappwallpaper.common.CategoryManager;
import com.development.duyph.androidappwallpaper.common.Constant;
import com.development.duyph.androidappwallpaper.common.Mock;
import com.development.duyph.androidappwallpaper.items.CategoryItem;
import com.development.duyph.androidappwallpaper.screen.BaseFragment;
import com.development.duyph.androidappwallpaper.utils.Utils;

import java.util.List;

/**
 * Created by nguoi on 12/20/2015.
 */
public class GalleryFragment extends BaseFragment {

    String[] TAB_DATA = new String[]{"GALLERY 1", "GALLERY 2", "GALLERY 3", "GALLERY 4", "GALLERY 5", "GALLERY 6"};
    CategoryGalleryFragment mCategoryGalleryFragment;
    ViewPager viewPager;
    List<CategoryItem> mCategories;

    public static GalleryFragment newInstance(int num) {

        Bundle args = new Bundle();
        args.putInt(Constant.INTENT_FRAGMENT_NUMBER, num);
        GalleryFragment fragment = new GalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategories  = CategoryManager.getInstance().getCategories();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_gallery_fragment, container, false);
        //set up tab with custom tab item
        viewPager = (ViewPager) v.findViewById(R.id.view_pager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabs(tabLayout, viewPager);
        return v;
    }


    private void setupTabs(TabLayout tabLayout, final ViewPager viewPager) {
        if (mCategories == null) {
            return;
        }
        for (int i = 0; i < mCategories.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(R.layout.layout_custom_tab);
                View view = tab.getCustomView();
                if (view != null) {
                    TextView tv = ((TextView) view.findViewById(R.id.tab));
                    //tv.setTypeface(FontHelper.getInstance().getTypeface(FontHelper.ROBOTO_MEDIUM));
                    tv.setText(mCategories.get(i).getName());
                    if (tab.isSelected()) {
                        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_sub_header_active));
                    } else {
                        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_sub_header_normal));
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
                    tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_sub_header_active));
                }
                if (pos != viewPager.getCurrentItem()) {
                    viewPager.setCurrentItem(pos);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                View view = tab.getCustomView();
                if (view != null) {
                    TextView tv = (TextView) view.findViewById(R.id.tab);
                    tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_sub_header_normal));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public void findPositionWithTabName(String tabName) {
        if(!Utils.isListValid(mCategories)){
            return;
        }

        for (int i = 0; i < mCategories.size(); i++) {
            if (mCategories.get(i).getName().equals(tabName)) {
                scrollToTab(i);
                break;
            }
        }
    }


    private void scrollToTab(int position) {
        viewPager.setCurrentItem(position);
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
            List<Fragment> fragments = fm.getFragments();
            if (fragments != null) {
                mCategoryGalleryFragment = (CategoryGalleryFragment) fragments.get(0);
            }
        }

        @Override
        public Fragment getItem(int position) {
            if(Utils.isListValid(mCategories)){
                return CategoryGalleryFragment.newInstance(mCategories.get(position).getId());
            }
            return CategoryGalleryFragment.newInstance(null);
        }

        @Override
        public int getCount() {
            int size = 0;
            if (Utils.isListValid(mCategories)) {
                size = mCategories.size();
            }
            return size;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (!Utils.isListValid(mCategories)) {
                return "";
            }
            return mCategories.get(position).getName();
        }
    }
}
