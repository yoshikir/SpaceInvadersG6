package com.example.markitos.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class Marcianos {

    RectF rect;
    private Bitmap bitmapMarciano;

    //largo y ancho del invader
    private int largo;
    private int ancho;

    //dar forma al invader
    private int x;
    private int y;

    //velocidad de los marcianos
    private int velocidadMarcianos;
    public int izquierda=1;
    public int derecha=2;
    private int movimiento=derecha;

    boolean isVisible;

    //constructor
    public Marcianos(Context context, int fila, int columna, int coordenadaX, int coordenadaY){
        //inicializamos el rect vacio

        rect =new RectF();
        largo=coordenadaX/10;
        ancho=coordenadaY/10;

        isVisible=true;

        int padding=coordenadaX/25;
        x=columna*(largo+padding);
        y=fila*(largo+padding/5);

        //meter imagen de dibujo
        bitmapMarciano=BitmapFactory.decodeResource(context.getResources(),R.drawable.marciano);
        bitmapMarciano=Bitmap.createScaledBitmap(bitmapMarciano,largo,ancho,false);

        velocidadMarcianos=25;
    }

    //movimiento
    public void update(int fps) {
        if (movimiento == izquierda) {
            x = x - velocidadMarcianos / fps;
        }

        if (movimiento == derecha) {
            x = x + velocidadMarcianos / fps;
        }
    }

    //movimiento inverso
    public void movimientoInverso(){
            if(movimiento==izquierda){
                movimiento=derecha;
            }else{
                movimiento=izquierda;
            }
            y=y+largo;
        }












    //generamos getters and setters

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }

    public Bitmap getBitmapMarciano() {
        return bitmapMarciano;
    }

    public void setBitmapMarciano(Bitmap bitmapMarciano) {
        this.bitmapMarciano = bitmapMarciano;
    }

    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVelocidadMarcianos() {
        return velocidadMarcianos;
    }

    public void setVelocidadMarcianos(int velocidadMarcianos) {
        this.velocidadMarcianos = velocidadMarcianos;
    }

    public int getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(int izquierda) {
        this.izquierda = izquierda;
    }

    public int getDerecha() {
        return derecha;
    }

    public void setDerecha(int derecha) {
        this.derecha = derecha;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
