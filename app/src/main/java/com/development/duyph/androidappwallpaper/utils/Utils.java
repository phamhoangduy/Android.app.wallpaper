package com.development.duyph.androidappwallpaper.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.development.duyph.androidappwallpaper.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

/**
 * Created by nguoi on 12/20/2015.
 */
public class Utils {
    private String TAG = Utils.class.getSimpleName();
    private Context _context;
    private PrefManager pref;
    /**
     *Check is list valid or not.
     **/
    public static boolean isListValid(List<? extends Object> list) {
        return list != null && list.size() > 0;
    }


    public void saveImageToSDCard(Bitmap bitmap) {
        File myDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                pref.getGalleryName());

        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "GalleryItem-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(
                    _context,
                    _context.getString(R.string.toast_saved).replace("#",
                            "\"" + pref.getGalleryName() + "\""),
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "GalleryItem saved to: " + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(_context,
                    _context.getString(R.string.toast_saved_failed),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
