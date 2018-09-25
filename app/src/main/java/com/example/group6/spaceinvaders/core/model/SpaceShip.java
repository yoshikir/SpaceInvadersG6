package com.example.group6.spaceinvaders.core.model;

import android.graphics.Color;

import Engine.GameObject;
import Engine.Vector2;

/**
 *
 * Created by Christian on 25/09/2018.
 */

public class SpaceShip extends GameObject {

    //Number of lifes of the spaceship
    private int lifes;
    private Color color;


    public SpaceShip(Vector2 center, float height, float width, Color color){
        this.setSize(width,height);
        this.setColor(color);
        this.setCenter(center);
    }

    private void setColor(Color color){
        this.color= color;
    }

}
