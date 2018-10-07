package es.urjc.jjve.spaceinvaders.view;

import java.util.Observable;

/**
 * Created by Christian on 03/10/2018.
 */

public class ViewObservable extends Observable {


    private static final String MOVEMENT_CODE="mov";
    private static final String SHOOT_CODE="sht";



    public void notifyMovement(int movement){

        notifyObservers(MOVEMENT_CODE+"|"+movement);
        setChanged();

    }

    public void notifyShoot(){

        notifyObservers(SHOOT_CODE);
        setChanged();
    }

}
