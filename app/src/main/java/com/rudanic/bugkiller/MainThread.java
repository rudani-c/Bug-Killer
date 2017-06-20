package com.rudanic.bugkiller;

/**
 * 		Name			:	Chandrakant Rudani.
 * 		LUID			:	L20386379.
 * 		Course			:	Android Programming
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.widget.Toast;

import java.util.Random;

public class MainThread extends Thread {
    private SurfaceHolder holder;
    private Handler handler; // Required for running code in the UI thread
    private boolean isRunning = false;
    Context context;
    Paint paint;
    int touchx, touchy;	// x,y of touch event
    boolean touched,touched1;	// true if touch happened
    private static final Object lock = new Object();
    Random r = new Random();
    Intent intent;

    public MainThread (SurfaceHolder surfaceHolder, Context context) {
        holder = surfaceHolder;
        this.context = context;
        handler = new Handler();
        touched = false;
    }

    // b value will be got from MainView class
    public void setRunning(boolean b) {
        isRunning = b;	// no need to synchronize this since this is the only line of code to writes this variable
    }

    // Set the touch event x,y location and flag indicating a touch has happened
    public void setXY (int x, int y) {
        synchronized (lock) {
            touchx = x;
            touchy = y;
            this.touched = true;
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            // Lock the canvas before drawing
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                // Perform drawing operations on the canvas
                render(canvas);
                // After drawing, unlock the canvas and display it
                holder.unlockCanvasAndPost (canvas);
            }
        }
    }

    // Loads graphics, etc. used in game  According to phone width and height
    private void loadData (Canvas canvas) {
        Bitmap bmp;
        int newWidth, newHeight;
        float scaleFactor;

        // Create a paint object for drawing vector graphics
        paint = new Paint();

        // Load score bar , not necessory -its not used here., except it loaded
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.scorebar);
        // Compute size of bitmap needed (suppose want height = 10% of screen height)
        newHeight = (int)(canvas.getHeight() * 0.075f);
        // Scale it to a new size
        Assets.scorebar = Bitmap.createScaledBitmap (bmp, canvas.getWidth(), newHeight, false);
        // Delete the original
        bmp = null;

        // Load food bar
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.foodbar);
        // Compute size of bitmap needed (suppose want height = 10% of screen height)
        newHeight = (int)(canvas.getHeight() * 0.1f);
        // Scale it to a new size
        Assets.foodbar = Bitmap.createScaledBitmap (bmp, canvas.getWidth(), newHeight, false);
        // Delete the original
        bmp = null;

        // Load life icon
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.life);
        // Compute size of bitmap needed (suppose want height = 10% of screen height)
        newHeight = (int)(canvas.getHeight() * 0.05f);
        // Scale it to a new size
        Assets.life = Bitmap.createScaledBitmap (bmp, newHeight, newHeight, false);
        // Delete the original
        bmp = null;

        // Load roach1
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.roach1);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 0.15f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.roach1 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        // Load roach2
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.roach2);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 0.15f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.roach2 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;
        // ...

        // Load dead roach
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.roachdead);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 0.15f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.roachdead = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        // Load bigroach1
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.bigroach1);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 0.22f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.bigroach1 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        // Load bigroach2
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.bigroach2);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 0.22f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.bigroach2 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;
        // Load dead bigroach
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.bigroachdead);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 0.22f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.bigroachdead = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        // Load lady1
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.lady1);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 0.1f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.lady1 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        // Load lady2
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.lady2);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 0.1f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.lady2 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        // Load lady3
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.lady3);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 0.1f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.lady3 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        // Create a bug
        Assets.bug = new Bug();
        Assets.bug1 = new Bug();
        Assets.roach = new Bug();
        Assets.bigroach = new Bigbug();
        Assets.bug2 = new SBug();
    }

    // Load specific background screen
    private void loadBackground (Canvas canvas, int resId) {
        // Load background
        Bitmap bmp = BitmapFactory.decodeResource (context.getResources(), resId);
        // Scale it to fill entire canvas
        Assets.background = Bitmap.createScaledBitmap (bmp, canvas.getWidth(), canvas.getHeight(), false);
        // Delete the original
        bmp = null;
    }

    private void render (Canvas canvas) {
        int i, x, y;

        switch (Assets.state) {
            case GettingReady:
                // Load a special "getting ready screen"
                loadBackground (canvas, R.drawable.getready);
                // Load data and other graphics needed by game
                loadData(canvas);
                // Draw the background screen
                //       canvas.drawBitmap (Assets.background, 0, 0, null);
                // Play a sound effect
                if(Assets.sound) {
                    Assets.soundPool.play(Assets.sound_getready, 1, 1, 1, 0, 1);
                }
                // Start a timer

                Assets.gameTimer = System.nanoTime() / 1000000000f;
                // Go to next state
                Assets.state = Assets.GameState.Starting;
                Assets.score=0;
                break;

            case Starting:
                // Draw the background screen
                canvas.drawBitmap (Assets.background, 0, 0, null);
                // Has 3 seconds elapsed?
                float currentTime = System.nanoTime() / 1000000000f;
                if (currentTime - Assets.gameTimer >= 3) {
                    // Load game play background
                    if(Assets.isMp) {
                        Assets.mp.start();    //start song
                    }
                    loadBackground (canvas, R.drawable.background);
                    // Goto next state
                    Assets.state = Assets.GameState.Running;
                }
                break;

            case Running:
                // Draw the background screen
                canvas.drawBitmap (Assets.background, 0, 0, null);


                canvas.drawBitmap (Assets.foodbar, 0, canvas.getHeight()-Assets.foodbar.getHeight(), null);
                // Draw one circle for each life at top right corner of screen
                int spacing = 8; // spacing in between life icons
                x = canvas.getWidth()- Assets.life.getHeight();	// coordinates for rightmost icon
                y = Assets.life.getHeight()/2;
                for (i=0; i<Assets.livesLeft; i++) {

                    canvas.drawBitmap (Assets.life, x, y, null);

                    // Reposition to draw the next life icon
                    x = x-(Assets.life.getHeight()+spacing);


                    paint = new Paint();

                    // Load score on bar
                    paint.setColor(Color.BLACK);
                    paint.setTextSize(50);
                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setTypeface(Typeface.DEFAULT_BOLD);
                    canvas.drawText("Score: " + Assets.score,canvas.getWidth() * 0.1f,75, paint);
                }

                // Process a touch
                if (touched) {

                    int i1 = (r.nextInt(3));

                    // Set touch flag to false since we are processing this touch now
                    touched = false;
                    // See if this touch killed a bug
                    boolean bugKilled = Assets.bug.touched(canvas, touchx, touchy);
                    boolean roachkilled = Assets.roach.touched(canvas, touchx, touchy);
                    boolean bigroachkilled = Assets.bigroach.touched(canvas, touchx, touchy);
                    boolean bugKilled2 = Assets.bug2.touched(canvas, touchx, touchy);
                    if (Assets.sound) {
                        if (bugKilled || roachkilled) {
                            if (i1 == 0)
                                Assets.soundPool.play(Assets.sound_squish1, 1, 1, 1, 0, 1);
                            else if (i1 == 1)
                                Assets.soundPool.play(Assets.sound_squish2, 1, 1, 1, 0, 1);
                            else if (i1 == 2)
                                Assets.soundPool.play(Assets.sound_squish3, 1, 1, 1, 0, 1);
                        } else {
                            if (i1 == 0)
                                Assets.soundPool.play(Assets.sound_thump, 1, 1, 1, 0, 1);
                        }
                    if (bugKilled2) {
                        if (i1 == 0)
                            Assets.soundPool.play(Assets.sound_squish1, 1, 1, 1, 0, 1);
                        else if (i1 == 1)
                            Assets.soundPool.play(Assets.sound_squish2, 1, 1, 1, 0, 1);
                        else if (i1 == 2)
                            Assets.soundPool.play(Assets.sound_squish3, 1, 1, 1, 0, 1);
                    } else {
                        if (i1 == 0)
                            Assets.soundPool.play(Assets.sound_miss, 1, 1, 1, 0, 1);
                    }
                    if (bigroachkilled) {
                            Assets.soundPool.play(Assets.sound_squish3, 1, 1, 1, 0, 1);
                        } else {
                            Assets.soundPool.play(Assets.sound_thump, 1, 1, 1, 0, 1);
                        }
                    }
                }
                // Draw dead bugs on screen
                Assets.bug.drawDead(canvas);
                // Move bugs on screen
                Assets.bug.move(canvas);
                // Assets.bug.touched (canvas,touchx,touchy);
                // Bring a dead bug to life?
                Assets.bug.birth(canvas);

                Assets.roach.drawDead(canvas);
                Assets.roach.move(canvas);
                Assets.roach.birth(canvas);
                Assets.bigroach.drawDead(canvas);
                Assets.bigroach.move(canvas);
                Assets.bigroach.birth(canvas);
                Assets.bug2.drawDead(canvas);
                Assets.bug2.move(canvas);
                Assets.bug2.birth(canvas);



                // Are no lives left?
                // Toast HIghScore
                if (Assets.livesLeft == 0){
                    // Goto next state
                    Assets.state = Assets.GameState.GameEnding;
                }
                break;

            case GameEnding:
                // Show a game over message

                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(context, "The Score is " + Assets.score, Toast.LENGTH_SHORT).show();

                    }
                });
                // Goto next state
                Assets.state = Assets.GameState.GameOver;
                break;

            case GameOver:
                loadBackground (canvas, R.drawable.game_over);
                canvas.drawBitmap (Assets.background, 0, 0, null);
                 break;
        }
    }

}
