package com.example.group6.spaceinvaders.core.model;

import android.graphics.Color;

import java.util.List;

/**
 * Created by Christian on 28/09/2018.
 */

public abstract class GameObject {

    private int width;
    private int height;
    private int speed;
    private Vector2 possition;

    private List<Object> components;

    private Color color;

    public void setColor(Color color){
        this.color= color;
    }

    public void setSize(int width, int height){
        this.width=width;
        this.height=height;
    }

    public void setCenter(Vector2 center){
        this.possition=center;
    }

}
