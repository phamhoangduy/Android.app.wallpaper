package com.development.duyph.androidappwallpaper.common;

import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;

import java.lang.reflect.Field;

/**
 * Created by nguoi on 12/25/2015.
 */
public class Profile {
    public static int[] getDisplay(Activity activity){
        int[] displays = new int[2];
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displays[0] = size.x;
        displays[1] = size.y;
        return displays;
    }

    public static String getAndroidName(){
        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return fieldName;
        }
        return "";
    }
}
