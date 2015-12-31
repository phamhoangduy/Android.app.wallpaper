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
import com.development.duyph.androidappwallpaper.items.GalleryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguoi on 12/30/2015.
 */
public class GalleryManager {
    private static String TAG = GalleryManager.class.getSimpleName();
    private List<GalleryItem> mWallpapers;

    // Picasa JSON response node keys
    private static final String TAG_FEED = "feed", TAG_ENTRY = "entry",
            TAG_MEDIA_GROUP = "media$group",
            TAG_MEDIA_CONTENT = "media$content", TAG_IMG_URL = "url",
            TAG_IMG_WIDTH = "width", TAG_IMG_HEIGHT = "height", TAG_ID = "id",
            TAG_T = "$t";

    /////////////////////////   Categories Events   ///////////////////////////
    private GalleryEvents mCallback;
    public interface GalleryEvents{
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

    public void registerGalleryEventsListener(GalleryEvents listener){
        this.mCallback = listener;
    }
    //////////////////////////////////////////////////////////////////////////

    private static GalleryManager sInstance = new GalleryManager();

    public static GalleryManager getsInstance() {
        return sInstance;
    }

    public boolean hasWallpapersData(){
        return mWallpapers != null && mWallpapers.size() > 0;
    }

    public void loadingWallpapersFromServer(final Context context, String url){
        mWallpapers = new ArrayList<>();

        Log.d(TAG, "photos request url: " + url);

        /**
         * Making volley's json object request to fetch list of photos of an
         * album
         * */
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "List of photos json reponse: " + response.toString());
                try {
                    // Parsing the json response
                    JSONArray entry = response.getJSONObject(TAG_FEED)
                            .getJSONArray(TAG_ENTRY);

                    // looping through each photo and adding it to list
                    // data set
                    for (int i = 0; i < entry.length(); i++) {
                        JSONObject photoObj = (JSONObject) entry.get(i);
                        JSONArray mediaContentArry = photoObj
                                .getJSONObject(TAG_MEDIA_GROUP)
                                .getJSONArray(TAG_MEDIA_CONTENT);

                        if (mediaContentArry.length() > 0) {
                            JSONObject mediaObj = (JSONObject) mediaContentArry
                                    .get(0);

                            String url = mediaObj
                                    .getString(TAG_IMG_URL);

                            String photoJson = photoObj.getJSONObject(
                                    TAG_ID).getString(TAG_T)
                                    + "&imgmax=d";

                            int width = mediaObj.getInt(TAG_IMG_WIDTH);
                            int height = mediaObj
                                    .getInt(TAG_IMG_HEIGHT);

                            GalleryItem p = new GalleryItem(photoJson, url, width, height);

                            // Adding the photo to list data set
                            mWallpapers.add(p);

                            Log.d(TAG, "Photo: " + url + ", w: "
                                    + width + ", h: " + height);
                        }
                    }

                    // Notify list adapter about dataset changes. So
                    // that it renders grid again
                    //adapter.notifyDataSetChanged();

                    // Hide the loader, make grid visible
                    //pbLoader.setVisibility(View.GONE);
                    //gridView.setVisibility(View.VISIBLE);
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
                Log.e(TAG, "Error: " + error.getMessage());
                // unable to fetch wallpapers
                // either google username is wrong or
                // devices doesn't have internet connection
                mCallback.onErrorResponse(false);
                Toast.makeText(context,
                        context.getString(R.string.msg_wall_fetch_error),
                        Toast.LENGTH_LONG).show();
            }
        });


        // Remove the url from cache
        AppController.getInstance().getRequestQueue().getCache().remove(url);

        // Disable the cache for this url, so that it always fetches updated
        // json
        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    /**
     * Get all the wallpapers available
     *
     * @return the list of wallpapers
     */
    public List<GalleryItem> getWallpapers() {
        return mWallpapers;
    }
}
