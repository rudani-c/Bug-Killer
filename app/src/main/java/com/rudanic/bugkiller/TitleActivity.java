package com.rudanic.bugkiller;

/**
 * 		Name			:	Chandrakant Rudani.
 * 		LUID			:	L20386379.
 * 		Course			:	Android Programming
 */

//for the title screen
//if some one touch the screen it will jump on the main activity

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;import com.rudanic.bugkiller.R;


public class TitleActivity extends AppCompatActivity implements View.OnClickListener {
    private Handler mHandler = new Handler();
    private Button bnPlay, bnSettings, bnScore;
    SharedPreferences sp;

    /**********************************************************************************************
     * This is main Acitvity just for to hold image for 2000ms and thn jump on second activity
     **********************************************************************************************/
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_title);

        bnPlay = (Button) findViewById(R.id.button_play);
        bnSettings = (Button) findViewById(R.id.button_settings);
        bnScore = (Button) findViewById(R.id.button_highscore);
        bnPlay.setOnClickListener(this);
        bnSettings.setOnClickListener(this);
        bnScore.setOnClickListener(this);

        sp = getSharedPreferences("HighscoreFile", MODE_PRIVATE);
        if (sp.getInt("newHighscore", Assets.score) <= Assets.score) {
            SharedPreferences.Editor ed = sp.edit();
            ed.putInt("newHighscore", Assets.score);
            ed.commit();
            mHighscore("Congratulations! You made Highest Score");
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_play:
                beginPlayingGame();
                break;

            case R.id.button_settings:
                startActivity(new Intent(this, PrefsActivity.class));
                break;

            case R.id.button_highscore:
                mHighscore("Highscore");
        }
    }

    private void beginPlayingGame() {
        Intent intent = new Intent(TitleActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }



    public void mHighscore(String msg) {
        String scoreMsg = Integer.toString(sp.getInt("newHighscore", Assets.score));
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.highscore)
                .setTitle(msg)
                .setMessage(scoreMsg)
                .setNegativeButton("OK", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
