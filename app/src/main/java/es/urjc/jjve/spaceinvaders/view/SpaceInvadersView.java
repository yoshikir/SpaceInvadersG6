package es.urjc.jjve.spaceinvaders.view;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import es.urjc.jjve.spaceinvaders.entities.Bullet;
import es.urjc.jjve.spaceinvaders.entities.DefenceBrick;
import es.urjc.jjve.spaceinvaders.entities.Invader;
import es.urjc.jjve.spaceinvaders.entities.PlayerShip;

/**
 * Clase utilizada para mostrar la interfaz del juego y manejar eventos dentro del juego, movimiento y disparo
 */

public class SpaceInvadersView extends SurfaceView {

    Context context;


    // Our SurfaceHolder to lock the surface before we draw our graphics
    private SurfaceHolder ourHolder;


    // A Canvas and a Paint object
    private Canvas canvas;
    private Paint paint;


    // The size of the screen in pixels
    private int screenX;
    private int screenY;


    private ViewObservable eventObservable;



//    // For sound FX
//    private SoundPool soundPool;
//    private int playerExplodeID = -1;
//    private int invaderExplodeID = -1;
//    private int shootID = -1;
//    private int damageShelterID = -1;
//    private int uhID = -1;
//    private int ohID = -1;



    // How menacing should the sound be?
    private long menaceInterval = 1000;
    // Which menace sound should play next
    private boolean uhOrOh;
    // When did we last play a menacing sound
    private long lastMenaceTime = System.currentTimeMillis();

    public SpaceInvadersView(Context context, int x, int y,Observer observer) {

        // The next line of code asks the
        // SurfaceView class to set up our object.
        super(context);
        this.eventObservable.addObserver(observer);

        // Make a globally available copy of the context so we can use it in another method
        this.context = context;

        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;

        // This SoundPool is deprecated but don't worry
//        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
//
//        try{
//            // Create objects of the 2 required classes
//            AssetManager assetManager = context.getAssets();
//            AssetFileDescriptor descriptor;
//
//            // Load our fx in memory ready for use
//            descriptor = assetManager.openFd("shoot.ogg");
//            shootID = soundPool.load(descriptor, 0);
//
//            descriptor = assetManager.openFd("invaderexplode.ogg");
//            invaderExplodeID = soundPool.load(descriptor, 0);
//
//            descriptor = assetManager.openFd("damageshelter.ogg");
//            damageShelterID = soundPool.load(descriptor, 0);
//
//            descriptor = assetManager.openFd("playerexplode.ogg");
//            playerExplodeID = soundPool.load(descriptor, 0);
//
//            descriptor = assetManager.openFd("damageshelter.ogg");
//            damageShelterID = soundPool.load(descriptor, 0);
//
//            descriptor = assetManager.openFd("uh.ogg");
//            uhID = soundPool.load(descriptor, 0);
//
//            descriptor = assetManager.openFd("oh.ogg");
//            ohID = soundPool.load(descriptor, 0);
//
//        }catch(IOException e){
//            // Print an error message to the console
//            Log.e("error", "failed to load sound files");
//        }


    }

//    private void prepareLevel(){
//
//        // Here we will initialize all the game objects
//
//        // Make a new player space ship
//        playerShip = new PlayerShip(context, screenX, screenY);
//
//        // Reset the menace level
//        menaceInterval = 1000;
//
//        // Prepare the players bullet
//        bullet = new Bullet(screenY);
//
//        // Initialize the invadersBullets array
//        for(int i = 0; i < invadersBullets.length; i++){
//            invadersBullets[i] = new Bullet(screenY);
//        }
//
//        // Build an army of invaders
//        numInvaders = 0;
//        for(int column = 0; column < 6; column ++ ){
//            for(int row = 0; row < 5; row ++ ){
//                invaders[numInvaders] = new Invader(context, row, column, screenX, screenY);
//                numInvaders ++;
//            }
//        }
//
//        // Build the shelters
//        numBricks = 0;
//        for(int shelterNumber = 0; shelterNumber < 4; shelterNumber++){
//            for(int column = 0; column < 10; column ++ ) {
//                for (int row = 0; row < 5; row++) {
//                    bricks[numBricks] = new DefenceBrick(row, column, shelterNumber, screenX, screenY);
//                    numBricks++;
//                }
//            }
//        }
//
//    }


    /**
     * This method should be in a controller
     */
//    @Override
//    public void run() {
//        while (playing) {
//
//            // Capture the current time in milliseconds in startFrameTime
//            long startFrameTime = System.currentTimeMillis();
//
//            // Update the frame
//            if(!paused){
//                update();
//            }
//
//            // Draw the frame
//            draw();
//
//            // Calculate the fps this frame
//            // We can then use the result to
//            // time animations and more.
//            timeThisFrame = System.currentTimeMillis() - startFrameTime;
//            if (timeThisFrame >= 1) {
//                fps = 1000 / timeThisFrame;
//            }
//
//            // We will do something new here towards the end of the project
//            // Play a sound based on the menace level
//            if(!paused) {
//                if ((startFrameTime - lastMenaceTime) > menaceInterval) {
//                    if (uhOrOh) {
//                        // Play Uh
//                        soundPool.play(uhID, 1, 1, 0, 0, 1);
//
//                    } else {
//                        // Play Oh
//                        soundPool.play(ohID, 1, 1, 0, 0, 1);
//                    }
//
//                    // Reset the last menace time
//                    lastMenaceTime = System.currentTimeMillis();
//                    // Alter value of uhOrOh
//                    uhOrOh = !uhOrOh;
//                }
//            }
//            // Reset the menace level
//            menaceInterval = 1000;
//
//        }
//
//    }

