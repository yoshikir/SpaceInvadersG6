package es.urjc.jjve.spaceinvaders.controllers;

import android.content.Context;
import android.graphics.RectF;
import android.media.SoundPool;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Pattern;

import es.urjc.jjve.spaceinvaders.entities.Bullet;
import es.urjc.jjve.spaceinvaders.entities.DefenceBrick;
import es.urjc.jjve.spaceinvaders.entities.Invader;
import es.urjc.jjve.spaceinvaders.entities.PlayerShip;
import es.urjc.jjve.spaceinvaders.view.SpaceInvadersView;

/**
 * Created by Christian on 03/10/2018.
 */

public class ViewController implements Runnable, Observer {


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


    private static final int MAX_INVADER_BULLETS = 300;

    private boolean underage;


    // This is used to help calculate the fps
    private long timeThisFrame;
    // This variable tracks the game frame rate

    private long fps = 20;

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
    private List<Bullet> invadersBullets;
    private List<Bullet> playerBullets;
    private int nextBullet;
    private int maxInvaderBullets = 10;

    // Up to 60 invaders
    List<Invader> invaders;
    int numInvaders = 0;

    // The player's shelters are built from bricks
    private List<DefenceBrick> bricks;
    private int numBricks;

    private Thread gameThread = null;

    //Determines the game bounds
    private int screenX;
    private int screenY;

    private Context context;

    public ViewController(Context context, int x, int y, SpaceInvadersView view) {


        this.screenX = x;
        this.screenY = y;

        this.view= view;
        this.playerBullets= new ArrayList<>();


        this.context = context;
        this.initGame(context);


    }


