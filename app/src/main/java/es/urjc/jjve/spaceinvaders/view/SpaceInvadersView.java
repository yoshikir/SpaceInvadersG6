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
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import es.urjc.jjve.spaceinvaders.controllers.ViewController;
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

    private ViewController spaceInvadersController;

    // How menacing should the sound be?
    private long menaceInterval = 1000;
    // Which menace sound should play next
    private boolean uhOrOh;
    // When did we last play a menacing sound
    private long lastMenaceTime = System.currentTimeMillis();

    public SpaceInvadersView(Context context, int x, int y, Boolean underage) {


        super(context);
        // The next line of code asks the
        // SurfaceView class to set up our object.
        this.eventObservable= new ViewObservable();


        // Make a globally available copy of the context so we can use it in another method
        this.context = context;

        // Initialize ourHolder and paint objects
        ourHolder = this.getHolder();
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


    public void drawBackground(){


            // Lock the canvas ready to draw
            // Draw the background color
        try {
            if(canvas != null)
                canvas.drawColor(Color.argb(255, 0, 0, 0));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void drawGameObject(RectF rect) {




        canvas.drawRect(rect, paint);


    }



    public void drawGameObject(Bitmap bitmap, float x, float y){
        // Make sure our drawing surface is valid or we crash
        Surface surface = ourHolder.getSurface();

        boolean surfValid = surface.isValid();

        if (surfValid) { // ITS FALSE ALWAYS why?????????????
            // Lock the canvas ready to draw


            // Choose the brush color for drawing

            // Now draw the Game Object
            canvas.drawBitmap(bitmap, x, y , paint);


            // Draw everything to the screen
        }
    }

    public void drawGameObject(String text, int x, int y){



        paint.setTextSize(40);

        canvas.drawText(text,x,y,paint);



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


    public void lockCanvas(){

        try {
            if(ourHolder.getSurface().isValid()){
                canvas = ourHolder.lockCanvas();
            }else{
                System.out.println(ourHolder.getSurface().isValid());
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void setPaintGameObject(){
         paint.setColor(Color.argb(255,  249, 129, 0));
    }


    public void unlockCanvas(){
        ourHolder.unlockCanvasAndPost(canvas);
    }


    public void setObserver(Observer obs){
        this.eventObservable.addObserver(obs);
    }

    public ViewController getController() {
        return this.spaceInvadersController;
    }
}