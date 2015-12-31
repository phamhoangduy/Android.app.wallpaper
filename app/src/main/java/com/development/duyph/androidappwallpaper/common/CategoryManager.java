package com.development.duyph.androidappwallpaper.common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.development.duyph.androidappwallpaper.R;
import com.development.duyph.androidappwallpaper.app.AppController;
import com.development.duyph.androidappwallpaper.items.CategoryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguoi on 12/30/2015.
 */
public class CategoryManager {
    // Hold all categories
    private List<CategoryItem> mCategories;
    private static CategoryManager sInstance = new CategoryManager();

    public static CategoryManager getInstance() {
        return sInstance;
    }

    private static final String TAG = CategoryManager.class.getSimpleName();
    private static final String TAG_FEED = "feed", TAG_ENTRY = "entry",
            TAG_GPHOTO_ID = "gphoto$id", TAG_T = "$t",
            TAG_ALBUM_TITLE = "title";


    /////////////////////////   Categories Events   ///////////////////////////
    CategoriesEvents mCallback;

    public interface CategoriesEvents {

        /**
         * Trigger when a response json task is success.
         */
        void onSuccessResponse();

        /**
         * Trigger when a response json task is failed.
         *
         * @param hasCategories true if categories has data
         *                      false if it is not.
         */
        void onErrorResponse(boolean hasCategories);
    }

    public void registerCategoriesEventCallback(CategoriesEvents listener) {
        this.mCallback = listener;
    }

    ///////////////////////////////////////////////////

    public boolean hasCategoryData() {
        return mCategories != null && mCategories.size() > 0;
    }


    public void loadingCategoriesFromServer(final Context context, String url) {
        mCategories = new ArrayList<>();
        Log.d(TAG, "Albums request url: " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Albums Response: " + response.toString());
                        //List<CategoryItem> albums = new ArrayList<>();

                        try {
                            // Parsing the json response
                            JSONArray entry = response.getJSONObject(TAG_FEED)
                                    .getJSONArray(TAG_ENTRY);

                            // loop through albums nodes and add them to album
                            // list
                            for (int i = 0; i < entry.length(); i++) {
                                JSONObject albumObj = (JSONObject) entry.get(i);
                                // album id
                                String albumId = albumObj.getJSONObject(
                                        TAG_GPHOTO_ID).getString(TAG_T);

                                // album title
                                String albumTitle = albumObj.getJSONObject(
                                        TAG_ALBUM_TITLE).getString(TAG_T);

                                CategoryItem album = new CategoryItem();
                                album.setId(albumId);
                                album.setTitle(albumTitle);

                                // add album to list
                                mCategories.add(album);

                                Log.d(TAG, "Album Id: " + albumId + ", Album Title: " + albumTitle);
                            }

                            Log.d(TAG, "arrays: " + mCategories.size());

                            // Store albums in shared pref
                            AppController.getInstance().getPrefManger().storeCategories(mCategories);
                            mCallback.onSuccessResponse();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context,
                                    context.getString(R.string.msg_unknown_error),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Volley Error: " + error.getMessage());

                        // show error toast
                        Toast.makeText(context,
                                context.getString(R.string.splash_error),
                                Toast.LENGTH_LONG).show();

                        // Unable to fetch albums
                        // check for existing Albums data in Shared Preferences
                        if (AppController.getInstance().getPrefManger()
                                .getCategories() != null && AppController.getInstance().getPrefManger()
                                .getCategories().size() > 0) {

                            //connect to server is failed. list categories in share reference's has data.
                            mCallback.onErrorResponse(true);
                        } else {
                            // Albums data not present in the shared preferences
                            // Launch settings activity, so that user can modify
                            // the settings
                            mCallback.onErrorResponse(false);
                        }

                    }
                });
        // disable the cache for this request, so that it always fetches updated
        // json
        jsObjRequest.setShouldCache(false);

        // Making the request
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }


    /**
     * Get all the categories available
     *
     * @return the list of category
     */
    public List<CategoryItem> getCategories() {
        if(!hasCategoryData()){
            mCategories = AppController.getInstance().getPrefManger().getCategories();
        }
        return mCategories;
    }
}
