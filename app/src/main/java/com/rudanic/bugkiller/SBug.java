package com.rudanic.bugkiller;


import android.graphics.Canvas;

public class SBug {

    // States of a Bug
    enum SBugState {
        Dead,
        ComingBackToLife,
        Alive, 			    // in the game
        DrawDead,			// draw dead body on screen
    };

    SBugState state;			// current state of bug
    int x,y,x1;				// location on screen (in screen coordinates)
    double speed;			// speed of bug (in pixels per second)
    // All times are in seconds
    float timeToBirth;		// # seconds till birth
    float startBirthTimer;	// starting timestamp when decide to be born
    float deathTime;		// time of death
    float animateTimer;		// used to move and animate the bug
    boolean a,d,e;

    // Bug starts not alive
    public SBug () {
        state = SBugState.Dead;
        a=true;
    }

    // Bug birth processing
    public void birth (Canvas canvas) {
        // Bring a bug to life?
        if (state == SBugState.Dead) {
            // Set it to coming alive
            state = SBugState.ComingBackToLife;
            // Set a random number of seconds before it comes to life
            //chenged 5 to 2
            timeToBirth = (float) Math.random () * 5; // this will give random float number from 0-5, math.randon() gives number between0-1(0 but not 1) and multiply it by 5
            // Note the current time
            startBirthTimer = System.nanoTime() / 1000000000f; //here system.nanotime will give the system time in nanosecond,so by dividing is by 10^-9 will give it in second
        }
        // Check if bug is alive yet
        else if (state == SBugState.ComingBackToLife) {
            float curTime = System.nanoTime() / 1000000000f;
            // Has birth timer expired?
            if (curTime - startBirthTimer >= timeToBirth) {
                // If so, then bring bug to life
                state = SBugState.Alive;
                // Set bug starting location at top of screen
                x = (int)(Math.random() * canvas.getWidth());
                // Keep entire bug on screen
                if (x < Assets.lady1.getWidth())
                    x = Assets.lady1.getWidth();
                else if (x > canvas.getWidth() - Assets.lady1.getWidth())
                    x = canvas.getWidth() - Assets.lady1.getWidth();
                y = 0;
                // Set speed of this bug
                speed = canvas.getHeight() / 4; // no faster than 1/4 a screen per second
                // subtract a random amount off of this so some bugs are a little slower
                // ADD CODE HERE
                // Record timestamp of this bug being born
                animateTimer = curTime;
                x1=x;             //so we can use x1 for Alive state in move method to change direction of roches...
                d=true;
                e=true;
            }
        }
    }

    // Bug movement processing
    public void move (Canvas canvas) {
        // Make sure this bug is alive
        if (state == SBugState.Alive) {
            // Get elapsed time since last call here
            float curTime = System.nanoTime() / 1000000000f;
            float elapsedTime = curTime - animateTimer;
            animateTimer = curTime;
            // Compute the amount of pixels to move (vertically down the screen)
            y += (speed * elapsedTime);
            // Draw bug on screen


//even after  I changed x axis for roches  correctly, it won't work adjectly ,how i asked to be worked
// because of taken two tread bugs will use same "d" and "e" value,
// anyway it is good as it make bugs movement unpredictable, so I will keep it as it is
            if(x>canvas.getWidth()/2){

                if(d){
                    x1 -= (canvas.getWidth() * elapsedTime * 0.1);  //x1 helps to change direction of roches...
                    if(x1==0){d=false;}
                }
                else
                    x1+=(canvas.getWidth()*elapsedTime*0.1);

            }

            else if(x<=canvas.getWidth()/2) {
                if(e) {
                    x1 += (canvas.getWidth() * elapsedTime * 0.1);
                    if (x1 == canvas.getWidth() - Assets.lady1.getWidth()) {
                        e = false;
                    }//so it won't go out of the screen
                }
                else
                    x1 -= (canvas.getWidth() * elapsedTime * 0.1);
            }


            if(a) {  //if else taken to change 2 images one by one ,so roches looks like they are walking
                canvas.drawBitmap(Assets.lady1, x1, y, null);
                // Draw bug on screen
                a=false;
            }
            else
            {
                canvas.drawBitmap(Assets.lady2, x1, y, null);
                // ADD CODE HERE - Draw each frame of animation as appropriate - don't just draw 1 frame
                a=true;
            }
            // Has it reached the bottom of the screen?
            if (y >= canvas.getHeight()) {
                // Kill the bug
                state = SBugState.Dead;
                // Subtract 1 life

            }
        }
    }

    // Process touch to see if kills bug - return true if bug killed
    public boolean touched (Canvas canvas, int touchx, int touchy) {
        boolean touched = false;
        x=x1;   // x1 value needs to store back to x
        // Make sure this bug is alive
        if (state == SBugState.Alive) {
            // Compute distance between touch and center of bug
            float dis = (float)(Math.sqrt ((touchx - x) * (touchx - x) + (touchy - y) * (touchy - y)));
            // Is this close enough for a kill?
            if (dis <= Assets.lady1.getWidth()*0.75f) {
                state = SBugState.DrawDead;	// need to draw dead body on screen for a while
                touched = true;
                // Record time of death
                deathTime = System.nanoTime() / 1000000000f;

            }

        }
        return (touched);
    }

    // Draw dead bug body on screen, if needed
    public void drawDead (Canvas canvas) {
        if (state == SBugState.DrawDead) {
            canvas.drawBitmap(Assets.lady3, x,  y, null);
            Assets.livesLeft=0;
            //  Assets.highscore++;
            // Get time since death
            float curTime = System.nanoTime() / 1000000000f;
            float timeSinceDeath = curTime - deathTime;
            // Drawn dead body long enough (4 seconds) ?
            if (timeSinceDeath > 2)
                state = SBugState.Dead;
        }
    }
}
