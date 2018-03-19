package com.example.allan.moviews.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

import com.example.allan.moviews.model.Review;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Allan Pana on 12/03/18.
 * allan.pana74@gmail.com
 */

public class MovieAppUtil {


    //Ping for the main name servers to check if android device has internet connectivity
    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 70, stream); //reduce the size of byte []
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


    //convert ArrayList to string to store into database
    public static String getStringFromList(List<Review> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    //convert string from database  to arraylist
    public static  List<Review> stringList(String stringJson){
        Type type = new TypeToken<ArrayList<Review>>(){}.getType();
        return new Gson().fromJson(stringJson, type);
    }
}
