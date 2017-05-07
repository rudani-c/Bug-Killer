package com.rudanic.bugkiller;

/**
 * 		Name			:	Chandrakant Rudani.
 * 		LUID			:	L20386379.
 * 		Course			:	Android Programming
 */

//this activity create tread and access MainTread class and when to run it
// Assets values are loaded



import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;import com.rudanic.bugkiller.R;

public class MainView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder = null;
    Context context;
    private MainThread t = null;

    // Constructor
    @SuppressWarnings("deprecation")
    public MainView (Context context) {
        super(context);
        // Save context
        this.context = context;
        // Retrieve the SurfaceHolder instance associated with this SurfaceView.
        holder = getHolder();
        // Initialize variables
        this.context = context;
        Assets.state = Assets.GameState.GettingReady;
        Assets.livesLeft = 3;
        Assets.bigbugTouched=4;

        // Load the sound effects

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Assets.sound = prefs.getBoolean("key_music", true);

        if(Assets.sound) {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Assets.soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            }
            else {
                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build();
                Assets.soundPool = new SoundPool.Builder()
                        .setAudioAttributes(attributes)
                        .build();

            }
            Assets.sound_getready = Assets.soundPool.load(context, R.raw.getready, 1);
            Assets.sound_squish1 = Assets.soundPool.load(context, R.raw.squish1, 1);
            Assets.sound_squish2 = Assets.soundPool.load(context, R.raw.squish2, 1);
            Assets.sound_squish3 = Assets.soundPool.load(context, R.raw.squish3, 1);
            Assets.sound_miss = Assets.soundPool.load(context, R.raw.miss, 1);
            Assets.sound_thump = Assets.soundPool.load(context, R.raw.thump, 1);
        }

        // Specify this class (MainView) as the class that implements the three callback methods required by SurfaceHolder.Callback.
        holder.addCallback(this);
    }

    public void pause ()
    {
        t.setRunning(false);
        while (true) {
            try {
                t.join();    // stop current thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        t = null;
    }

    public void resume ()
    {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x, y;
        int action = event.getAction();
        x = event.getX();
        y = event.getY();
//		if (action==MotionEvent.ACTION_MOVE) {
//		}
//		if (action==MotionEvent.ACTION_DOWN){
//		}
        if (action == MotionEvent.ACTION_UP) {
            if (t != null)
                t.setXY ((int)x, (int)y);
        }
        return true; // to indicate we have handled this event
    }

    @Override
    public void surfaceCreated (SurfaceHolder holder) {
        // Create and start a drawing thread whose Runnable object is defined by this class (MainView)
        if (t == null) {
            t = new MainThread(holder, context);
            t.setRunning(true);
            t.start();
            setFocusable(true); // make sure we get events
        }
    }
    // Neither of these two methods are used in this example, however, their definitions are required because SurfaceHolder.Callback was implemented
    @Override
    public void surfaceChanged(SurfaceHolder sh, int f, int w, int h) {}
    @Override
    public void surfaceDestroyed(SurfaceHolder sh) {}
}
