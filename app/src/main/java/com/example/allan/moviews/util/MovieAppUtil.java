package com.example.allan.moviews.util;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Allan Pana on 12/03/18.
 * allan.pana74@gmail.com
 */

public class MovieAppUtil {


    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


    //convert ArrayList to string to store into database
    public static <T> String getStringFromList(List<T> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    //convert string from database  to arraylist
    public static <T> List<String> stringList(String stringJson){
        Type type = new TypeToken<ArrayList<T>>(){}.getType();
        return new Gson().fromJson(stringJson, type);
    }
}
