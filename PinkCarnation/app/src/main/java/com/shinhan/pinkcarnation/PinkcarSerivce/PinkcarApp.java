package com.shinhan.pinkcarnation.PinkcarSerivce;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rollcake on 2017-03-28.
 */

public class PinkcarApp extends Application {

    public static String IntroDone = "";
    public static String Role = "";
    public static String DeviceID = "";
    public static String DevicePW = "";
    public static String TargetID = "";
    public static String TargetPW = "";

    public PinkcarApp() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.updateStaticValues();
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void HttpService(String cmd, JSONObject biz, HttpCallBack callback) {
        JSONObject param = new JSONObject();
        JSONObject com = new JSONObject();
        try {
            param.put("cmd", cmd);
            com.put("deviceid", this.get("Main", "DeviceID", ""));
            com.put("devicepw", this.get("Main", "DevicePW", ""));
            com.put("targetid", this.get("Main", "TargetID", ""));
            com.put("targetpw", this.get("Main", "TargetPW", ""));
            com.put("vcode", this.getVCode());  // 전문 검증 코드

            param.put("com", com);
            param.put("biz", biz);

        } catch (JSONException e) {
            e.printStackTrace();
            callback.callback(0, "", e.getMessage());
        }

        String paramStr = param.toString();
        new HttpService(callback).execute(paramStr);
    }

    public String getVCode() {
        return "88888888";
    }

    public String get(String category, String key, String value) {
        SharedPreferences spf = getSharedPreferences(category, MODE_PRIVATE);
        return spf.getString(key, value);
    }

    public Boolean put(String category, String key, String value) {
        SharedPreferences spf = getSharedPreferences(category, MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(key, value);
        editor.commit();

        this.updateStaticValues();
        return true;
    }



    public Boolean remove(String category, String key) {
        SharedPreferences spf = getSharedPreferences(category, MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.remove(key);
        editor.commit();

        this.updateStaticValues();
        return true;
    }

    public Boolean clear(String category) {
        SharedPreferences spf = getSharedPreferences(category, MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.clear();
        editor.commit();

        this.updateStaticValues();
        return true;
    }

    private void updateStaticValues() {
        this.IntroDone = this.get("Main", "IntroDone", "");
        this.Role = this.get("Main", "Role", "");
        this.DeviceID = this.get("Main", "DeviceID", "");
        this.DevicePW = this.get("Main", "DevicePW", "");
        this.TargetID = this.get("Main", "TargetID", "");
        this.TargetPW = this.get("Main", "TargetPW", "");
    }
}
