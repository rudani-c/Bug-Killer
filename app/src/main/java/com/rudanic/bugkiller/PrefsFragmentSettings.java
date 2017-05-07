package com.rudanic.bugkiller;

/**
 * 		Name			:	Chandrakant Rudani.
 * 		LUID			:	L20386379.
 * 		Course			:	Android Programming
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import com.rudanic.bugkiller.R;

public class PrefsFragmentSettings extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    Preference pref;
    String score;
    public PrefsFragmentSettings () {}

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);
        // Load preferences from an XML resource
        addPreferencesFromResource(R.xml.prefs_fragment_settings);

    }
    @Override
    public void onResume () {
        super.onResume();

        // Set a click listener whenever key changes
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);


        pref= getPreferenceScreen().findPreference("highscore");
        pref.setSummary(Integer.toString(Assets.score));
        pref.shouldCommit();

        // Set up a click listener
        pref = getPreferenceScreen().findPreference("key_share");
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick (Preference preference) {
                // Launch the share so user can select a sharing method
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=com.handm.dhruval.bugkiller";
                sharingIntent.putExtra (android.content.Intent.EXTRA_SUBJECT, "Check out \"Lamar\"");
                sharingIntent.putExtra (android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                return true;
            }
        });


    }

    // below in "onSharedPreferenceChanged", we can write algorithem to make chenge in activity

    public void onSharedPreferenceChanged (SharedPreferences sharedPreferences, String key) {
        if (key.equals("key_music")) {
            boolean b = sharedPreferences.getBoolean("key_music", true);
            if (!b) {
                Assets.isMp=false;
            }
            else {
                Assets.isMp=true;
            }
        }

        if (key.equals("key_sound")) {
            boolean b = sharedPreferences.getBoolean("key_sound", true);
            if (b == false)
                Assets.sound=false;
            else
                Assets.sound=true;
        }


    }
}

