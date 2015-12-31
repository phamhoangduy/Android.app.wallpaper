package com.development.duyph.androidappwallpaper.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.development.duyph.androidappwallpaper.common.Constant;
import com.development.duyph.androidappwallpaper.items.CategoryItem;
import com.google.gson.Gson;

public class PrefManager {
	private static final String TAG = PrefManager.class.getSimpleName();

	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "WallpapersHD";

	// Google's username
	private static final String KEY_GOOGLE_USERNAME = "google_username";

	// No of grid columns
	private static final String KEY_NO_OF_COLUMNS = "no_of_columns";

	// Gallery directory name
	private static final String KEY_GALLERY_NAME = "gallery_name";

	// gallery albums key
	private static final String KEY_ALBUMS = "albums";

	public PrefManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

	}

	/**
	 * Storing google username
	 * */
	public void setGoogleUsername(String googleUsername) {
		editor = pref.edit();

		editor.putString(KEY_GOOGLE_USERNAME, googleUsername);

		// commit changes
		editor.commit();
	}

	public String getGoogleUserName() {
		return pref.getString(KEY_GOOGLE_USERNAME, Constant.PICASA_USER);//DUYPH fix here
	}

	/**
	 * store number of grid columns
	 * */
	public void setNoOfGridColumns(int columns) {
		editor = pref.edit();

		editor.putInt(KEY_NO_OF_COLUMNS, columns);

		// commit changes
		editor.commit();
	}

	public int getNoOfGridColumns() {
		return pref.getInt(KEY_NO_OF_COLUMNS, Constant.NUM_OF_COLUMNS);
	}

	/**
	 * storing gallery name
	 * */
	public void setGalleryName(String galleryName) {
		editor = pref.edit();

		editor.putString(KEY_GALLERY_NAME, galleryName);

		// commit changes
		editor.commit();
	}

	public String getGalleryName() {
		return pref.getString(KEY_GALLERY_NAME, Constant.SDCARD_DIR_NAME);
	}

	/**
	 * Storing albums in shared preferences
	 * */
	public void storeCategories(List<CategoryItem> albums) {
		editor = pref.edit();
		Gson gson = new Gson();

		Log.d(TAG, "Albums: " + gson.toJson(albums));

		editor.putString(KEY_ALBUMS, gson.toJson(albums));

		// save changes
		editor.commit();
	}

	/**
	 * Fetching albums from shared preferences. Albums will be sorted before
	 * returning in alphabetical order
	 * */
	public List<CategoryItem> getCategories() {
		List<CategoryItem> albums = new ArrayList<>();

		if (pref.contains(KEY_ALBUMS)) {
			String json = pref.getString(KEY_ALBUMS, null);
			Gson gson = new Gson();
			CategoryItem[] albumArry = gson.fromJson(json, CategoryItem[].class);

			albums = Arrays.asList(albumArry);
			albums = new ArrayList<>(albums);
		} else
			return null;

		List<CategoryItem> allAlbums = albums;

		// Sort the albums in alphabetical order
		Collections.sort(allAlbums, new Comparator<CategoryItem>() {
			public int compare(CategoryItem a1, CategoryItem a2) {
				return a1.getName().compareToIgnoreCase(a2.getName());
			}
		});

		return allAlbums;

	}

	/**
	 * Comparing albums titles for sorting
	 * */
	public class CustomComparator implements Comparator<CategoryItem> {
		@Override
		public int compare(CategoryItem c1, CategoryItem c2) {
			return c1.getName().compareTo(c2.getName());
		}
	}
}