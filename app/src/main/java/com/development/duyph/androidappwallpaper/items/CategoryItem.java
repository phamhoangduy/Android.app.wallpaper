package com.development.duyph.androidappwallpaper.items;

/**
 * Created by nguoi on 12/25/2015.
 */
public class CategoryItem implements IWallPager {
    private String id;
    private String title;

    public CategoryItem() {
    }

    public CategoryItem(String name) {
        this.title = name;
    }

    public CategoryItem(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return title;
    }
}
