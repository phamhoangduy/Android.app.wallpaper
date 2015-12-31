package com.development.duyph.androidappwallpaper.adapters.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.development.duyph.androidappwallpaper.R;
import com.development.duyph.androidappwallpaper.adapters.WallPagerAdapter;
import com.development.duyph.androidappwallpaper.items.CategoryItem;

/**
 * Created by nguoi on 12/25/2015.
 */
public class CategoryItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tvCategoryName;
    private WallPagerAdapter.OnWallPapersItemClickListener mItemClickEvent;

    public void setCategoryItemClickListener(WallPagerAdapter.OnWallPapersItemClickListener mItemClickEvent){
        this.mItemClickEvent = mItemClickEvent;
    }
    public CategoryItemHolder(View itemView) {
        super(itemView);
        tvCategoryName = (TextView) itemView.findViewById(R.id.tv_category);
        itemView.setOnClickListener(this);
    }

    public void setDataItem(CategoryItem item){
        tvCategoryName.setText(item.getName());
    }

    @Override
    public void onClick(View v) {
        if(mItemClickEvent!=null){
            mItemClickEvent.onClick(this,v,getAdapterPosition());
        }
    }
}
