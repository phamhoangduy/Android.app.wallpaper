package com.development.duyph.androidappwallpaper.common;

import com.development.duyph.androidappwallpaper.items.CategoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguoi on 12/19/2015.
 */
public class Mock {
    public static List<CategoryItem> fakeCategory(){
        List<CategoryItem> category = new ArrayList<>();
        category.add(new CategoryItem("SUMMBER"));
        category.add(new CategoryItem("ANIMALS"));
        category.add(new CategoryItem("CARTOON"));
        category.add(new CategoryItem("MOVIES"));
        category.add(new CategoryItem("FANTASCY"));
        category.add(new CategoryItem("FUNNY"));
        category.add(new CategoryItem("FOODY"));

        return category;
    }
}
