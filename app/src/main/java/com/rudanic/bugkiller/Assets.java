package com.rudanic.bugkiller;

/**
 * 		Name			:	Chandrakant Rudani
 * 		LUID			:	L20386379
 * 		Course			:	Android Programming
 */


//this class is Assets for all game and game state, so it is connected to all activity
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class Assets {
    static Bitmap background;
    static Bitmap foodbar;
    static Bitmap scorebar;
    static Bitmap roach1;
    static Bitmap roach2;
    static Bitmap roachdead;
    static Bitmap bigroach1;
    static Bitmap bigroach2;
    static Bitmap bigroachdead;
    static Bitmap lady1;
    static Bitmap lady2;
    static Bitmap lady3;
    static Bitmap life;

    // States of the Game Screen
    enum GameState {
        GettingReady,	// play "get ready" sound and start timer, goto next state
        Starting,		// when 3 seconds have elapsed, goto next state
        Running, 		// play the game, when livesLeft == 0 goto next state
        GameEnding,	    // show game over message
        GameOver,		// game is over, wait for any Touch and go back to title activity screen
    };
    static GameState state;		// current state of the game
    static float gameTimer;	    // in seconds
    static int livesLeft;		// 0-3
    static int bigbugTouched;
    static int score;

    static SoundPool soundPool;
    public static MediaPlayer mp;
    public static boolean isMp;
    static int sound_getready;
    static int sound_squish1;
    static int sound_squish2;
    static int sound_squish3;
    static int sound_miss;
    static int sound_thump;
    public static boolean sound;


    static Bug bug; // Using array of bugs instead of only 1 bug
    static Bug bug1;
    static SBug bug2;
    static Bug roach;
    static Bigbug bigroach;
}
