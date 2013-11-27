package com.android.settings.beanstalk;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.PreferenceCategory;
import android.preference.ListPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class GeneralSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String PREF_USE_ALT_RESOLVER = "use_alt_resolver";
    private static final String KEY_VOLUME_WAKE = "pref_volume_wake";
    private static final String SHOW_ENTER_KEY = "show_enter_key";
    private static final String PREF_DISABLE_FULLSCREEN_KEYBOARD = "disable_fullscreen_keyboard";

    private CheckBoxPreference mDisableFullscreenKeyboard;
    private CheckBoxPreference mShowEnterKey;
    private CheckBoxPreference mUseAltResolver;
    private CheckBoxPreference mVolumeWake;
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.general_settings);

 	mUseAltResolver = (CheckBoxPreference) findPreference(PREF_USE_ALT_RESOLVER);
        mUseAltResolver.setChecked(Settings.System.getInt(
                getActivity().getContentResolver(),
                Settings.System.ACTIVITY_RESOLVER_USE_ALT, 0) == 1); 

	mVolumeWake = (CheckBoxPreference) findPreference(KEY_VOLUME_WAKE);
        if (mVolumeWake != null) {
            mVolumeWake.setChecked(Settings.System.getInt(
		getActivity().getContentResolver(),
                Settings.System.VOLUME_WAKE_SCREEN, 0) == 1);
            mVolumeWake.setOnPreferenceChangeListener(this);
        }

	mDisableFullscreenKeyboard =
            (CheckBoxPreference) findPreference(PREF_DISABLE_FULLSCREEN_KEYBOARD);
        if (mDisableFullscreenKeyboard != null) {
            mDisableFullscreenKeyboard.setChecked(Settings.System.getInt(
		getActivity().getContentResolver(),
                Settings.System.DISABLE_FULLSCREEN_KEYBOARD, 0) == 1);
            mDisableFullscreenKeyboard.setOnPreferenceChangeListener(this);
        }

	mShowEnterKey = (CheckBoxPreference) findPreference(SHOW_ENTER_KEY);
        if (mShowEnterKey != null) {
            mShowEnterKey.setChecked(Settings.System.getInt(
		getActivity().getContentResolver(),
                Settings.System.FORMAL_TEXT_INPUT, 0) == 1);
            mShowEnterKey.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mUseAltResolver) {
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.ACTIVITY_RESOLVER_USE_ALT,
                    ((CheckBoxPreference) preference).isChecked() ? 1 : 0);
            return true;
        }

        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
	final String key = preference.getKey();
        if (KEY_VOLUME_WAKE.equals(key)) {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.VOLUME_WAKE_SCREEN,
                    (Boolean) newValue ? 1 : 0);
        }
        if (PREF_DISABLE_FULLSCREEN_KEYBOARD.equals(key)) {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.DISABLE_FULLSCREEN_KEYBOARD,
		    (Boolean) newValue ? 1 : 0);
	}
        if (SHOW_ENTER_KEY.equals(key)) {
            Settings.System.putInt(getContentResolver(),
                Settings.System.FORMAL_TEXT_INPUT,
		(Boolean) newValue ? 1 : 0);
        }
         return true;
    }
}
