package com.development.duyph.androidappwallpaper.adapters.holder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.development.duyph.androidappwallpaper.R;
import com.development.duyph.androidappwallpaper.app.AppController;
import com.development.duyph.androidappwallpaper.items.GalleryItem;

/**
 * Created by nguoi on 12/30/2015.
 */
public class GalleryItemHolder extends RecyclerView.ViewHolder {
    NetworkImageView thumbNail;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public GalleryItemHolder(View itemView) {
        super(itemView);
        thumbNail = (NetworkImageView) itemView.findViewById(R.id.image_view);
    }

    public void setDataItem(GalleryItem item){
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        thumbNail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbNail.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        thumbNail.setImageUrl(item.getUrl(), imageLoader);

    }
}
