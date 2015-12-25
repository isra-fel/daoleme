package cn.edu.fudan.daoleme.module;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.data.constant.Preferences;
import cn.edu.fudan.daoleme.data.pojo.Setting;
import cn.edu.fudan.daoleme.service.TimeRefreshService;
import cn.edu.fudan.daoleme.util.SessionUtil;

/**
 * Created by rinnko on 2015/11/14.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "SettingFragment";

    private Preference mAbout, mUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_general);
        mAbout = findPreference(Preferences.Key.SETTING_KEY_ABOUT);
        mAbout.setOnPreferenceClickListener(this);
        mUpdate = findPreference(Preferences.Key.SETTING_KEY_UPDATE);
        mUpdate.setOnPreferenceClickListener(this);
        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(this);
    }

    private void onQueryUpdate() {
        // TODO query update
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == mAbout) {
            startActivity(new Intent(getActivity(), AboutActivity.class));
        } else if (preference == mUpdate) {
            onQueryUpdate();
        } else {
            Log.e(TAG, "unknown preference");
            return false;
        }
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Setting setting = SessionUtil.getSession(getActivity()).getSetting();
        if (key.equals(Preferences.Key.SETTING_OPEN_WALLPAPER_NOTIFY)) {
            setting.openWallpaperNotify = sharedPreferences.getBoolean(Preferences.Key.SETTING_OPEN_WALLPAPER_NOTIFY, false);
            //TimeRefreshService.setPeriod(SessionUtil.getSession(getActivity()).IsWallpaperNotifyOpen(), SessionUtil.getSession(getActivity()).getPollFrequency() * 60 * 1000);
        } else if (key.equals(Preferences.Key.SETTING_NOTIFY_IF_FAVORITE_DEFAULT)) {
            setting.notifyIfFavoriteDefault = sharedPreferences.getBoolean(Preferences.Key.SETTING_NOTIFY_IF_FAVORITE_DEFAULT, false);
        } else if (key.equals(Preferences.Key.SETTING_POLL_FREQUENCY)) {
            setting.pollFrequency = Integer.parseInt(sharedPreferences.getString(Preferences.Key.SETTING_POLL_FREQUENCY, "1"));
           //TimeRefreshService.setPeriod(SessionUtil.getSession(getActivity()).IsWallpaperNotifyOpen(),SessionUtil.getSession(getActivity()).getPollFrequency()*60*1000);
        }
    }

}
