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


    public static int getCachedMethod(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getInt(KEY_METHOD,RESULT_NONE);
    }

    public static void cacheMethod(Context context,int method){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putInt(KEY_METHOD,method);
        e.commit();
    }
}
