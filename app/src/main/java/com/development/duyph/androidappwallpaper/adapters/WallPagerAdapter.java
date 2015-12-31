package com.development.duyph.androidappwallpaper.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.development.duyph.androidappwallpaper.R;
import com.development.duyph.androidappwallpaper.adapters.holder.CategoryItemHolder;
import com.development.duyph.androidappwallpaper.adapters.holder.EmptyItemHolder;
import com.development.duyph.androidappwallpaper.adapters.holder.GalleryItemHolder;
import com.development.duyph.androidappwallpaper.common.ViewType;
import com.development.duyph.androidappwallpaper.items.CategoryItem;
import com.development.duyph.androidappwallpaper.items.EmptyItem;
import com.development.duyph.androidappwallpaper.items.GalleryItem;
import com.development.duyph.androidappwallpaper.items.IWallPager;
import com.development.duyph.androidappwallpaper.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguoi on 12/25/2015.
 */
public class WallPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IWallPager> mItems;
    private OnWallPapersItemClickListener mItemClickEvent;

    /**
     * A listener for tracking click interaction
     */
    public interface OnWallPapersItemClickListener {
        /**
         * Triggered when an item of the list is clicked
         *
         * @param holder the view holder of a clicked item
         */
        void onClick(RecyclerView.ViewHolder holder, View v, int pos);

        /**
         * Triggered when a child item of the list is clicked
         *
         * @param parentHolder the parent holder
         * @param parentView   the parent view
         * @param childHolder  the child holder
         * @param childView    the child view
         */
        void onChildClick(RecyclerView.ViewHolder parentHolder, View parentView,
                          RecyclerView.ViewHolder childHolder, View childView, int pos);
    }

    public void setOnWallPapersItemClickListener(OnWallPapersItemClickListener event) {
        mItemClickEvent = event;
    }

    public WallPagerAdapter() {
        mItems = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ViewType.CATEGORY_ITEM:
                CategoryItemHolder holder = new CategoryItemHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.layout_item_category, parent, false));
                holder.setCategoryItemClickListener(mItemClickEvent);
                return holder;
            case ViewType.EMPTY_ITEM:
                return new EmptyItemHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.layout_news_view_type_empty_data, parent, false));
            case ViewType.GALLERY_ITEM:
                return new GalleryItemHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.layout_gallery_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ViewType.CATEGORY_ITEM:
                bindCategoryItemHolder(holder, position);
                break;
            case ViewType.EMPTY_ITEM:
                bindEmptyItem(holder, position);
                break;
            case ViewType.GALLERY_ITEM:
                bindGalleryItem(holder,position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 0 || position > mItems.size() - 1) {
            return ViewType.UNDEFINED;
        }
        final IWallPager item = mItems.get(position);
        if (item instanceof CategoryItem) {
            return ViewType.CATEGORY_ITEM;
        } else if (item instanceof EmptyItem) {
            return ViewType.EMPTY_ITEM;
        } else if (item instanceof GalleryItem) {
            return ViewType.GALLERY_ITEM;
        }
        return ViewType.UNDEFINED;
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }


    /**
     * set item data for category holder. it used to show information of item.
     **/
    private void bindCategoryItemHolder(RecyclerView.ViewHolder holder, int pos) {
        final IWallPager item = mItems.get(pos);
        if (!(holder instanceof CategoryItemHolder) || !(item instanceof CategoryItem)) {
            return;
        }
        CategoryItemHolder categoryItemHolder = (CategoryItemHolder) holder;
        categoryItemHolder.setDataItem((CategoryItem) item);
    }

    private void bindEmptyItem(RecyclerView.ViewHolder holder, int pos) {
        final IWallPager item = mItems.get(pos);
        if (!(holder instanceof EmptyItemHolder) || !(item instanceof EmptyItem)) {
            return;
        }
        ((EmptyItemHolder) holder).setDataItem((EmptyItem) item);
    }

    private void bindGalleryItem(RecyclerView.ViewHolder holder, int pos) {
        final IWallPager item = mItems.get(pos);
        if (!(holder instanceof GalleryItemHolder) || !(item instanceof GalleryItem)) {
            return;
        }
        ((GalleryItemHolder) holder).setDataItem((GalleryItem) item);
    }

    public void setData(List<IWallPager> items) {
        if (!Utils.isListValid(items)) {
            return;
        }

        clearDataOnly();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    protected void clearDataOnly() {
        if (mItems != null) {
            mItems.clear();
        }
    }
}