    private void update(){

        // Did an invader bump into the side of the screen
        boolean bumped = false;

        // Has the player lost
        boolean lost = false;

        // Move the player's ship
        playerShip.update(fps);

        // Update the invaders if visible


        // Update all the invaders if visible

        // Did an invader bump into the edge of the screen


        if(lost){
            prepareLevel();
        }

        // Update the players bullet
        if(bullet.getStatus()){
            bullet.update(fps);
        }

        // Has the player's bullet hit the top of the screen
        if(bullet.getImpactPointY() < 0){
            bullet.setInactive();
        }

        // Has an invaders bullet hit the bottom of the screen
        for(int i = 0; i < invadersBullets.length; i++){
            if(invadersBullets[i].getImpactPointY() > screenY){
                invadersBullets[i].setInactive();
            }
        }

        // Has the player's bullet hit an invader
        if(bullet.getStatus()) {
            for (int i = 0; i < numInvaders; i++) {
                if (invaders[i].getVisibility()) {
                    if (RectF.intersects(bullet.getRect(), invaders[i].getRect())) {
                        invaders[i].setInvisible();
//                        soundPool.play(invaderExplodeID, 1, 1, 0, 0, 1);
                        bullet.setInactive();
                        score = score + 10;

                        // Has the player won
                        if(score == numInvaders * 10){
                            paused = true;
                            score = 0;
                            lives = 3;
                            prepareLevel();
                        }
                    }
                }
            }
        }

        // Has an alien bullet hit a shelter brick
        for(int i = 0; i < invadersBullets.length; i++){
            if(invadersBullets[i].getStatus()){
                for(int j = 0; j < numBricks; j++){
                    if(bricks[j].getVisibility()){
                        if(RectF.intersects(invadersBullets[i].getRect(), bricks[j].getRect())){
                            // A collision has occurred
                            invadersBullets[i].setInactive();
                            bricks[j].setInvisible();
//                            soundPool.play(damageShelterID, 1, 1, 0, 0, 1);
                        }
                    }
                }
            }

        }

        // Has a player bullet hit a shelter brick
        if(bullet.getStatus()){
            for(int i = 0; i < numBricks; i++){
                if(bricks[i].getVisibility()){
                    if(RectF.intersects(bullet.getRect(), bricks[i].getRect())){
                        // A collision has occurred
                        bullet.setInactive();
                        bricks[i].setInvisible();
//                        soundPool.play(damageShelterID, 1, 1, 0, 0, 1);
                    }
                }
            }
        }

        // Has an invader bullet hit the player ship
        for(int i = 0; i < invadersBullets.length; i++){
            if(invadersBullets[i].getStatus()){
                if(RectF.intersects(playerShip.getRect(), invadersBullets[i].getRect())){
                    invadersBullets[i].setInactive();
                    lives --;
//                    soundPool.play(playerExplodeID, 1, 1, 0, 0, 1);

                    // Is it game over?
                    if(lives == 0){
                        paused = true;
                        lives = 3;
                        score = 0;
                        prepareLevel();

                    }
                }
            }
        }

    }

    public void drawBackground(){

        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();
            // Draw the background color
            canvas.drawColor(Color.argb(255, 26, 128, 182));
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void drawGameObject(RectF rect){

        paint.setColor(Color.argb(255,  255, 255, 255));
        canvas = ourHolder.lockCanvas();
        canvas.drawRect(rect,paint);

        ourHolder.unlockCanvasAndPost(canvas);
    }


    public void drawGameObject(Bitmap bitmap, float x, float y){
        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255,  255, 255, 255));
            // Now draw the Game Object
            canvas.drawBitmap(bitmap, x, y , paint);


            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void drawGameObject(String text, int x, int y){

        canvas = ourHolder.lockCanvas();
        paint.setColor(Color.argb(255,  249, 129, 0));
        paint.setTextSize(40);

        canvas.drawText(text,x,y,paint);

        ourHolder.unlockCanvasAndPost(canvas);

    }


    // If SpaceInvadersActivity is paused/stopped
    // shutdown our thread.
//    public void pause() {
//        playing = false;
//        try {
//            gameThread.join();
//        } catch (InterruptedException e) {
//            Log.e("Error:", "joining thread");
//        }
//
//    }

//    // If SpaceInvadersActivity is started then
//    // start our thread.
//    public void resume() {
//        playing = true;
//        gameThread = new Thread(this);
//        gameThread.start();
//    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                /**
                 * Checks if the touch event happened in the upper half of the screen
                 */
                if(motionEvent.getY() > screenY - screenY / 8) {

                    if (motionEvent.getX() > screenX / 2) {
                        eventObservable.notifyMovement(2);
                    } else {
                        eventObservable.notifyMovement(1);


                    }

                }else {
                    // Shots fired
                   eventObservable.notifyShoot();
                }
                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:

                eventObservable.notifyMovement(0);

                break;

        }

        return true;
    }
}