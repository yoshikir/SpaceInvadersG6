package com.example.group6.spaceinvaders.controllers.game;

import android.graphics.Color;

import com.example.group6.spaceinvaders.core.model.Alien;
import com.example.group6.spaceinvaders.core.model.SpaceShip;

import java.util.List;

import Engine.Vector2;

/**
 * Created by Christian on 25/09/2018.
 */

public class GameController {

    private static final Vector2 SPACESHIP_POSITION = new Vector2(1,25);
    private static final int SPACESHIP_HEIGHT = 5;
    private static final int SPACESHIP_WIDTH=5;

    private Color shipColor;
    private List<Alien> Aliens;
    private SpaceShip spaceShip;

    public void initialize(){

        shipColor.blue(5);
        spaceShip= new SpaceShip(SPACESHIP_POSITION,SPACESHIP_HEIGHT,SPACESHIP_WIDTH,shipColor);


    }
}
