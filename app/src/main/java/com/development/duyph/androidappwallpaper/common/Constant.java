package com.development.duyph.androidappwallpaper.common;

/**
 * Created by nguoi on 12/19/2015.
 */
public class Constant {
    /**
     * numbers fragment of view pagers
     **/
    public static String INTENT_FRAGMENT_NUMBER = "fragment_number";
    public static String INTENT_SELECTED_ALBUM_ID = "selected_album_id";

    /**
     * have problem with connected internet of device.
     **/
    public static int ERR_CODE_INTERTNET = -1;



    // Number of columns of Grid View
    // by default 2 but user can configure this in settings activity
    public static final int NUM_OF_COLUMNS = 2;

    // Gridview image padding
    public static final int GRID_PADDING = 4; // in dp

    // Gallery directory name to save wallpapers
    public static final String SDCARD_DIR_NAME = "Awesome Wallpapers";

    // Picasa/Google web album username
    public static final String PICASA_USER = "nguoibanmoi001";

    // Public albums list url
    public static final String URL_PICASA_ALBUMS = "https://picasaweb.google.com/data/feed/api/user/_PICASA_USER_?kind=album&alt=json";

    // Picasa album photos url
    public static final String URL_ALBUM_PHOTOS = "https://picasaweb.google.com/data/feed/api/user/_PICASA_USER_/albumid/_ALBUM_ID_?alt=json";

    // Picasa recenlty added photos url
    public static final String URL_RECENTLY_ADDED = "https://picasaweb.google.com/data/feed/api/user/_PICASA_USER_?kind=photo&alt=json";




}
