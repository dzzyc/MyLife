package com.example.dell.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dell on 2018/4/5.
 */

public class SharedPreferencesUtils {
    SharedPreferences sharedPreferences;
    public SharedPreferencesUtils(Context context, String fileName){
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public static class ContentVaule{
        String key;
        Object value;

        public ContentVaule(String key,Object value){
            this.key = key;
            this.value = value;
        }
    }

    public void putValues(ContentVaule...contentVaules){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (ContentVaule contentVaule : contentVaules){

            if(contentVaule.value instanceof String){
                editor.putString(contentVaule.key, contentVaule.value.toString()).commit();
            }
            if(contentVaule.value instanceof Integer){
                editor.putInt(contentVaule.key, Integer.parseInt(contentVaule.value.toString())).commit();
            }
            if(contentVaule.value instanceof Long){
                editor.putLong(contentVaule.key, Long.parseLong(contentVaule.value.toString())).commit();
            }
            if(contentVaule.value instanceof Boolean){
                editor.putBoolean(contentVaule.key, Boolean.parseBoolean(contentVaule.value.toString())).commit();
            }
        }
    }

    public String getString(String key){
        return sharedPreferences.getString(key, null);
    }

    public boolean getBoolean(String key, Boolean b){
        return sharedPreferences.getBoolean(key, b);
    }

    public int getInt(String key, Integer i){
        return sharedPreferences.getInt(key, -1);
    }

    public long getLong(String key, Long l){
        return sharedPreferences.getLong(key, -1);
    }

    public void clear(){
        sharedPreferences.edit().clear().commit();
    }

}
