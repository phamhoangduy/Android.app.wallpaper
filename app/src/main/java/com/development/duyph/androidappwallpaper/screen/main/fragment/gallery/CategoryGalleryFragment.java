package com.development.duyph.androidappwallpaper.screen.main.fragment.gallery;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.development.duyph.androidappwallpaper.R;
import com.development.duyph.androidappwallpaper.adapters.WallPagerAdapter;
import com.development.duyph.androidappwallpaper.common.Constant;
import com.development.duyph.androidappwallpaper.common.GalleryManager;
import com.development.duyph.androidappwallpaper.items.EmptyItem;
import com.development.duyph.androidappwallpaper.items.GalleryItem;
import com.development.duyph.androidappwallpaper.items.IWallPager;
import com.development.duyph.androidappwallpaper.screen.BaseFragment;
import com.development.duyph.androidappwallpaper.utils.PrefManager;
import com.development.duyph.androidappwallpaper.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguoi on 12/20/2015.
 */
public class CategoryGalleryFragment extends BaseFragment implements GalleryManager.GalleryEvents {
    WallPagerAdapter mWallPagerAdapter;
    RecyclerView mRecyclerView;
    List<GalleryItem> mWallpapers;
    ProgressBar mProgressBar;

    private int columnWidth;
    AsyncTask<Void, Void, List<GalleryItem>> mLoader;
    PrefManager pref;
    Utils utils;


    public static CategoryGalleryFragment newInstance(String num) {

        Bundle args = new Bundle();
        args.putString(Constant.INTENT_SELECTED_ALBUM_ID, num);
        CategoryGalleryFragment fragment = new CategoryGalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String selectedAlbumId = getArguments().getString(Constant.INTENT_SELECTED_ALBUM_ID, null);
        String url = getString(R.string.base_url)
                + Constant.PICASA_USER
                + getString(R.string.picasa_albums_photo_start)
                + selectedAlbumId
                + "?" + getString(R.string.picasa_album_photo_end);
        if (!Utils.isListValid(mWallpapers)) {
            GalleryManager.getsInstance().loadingWallpapersFromServer(getActivity(), url);
        }
        GalleryManager.getsInstance().registerGalleryEventsListener(this);

        pref = new PrefManager(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_category_gallery_fragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter();
    }

    protected void initAdapter() {
        if (mWallPagerAdapter == null) {
            mWallPagerAdapter = new WallPagerAdapter();
        }
        
        //if recyclerView show messages recyclerview No adapter attached; skipping layout
        // when it never setAdapter.
        mRecyclerView.setAdapter(mWallPagerAdapter);
    }

    public List<IWallPager> resolverData(List<GalleryItem> galleryItems) {
        List<IWallPager> iWallPagers = new ArrayList<>();
        if (Utils.isListValid(galleryItems)) {
            iWallPagers.addAll(galleryItems);
        }
        if (iWallPagers != null && iWallPagers.size() < 1) {
            iWallPagers.add(new EmptyItem());
        }
        return iWallPagers;
    }

    @Override
    public void onSuccessResponse() {
        mLoader = new AsyncTask<Void, Void, List<GalleryItem>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected List<GalleryItem> doInBackground(Void... params) {
                mWallpapers = GalleryManager.getsInstance().getWallpapers();
                return mWallpapers;
            }

            @Override
            protected void onPostExecute(List<GalleryItem> galleryItems) {
                super.onPostExecute(galleryItems);
                mProgressBar.setVisibility(View.GONE);
                mWallPagerAdapter.setData(resolverData(galleryItems));
            }
        };
        mLoader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mLoader != null) {
            mLoader.cancel(true);
        }
    }

    @Override
    public void onErrorResponse(boolean hasCategories) {

    }
}
