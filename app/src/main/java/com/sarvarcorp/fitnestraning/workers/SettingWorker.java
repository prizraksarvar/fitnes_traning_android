package com.sarvarcorp.fitnestraning.workers;

import android.content.Context;
import android.content.SharedPreferences;

import com.sarvarcorp.fitnestraning.base.BaseAppCompatActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SettingWorker {
    private SharedPreferences mSharedPreferences;

    private static final String APP_PREFERENCES = "com.sarvarcorp.fitnestraning.SettingWorker";

    private static final String USER_TOKEN = "USER_TOKEN";

    @Inject
    public SettingWorker(Context context) {
        mSharedPreferences = context.getSharedPreferences(APP_PREFERENCES, BaseAppCompatActivity.MODE_PRIVATE);
    }

    public String getUserToken() {
        return mSharedPreferences.getString(USER_TOKEN, "");
    }

    public void setUserToken(String userToken) {
        mSharedPreferences.edit().putString(USER_TOKEN, userToken).apply();
    }
}
