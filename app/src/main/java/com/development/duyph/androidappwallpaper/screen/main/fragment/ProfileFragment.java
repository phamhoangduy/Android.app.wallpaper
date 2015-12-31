package com.development.duyph.androidappwallpaper.screen.main.fragment;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.development.duyph.androidappwallpaper.BuildConfig;
import com.development.duyph.androidappwallpaper.R;
import com.development.duyph.androidappwallpaper.adapters.WallPagerAdapter;
import com.development.duyph.androidappwallpaper.common.CategoryManager;
import com.development.duyph.androidappwallpaper.common.Constant;
import com.development.duyph.androidappwallpaper.common.Profile;
import com.development.duyph.androidappwallpaper.items.CategoryItem;
import com.development.duyph.androidappwallpaper.items.IWallPager;
import com.development.duyph.androidappwallpaper.screen.BaseFragment;
import com.development.duyph.androidappwallpaper.screen.main.MainActivity;
import com.development.duyph.androidappwallpaper.screen.main.fragment.gallery.GalleryFragment;
import com.development.duyph.androidappwallpaper.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguoi on 12/19/2015.
 */
public class ProfileFragment extends BaseFragment implements WallPagerAdapter.OnWallPapersItemClickListener {
    TextView tvDeviceName, tvAndroidName, tvDisplayPixel, tvAndroidVersion;
    RecyclerView mRecyclerView;
    WallPagerAdapter mWallPagerAdapter;
    List<CategoryItem> mCategories;

    public static ProfileFragment newInstance(int num) {

        Bundle args = new Bundle();
        args.putInt(Constant.INTENT_FRAGMENT_NUMBER, num);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategories = CategoryManager.getInstance().getCategories();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_profile_fragment, container, false);


        tvDeviceName = (TextView) v.findViewById(R.id.tv_device_name);
        tvAndroidName = (TextView) v.findViewById(R.id.tv_os_name);
        tvAndroidVersion = (TextView) v.findViewById(R.id.tv_version);
        tvDisplayPixel = (TextView) v.findViewById(R.id.tv_witdh_height);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //set data
        tvAndroidName.setText((BuildConfig.VERSION_NAME.toUpperCase()));
        tvAndroidVersion.setText(String.format(getResources()
                .getString(R.string.version_code), Build.VERSION.RELEASE));
        tvDeviceName.setText(Build.MODEL);

        int[] displays = Profile.getDisplay(getActivity());
        tvDisplayPixel.setText(String.format(getResources()
                .getString(R.string.display_device), displays[0], displays[1]));
        initAdapter();

        return v;
    }

    protected void initAdapter() {
        if(!Utils.isListValid(mCategories)){
            return;
        }
        if (mWallPagerAdapter == null) {
            mWallPagerAdapter = new WallPagerAdapter();
            mWallPagerAdapter.setOnWallPapersItemClickListener(this);
        }
        mRecyclerView.setAdapter(mWallPagerAdapter);
        mWallPagerAdapter.setData(resolverData(mCategories));
    }


    public List<IWallPager> resolverData(List<CategoryItem> items) {
        List<IWallPager> iWallPagers = new ArrayList<>();
        iWallPagers.addAll(items);
        return iWallPagers;
    }

    @Override
    public void onClick(RecyclerView.ViewHolder holder, View v, int pos) {
        if(!Utils.isListValid(mCategories)){
            return;
        }
        List<Fragment> fragments = getFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof GalleryFragment && mCategories.get(pos).getName() != null) {
                    ((MainActivity)getActivity()).goToTab(1);///this position different "pos".
                    ((GalleryFragment) fragment).findPositionWithTabName(mCategories.get(pos).getName());
                    break;
                }
            }
        } else {
            throw new IllegalStateException("fragment is null at onClick method ProfileFragment Class");
        }
        //Snackbar.make(v, "Hello World", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onChildClick(RecyclerView.ViewHolder parentHolder, View parentView,
                             RecyclerView.ViewHolder childHolder, View childView, int pos) {

    }
}
