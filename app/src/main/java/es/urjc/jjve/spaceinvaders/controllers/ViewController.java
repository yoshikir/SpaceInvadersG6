package es.urjc.jjve.spaceinvaders.controllers;

import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;

import es.urjc.jjve.spaceinvaders.entities.Bullet;
import es.urjc.jjve.spaceinvaders.entities.DefenceBrick;
import es.urjc.jjve.spaceinvaders.entities.Invader;
import es.urjc.jjve.spaceinvaders.entities.PlayerShip;
import es.urjc.jjve.spaceinvaders.view.SpaceInvadersView;

/**
 * Created by Christian on 03/10/2018.
 */

public class ViewController implements Observer,Runnable {


    // For sound FX
//    private SoundPool soundPool;
//    private int playerExplodeID = -1;
//    private int invaderExplodeID = -1;
//    private int shootID = -1;
//    private int damageShelterID = -1;
//    private int uhID = -1;
//    private int ohID = -1;
//    private long menaceInterval = 1000;
//    // Which menace sound should play next
//    private boolean uhOrOh;
//    // When did we last play a menacing sound
//    private long lastMenaceTime = System.currentTimeMillis();


    // This is used to help calculate the fps
    private long timeThisFrame;
    // This variable tracks the game frame rate
    private long fps;

    // Game is paused at the start
    private boolean paused = true;

    // A boolean which we will set and unset
    // when the game is running- or not.
    private volatile boolean playing;

    private SpaceInvadersView view;


    private PlayerShip playerShip;

    private boolean lost;
    private int score;



    // The player's bullet
    private Bullet bullet;

    // The invaders bullets
    private Bullet[] invadersBullets = new Bullet[200];
    private int nextBullet;
    private int maxInvaderBullets = 10;

    // Up to 60 invaders
    Invader[] invaders = new Invader[60];
    int numInvaders = 0;

    // The player's shelters are built from bricks
    private DefenceBrick[] bricks = new DefenceBrick[400];
    private int numBricks;

    private Thread gameThread = null;

    //Determines the game bounds
    private int screenX;
    private int screenY;


    public ViewController(Context context, int x, int y){


        this.screenX=x;
        this.screenY=y;

        this.view= new SpaceInvadersView(context,x,y,this);

        this.initGame(context);


    }

    @Override
    public void update(Observable observable, Object o) {

        //parsea o y decide que modifica

    }

    @Override
    public void run() {
        while (playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            updateGame();

            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }

            // We will do something new here towards the end of the project
            // Play a sound based on the menace level
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


        }
    }

    public void updateGame(){


        view.drawBackground();

        paintInvaders();

        paintBricks();

        if(bullet.getStatus()){
            view.drawGameObject(bullet.getRect());
        }

        for(Bullet bullet:invadersBullets){
            if(bullet.getStatus()) {
                view.drawGameObject(bullet.getRect());
            }
        }

        view.drawGameObject("Score: " + score , 10,50);

    }


    public void updateEntities(){

        boolean bumpedEntity = false;

        for(Invader i:invaders){

            if(i.getVisibility()) {
                // Move the next invader
                i.update(fps);

                // Does he want to take a shot?
                if(i.takeAim(playerShip.getX(),
                        playerShip.getLength())){

                    // If so try and spawn a bullet
                    if(invadersBullets[nextBullet].shoot(i.getX()
                                    + i.getLength() / 2,
                            i.getY(), bullet.DOWN)) {

                        // Shot fired
                        // Prepare for the next shot
                        nextBullet++;

                        // Loop back to the first one if we have reached the last
                        if (nextBullet == maxInvaderBullets) {
                            // This stops the firing of another bullet until one completes its journey
                            // Because if bullet 0 is still active shoot returns false.
                            nextBullet = 0;
                        }
                    }
                }

                // If that move caused them to bump the screen change bumped to true
                if (i.getX() > screenX - i.getLength()
                        || i.getX() < 0){

                    bumpedEntity = true;

                }
            }

            if(bumpedEntity){

                // Move all the invaders down and change direction
                for(Invader inv:invaders){
                    inv.dropDownAndReverse();
                    // Have the invaders landed
                    if(inv.getY() > screenY - screenY / 10){
                        lost = true;
                    }
                }


            }

        }

        // Update all the invaders bullets if active
        for(Bullet bullet:invadersBullets){
            if(bullet.getStatus()) {
                bullet.update(fps);
            }
        }

    }


    // If SpaceInvadersActivity is started then
    // start our thread.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    public void initGame(Context context){
        // Make a new player space ship
        playerShip = new PlayerShip(context, screenX, screenY);

        // Reset the menace level


        // Prepare the players bullet
        bullet = new Bullet(screenY);

        // Initialize the invadersBullets array
        for(int i = 0; i < invadersBullets.length; i++){
            invadersBullets[i] = new Bullet(screenY);
        }

        // Build an army of invaders
        numInvaders = 0;
        for(int column = 0; column < 6; column ++ ){
            for(int row = 0; row < 5; row ++ ){
                invaders[numInvaders] = new Invader(context, row, column, screenX, screenY);
                numInvaders ++;
            }
        }

        // Build the shelters
        numBricks = 0;
        for(int shelterNumber = 0; shelterNumber < 4; shelterNumber++){
            for(int column = 0; column < 10; column ++ ) {
                for (int row = 0; row < 5; row++) {
                    bricks[numBricks] = new DefenceBrick(row, column, shelterNumber, screenX, screenY);
                    numBricks++;
                }
            }
        }
    }

    public void paintInvaders(){
        for(Invader i:invaders){
            if(i.getVisibility()) {

                this.view.drawGameObject(i.getBitmap(), i.getX(), i.getY());

            }
        }
    }

    public void paintBricks(){
        for(DefenceBrick b:bricks){
            if(b.getVisibility()) {
                view.drawGameObject(b.getRect());
            }
        }

    }

    public SpaceInvadersView getView() {
        return view;
    }

    public void setView(SpaceInvadersView view) {
        this.view = view;
    }
}
