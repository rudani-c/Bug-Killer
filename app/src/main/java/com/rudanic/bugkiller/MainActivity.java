package com.rudanic.bugkiller;

/**
 * 		Name	:	Chandrakant Rudani
 * 		LUID	:	L20386379
 * 		Course	:	Android Programming
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.rudanic.bugkiller.R;

public class MainActivity extends AppCompatActivity {
    MainView v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Disable the title
        //requestWindowFeature (Window.FEATURE_NO_TITLE);  // use the styles.xml file to set no title bar
        // Make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Start the view
        v = new MainView(this);
        setContentView(v); // this means this activity won't use XML file in layout. it will draw it's own(by jumping MainView activity)

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Assets.isMp = prefs.getBoolean("key_music", true);

        if(Assets.isMp) {
            Assets.mp = null;
            if (Assets.mp != null) {
                Assets.mp.release();
                Assets.mp = null;
            }
            Assets.mp = MediaPlayer.create(this, R.raw.song);
            Assets.mp.setLooping(true);
        }
    }

    @Override
    protected void onPause () {
        super.onPause();
        v.pause();
        if(Assets.isMp) {
            Assets.mp.stop();
            Assets.mp.release();
            Assets.mp = null;
        }
        //      t.setRunning(false);
    }
    @Override
    protected void onSaveInstanceState (Bundle outBundle) {  //outBundle is just the name taken to for Bundle to save the current state
        super.onSaveInstanceState(outBundle);
        //this will save current image so even if we rotate screen application starts from the 1st ,so it will resume from last current page.
    }

    @Override
    protected void onResume () {
        super.onResume();
        v.resume();
    }

    @Override
    public void onBackPressed () {
        //this will help to end this activity as well with the 2nd activity with the back press button
        super.onBackPressed();
        Intent intent = new Intent(MainActivity.this, TitleActivity.class);
        startActivity(intent);
        finish();
    }

}
