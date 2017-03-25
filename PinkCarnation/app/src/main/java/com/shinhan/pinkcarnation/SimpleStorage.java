package com.shinhan.pinkcarnation;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by IC-INTPC-087107 on 2017-03-24.
 */

public class SimpleStorage {
    Context context;

    public SimpleStorage(Context context) {
        this.context = context;
    }

    public String get(String category, String key, String value) {
        SharedPreferences spf = context.getSharedPreferences(category, MODE_PRIVATE);
        return spf.getString(key, value);
    }

    public Boolean put(String category, String key, String value) {
        SharedPreferences spf = context.getSharedPreferences(category, MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(key, value);
        editor.commit();
        return true;
    }

    public Boolean remove(String category, String key) {
        SharedPreferences spf = context.getSharedPreferences(category, MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.remove(key);
        editor.commit();
        return true;
    }

    public Boolean clear(String category) {
        SharedPreferences spf = context.getSharedPreferences(category, MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.clear();
        editor.commit();
        return true;
    }
}