    @Override
    public void run() {


        while (playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.nanoTime();
///////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (!paused) {
                if (!updateEntities()) {
                    initGame(this.context);
                }

            }

            updateGame();

            //ToDo show start again button if updateEntities returns false


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


    /**
     * This method is used every game tick to update the positions of every entity and the score
     * Should be called when the entity is modified only, ToDo for next sprint, update entities on screen only when they are modified
     */

    public void updateGame() {


        view.lockCanvas();
        view.drawBackground();

        view.setPaintGameObject();

        paintInvaders();


        paintBricks();

        paintBullets();

        paintShip();

        view.unlockCanvas();


        view.drawGameObject("Score: " + score, 10, 50);


    }

    private void paintBullets() {

        for (Bullet shipBull : this.playerBullets) {
            view.drawGameObject(shipBull.getRect());
        }

        for (Bullet bullet : invadersBullets) {
            if (bullet.getStatus()) {
                view.drawGameObject(bullet.getRect());
            }
        }
    }


    /**
     * This method is used to update the entity position and the actions they are going to take
     * it checks if the invaders have reached the screen limit, and if they bumped into a brick barrier
     * also it checks if the invader has the opportunity to shoot
     * updates the bullet possition depending on the fps attribute
     */
    public boolean updateEntities() {

        //checks if any entity has reached a limit or another entity
        boolean bumpedEntity = false;

        //moves the spaceship
        playerShip.update(fps);

        //For each invader, we check if its an active one and then we check if it has the opportunity to shoot
        //if the invader has reached the screen limit, it reverses the direction and goes down
        for (Invader i : invaders) {

            if (i.getVisibility()) {
                // Move the next invader
                i.update(fps);

                // Does he want to take a shot?
                if (i.takeAim(playerShip.getX(),
                        playerShip.getLength())) {

                    // If so try and spawn a bullet
                    if (invadersBullets.get(nextBullet).shoot(i.getX()
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
                        || i.getX() < 0) {

                    bumpedEntity = true;

                }
            }

            //Checks if an invader has touched the playership
            if (bumpedEntity) {

                // Move all the invaders down and change direction
                for (Invader inv : invaders) {
                    inv.dropDownAndReverse();
                    // Have the invaders landed
                    if (RectF.intersects(i.getRect(), playerShip.getRect())) {
                        return false;
                    }

                }
                return true;


            }

        }

        if(!underage) {

            // Update the players bullet

            for (int i=playerBullets.size()-1;i>=0;i--) {
                Bullet currentBull = playerBullets.get(i);
                currentBull.update(fps);
                //Colisión de bala con invader
                if(currentBull.getStatus()) {
                    if(!checkImpactWithInvaders(currentBull)){

                    }

                    //Colisión de bala con muros
                    for (DefenceBrick brick : bricks) {
                        if (brick.getVisibility()) {
                            if (RectF.intersects(bullet.getRect(), brick.getRect())) {
                                // A collision has occurred
                                currentBull.setInactive();
                                brick.setInvisible();
//                          soundPool.play(damageShelterID, 1, 1, 0, 0, 1);
                            }
                        }
                    }
                    //Colisión de bala con borde de pantalla
                    if (currentBull.getImpactPointY() < 0) {
                        currentBull.changeDir();
                    }
                }else {
                    playerShip.removeBullet(currentBull);
                }
            }

            // Has the player's bullet hit the top of the screen


            // Update all the invaders bullets if active
            for (Bullet bullet : invadersBullets) {
                if (bullet.getStatus()) {
                    bullet.update(fps);
                }
                if (bullet.getImpactPointY() > screenY) {
                    bullet.setInactive();
                }
            }

            // Has an invaders bullet hit the bottom of the screen


            // Has the player's bullet hit an invader
//            if (this.bullet.getStatus()) {
//                for (Invader inv : invaders) {
//                    if (inv.getVisibility()) {
//                        if (RectF.intersects(bullet.getRect(), inv.getRect())) {
//                            inv.setInvisible();
////                        soundPool.play(invaderExplodeID, 1, 1, 0, 0, 1);
//                            playerBullets.remove(bullet);
//                            score = score + 100;
//
//                            // Has the player won
//                            if (score == numInvaders * 100) {
//                                paused = true;
//                                score = 0;
//                                initGame(this.context);
//                            }
//                        }
//                    }
//                }
//            }

            // Has an alien bullet hit a shelter brick
            for (Bullet bullet : invadersBullets) {
                if (bullet.getStatus()) {
                    for (DefenceBrick brick : bricks) {
                        if (brick.getVisibility()) {
                            if (RectF.intersects(bullet.getRect(), brick.getRect())) {
                                // A collision has occurred
                                bullet.setInactive();
                                brick.setInvisible();
//                            soundPool.play(damageShelterID, 1, 1, 0, 0, 1);
                            }
                        }
                    }
                }

            }

            // Has a player bullet hit a shelter brick

            for (Bullet bullet:this.playerBullets) {
                if (bullet.getStatus()) {

                }
            }

            // Has an invader bullet hit the player ship
            for (Bullet bullet : invadersBullets) {
                if (bullet.getStatus()) {
                    if (RectF.intersects(playerShip.getRect(), bullet.getRect())) {
                        bullet.setInactive();

                        return false;
//                  soundPool.play(playerExplodeID, 1, 1, 0, 0, 1);


                    }
                }
            }
        }

        return true;

    }


    // If SpaceInvadersActivity is started then
    // start our thread.
    public void resume() {
        playing = true;
        paused=false;

    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    public void initGame(Context context) {
        // Make a new player space ship
        playerShip = new PlayerShip(context, screenX, screenY);

        this.invaders = new ArrayList<>();
        this.invadersBullets = new ArrayList<>();
        this.bricks = new ArrayList<>();

        //view.drawGameObject(playerShip.getBitmap());
        // Reset the menace level

        if (!underage) {
            // Prepare the players bullet
            bullet = new Bullet(screenY);

            // Initialize the invadersBullets array
            for (int i = 0; i < MAX_INVADER_BULLETS; i++) {
                invadersBullets.add(new Bullet(screenY));
            }
        }
        // Build an army of invaders
        numInvaders = 0;
        for (int column = 0; column < 6; column++) {
            for (int row = 0; row < 5; row++) {
                invaders.add(new Invader(context, row, column, screenX, screenY));
                numInvaders++;
            }
        }


        // Build the shelters
        numBricks = 0;
        for (int shelterNumber = 0; shelterNumber < 4; shelterNumber++) {
            for (int column = 0; column < 10; column++) {
                for (int row = 0; row < 5; row++) {
                    bricks.add(new DefenceBrick(row, column, shelterNumber, screenX, screenY));
                    numBricks++;
                }
            }
        }


    }


    private boolean checkImpactWithInvaders(Bullet bullet){
        Invader inv= null;
        int i=0;
        while(bullet.getStatus() && i<invaders.size() ){
            inv = invaders.get(i);
            if (inv.getVisibility()) {
                if (RectF.intersects(bullet.getRect(), inv.getRect())) { //Has a bullet hit an invader?
                    inv.setInvisible();
//                          soundPool.play(invaderExplodeID, 1, 1, 0, 0, 1);
                    bullet.setInactive();
                    score = score + 100;

                    // Has the player won
                    if (score == numInvaders * 100) {
                        paused = true;
                        score = 0;
                        initGame(this.context);
                    }
                }
            }

            i++;
        }
        return RectF.intersects(bullet.getRect(), inv.getRect());
    }

    private void paintShip() {


        view.drawGameObject(playerShip.getBitmap(), playerShip.getX(), screenY - 50);

    }

    public void paintInvaders() {


        for (Invader i : invaders) {
            if (i.getVisibility()) {

                this.view.drawGameObject(i.getBitmap(), i.getX(), i.getY());

            }
        }

    }

    public void paintBricks() {

        for (DefenceBrick b : bricks) {
            try {
                if (b.getVisibility()) {
                    view.drawGameObject(b.getRect());
                }
            } catch (RuntimeException e) {

                System.out.println("Peta" + b.toString());

            }

        }


    }


    public SpaceInvadersView getView() {
        return view;
    }

    public void setView(SpaceInvadersView view) {
        this.view = view;
    }

    @Override
    public void update(Observable observable, Object o) {


        String splitter = Pattern.quote("|");
        String[] split = ((String) o).split(splitter);

        switch (split[0]) {

            case "mov":

                if (paused) {
                    paused = false;

                }
                playerShip.setMovementState(Integer.parseInt(split[1]));

                playerShip.update(fps);

                break;
            case "sht":

                if (!underage) {
                    //Instance a new bullet and shoot it
                    Bullet bullet = new Bullet(screenY);
                    if (this.playerBullets.size()<5) {
                        this.playerBullets.add(bullet);
                        bullet.shoot(playerShip.getX() + playerShip.getLength() / 2, screenY - 100, bullet.UP);
                    }
                }

                break;
        }
        //parsea o y decide que modifica

    }

    public boolean isUnderage() {
        return underage;
    }

    public void setUnderage(boolean underage) {
        this.underage = underage;
    }
}
