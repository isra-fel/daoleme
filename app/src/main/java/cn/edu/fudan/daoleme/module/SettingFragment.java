package cn.edu.fudan.daoleme.module;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import cn.edu.fudan.daoleme.R;

/**
 * Created by rinnko on 2015/11/14.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private static final String TAG = "SettingFragment";

    private Preference mAbout, mUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_general);
        mAbout = findPreference(getString(R.string.preference_about));
        mAbout.setOnPreferenceClickListener(this);
        mUpdate = findPreference(getString(R.string.preference_update));
        mUpdate.setOnPreferenceClickListener(this);
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
}
