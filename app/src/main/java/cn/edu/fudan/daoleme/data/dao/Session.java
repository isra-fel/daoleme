package cn.edu.fudan.daoleme.data.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import cn.edu.fudan.daoleme.data.constant.Preferences;
import cn.edu.fudan.daoleme.data.pojo.Setting;
import cn.edu.fudan.daoleme.data.pojo.User;

/**
 * Created by rinnko on 2015/12/7.
 */
public class Session {

    private User mUser;
    private Setting mSetting;

    public Session(Context context) {
        SharedPreferences userPreferences = context.getSharedPreferences(Preferences.Key.USER_PREFERENCE_KEY, Context.MODE_PRIVATE);
        long userId = userPreferences.getLong(Preferences.Key.USER_ID, -1);
        if (userId != -1) {
            mUser = new User();
            mUser.setId(userId);
            mUser.setName(userPreferences.getString(Preferences.Key.USER_NAME, ""));
            mUser.setEmail(userPreferences.getString(Preferences.Key.USER_EMAIL, ""));
            mUser.setToken(userPreferences.getString(Preferences.Key.USER_TOKEN, ""));
        }
        SharedPreferences settingPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mSetting = new Setting();
        mSetting.openWallpaperNotify = settingPreferences.getBoolean(Preferences.Key.SETTING_OPEN_WALLPAPER_NOTIFY, true);
        mSetting.notifyIfFavoriteDefault = settingPreferences.getBoolean(Preferences.Key.SETTING_NOTIFY_IF_FAVORITE_DEFAULT, true);
        mSetting.pollFrequency = Integer.parseInt(settingPreferences.getString(Preferences.Key.SETTING_POLL_FREQUENCY, "1"));
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Preferences.Key.USER_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(Preferences.Key.USER_ID, user.getId());
        editor.putString(Preferences.Key.USER_NAME, user.getName());
        editor.putString(Preferences.Key.USER_EMAIL, user.getEmail());
        editor.putString(Preferences.Key.USER_TOKEN, user.getToken());
        editor.apply();
        mUser = user;
    }

    public void deleteUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Preferences.Key.USER_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(Preferences.Key.USER_ID, -1);
        editor.putString(Preferences.Key.USER_NAME, "");
        editor.putString(Preferences.Key.USER_EMAIL, "");
        editor.putString(Preferences.Key.USER_TOKEN, "");
        editor.apply();
        mUser = null;
    }

    public boolean isLogin() {
        return mUser != null;
    }

    public Setting getSetting() {
        return mSetting;
    }

    public void storeOpenWallpaperNotify(Context  context, boolean open) {
        if (open != mSetting.openWallpaperNotify) {
            mSetting.openWallpaperNotify = open;
            SharedPreferences settingPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            settingPreferences.edit().putBoolean(Preferences.Key.SETTING_OPEN_WALLPAPER_NOTIFY, open).apply();
        }
    }

    public boolean IsWallpaperNotifyOpen() {
        return mSetting.openWallpaperNotify;
    }

    public void storeNotifyIfFavoriteDefault(Context context, boolean notify) {
        if (notify != mSetting.notifyIfFavoriteDefault) {
            mSetting.notifyIfFavoriteDefault = notify;
            SharedPreferences settingPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            settingPreferences.edit().putBoolean(Preferences.Key.SETTING_NOTIFY_IF_FAVORITE_DEFAULT, notify).apply();
        }
    }

    public boolean IsNotifyFavoriteDefault() {
        return mSetting.notifyIfFavoriteDefault;
    }

    public void storePollFrequency(Context context, int frequency) {
        if (frequency != mSetting.pollFrequency) {
            mSetting.pollFrequency = frequency;
            SharedPreferences settingPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            settingPreferences.edit().putString(Preferences.Key.SETTING_POLL_FREQUENCY, String.valueOf(frequency)).apply();
        }
    }

    public int getPollFrequency() {
        return mSetting.pollFrequency;
    }

}
