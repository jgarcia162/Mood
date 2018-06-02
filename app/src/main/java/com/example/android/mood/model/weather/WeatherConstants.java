package com.example.android.mood.model.weather;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by Joe on 4/2/18.
 */

public class WeatherConstants {

    public static String ACCESS_ID = "CSmwtNoZifPD0iecEwoZ6";
    public static String SECRET_KEY = "MsWdAQZ5Zsa8OcCOpHAp5tAMzIEFFxplrNyqUhZl";
    public static String BASE_URL = "https://api.aerisapi.com/";

    public static Drawable getIconDrawable(Context context, String icon) {
        Resources resources = context.getResources();
        Drawable drawable;
        String uri = "@drawable/" + icon;
        Log.d("URI ", "getIconDrawable: " + uri);
        try {
            int imageResource = resources.getIdentifier(uri, null, context.getPackageName());
            drawable = resources.getDrawable(imageResource, null);
        }catch (RuntimeException e){
            return null;
        }
        return drawable;
    }


}
