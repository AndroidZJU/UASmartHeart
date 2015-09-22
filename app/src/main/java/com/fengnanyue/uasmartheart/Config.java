package com.fengnanyue.uasmartheart;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Fernando on 15/9/17.
 */
public class Config {
    public static final String APP_ID = "com.fengnanyue.uasmartheart";
    private static final String KEY_METHOD ="CPRmethod";
    public static final int RESULT_METHOD1 =1001;
    public static final int RESULT_METHOD2 =1002;
    public static final int RESULT_METHOD3 =1003;
    public static final int RESULT_NONE =1000;
    private static final String KEY_NAME ="name";
    private static final String KEY_DATE = "date";
    private static final String KEY_AGE = "age";
    public static final String GENDER_STATUS = "gender_status";
    public static final String MALE = "gender_male";
    public static final String FEMALE ="gender_female" ;
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_STERNUM = "sternum";
    private static final String KEY_CHEST_CIRCUMFERENCE = "chest_circumference";
    private static final String KEY_UPDATE_DATE = "update_date";
    private static final String KEY_FRONT_IMAGE = "front_image";
    private static final String KEY_SIDE_IMAGE = "side_image";


    public static int getCachedMethod(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getInt(KEY_METHOD,RESULT_NONE);
    }

    public static void cacheMethod(Context context,int method){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putInt(KEY_METHOD,method);
        e.commit();
    }

    public static String getCachedName(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_NAME, "");

    }
    public static void cacheName(Context context,String name){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_NAME, name);
        e.commit();
    }

    public static String getCachedDate(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_DATE,"0000-00-00");
    }
    public static void cacheDate(Context context,String date){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_DATE,date);
        e.commit();
    }

    public static int getCachedAge(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getInt(KEY_AGE, 1);
    }

    public static void cacheAge(Context context,int age){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putInt(KEY_AGE, age);
        e.commit();
    }
    public static void cacheGender(Context context,String gender){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(GENDER_STATUS, gender);
        e.commit();
    }
    public static String getGender(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(GENDER_STATUS, "");
    }

    public static String getCachedHeight(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_HEIGHT, "");
    }

    public static void cacheHeight(Context context,String height){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_HEIGHT, height);
        e.commit();
    }
    public static String getCachedWeight(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_WEIGHT, "");
    }

    public static void cacheWeight(Context context,String weight){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_WEIGHT, weight);
        e.commit();
    }
    public static String getCachedSternum(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_STERNUM, "");
    }

    public static void cacheSternum(Context context,String sternum){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_STERNUM, sternum);
        e.commit();
    }
    public static String getCachedChestCircumference(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_CHEST_CIRCUMFERENCE, "");
    }

    public static void cacheChestCircumference(Context context,String chest){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_CHEST_CIRCUMFERENCE, chest);
        e.commit();
    }


    public static String getCachedUpdateDate(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_UPDATE_DATE,"First update");
    }

    public static void cacheUpdateDate(Context context,String update){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_UPDATE_DATE, update);
        e.commit();
    }

    public static String getCachedFrontImage(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_FRONT_IMAGE,"");
    }

    public static void cacheUpdateFrontImage(Context context,String front){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_FRONT_IMAGE, front);
        e.commit();
    }
}
