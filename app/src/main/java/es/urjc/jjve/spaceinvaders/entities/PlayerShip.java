package es.urjc.jjve.spaceinvaders.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import es.urjc.jjve.spaceinvaders.R;

public class PlayerShip {
    RectF rect;

    // La nave espacial del jugador será representada por un Bitmap
    private Bitmap bitmap;

    // Que tan ancho y alto puede llegar nuestra nave espacial
    private float length;
    private float height;

    // X es la parte extrema a la izquierda del rectángulo el cual forma nuestra nave espacial
    private float x;

    // Y es la coordenada de a mero arriba
    private float y;

    // Esto va a mantener la rapidez de los pixeles por segundo a la que la nave espacial se moverá
    private float shipSpeed;

    // En qué direcciones se puede mover la nave espacial
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    // Se esta moviendo la nave espacial y en que dirección
    private int shipMoving = STOPPED;

    // Este es el método del constructor
    // Cuando creamos un objeto de esta clase daremos
    // la anchura y la altura de la pantalla
    public PlayerShip(Context context, int screenX, int screenY){

        
        rect = new RectF(this.height/2, this. width/2, this.height/2, this.width/2);

        length = screenX/10;
        height = screenY/10;

        // Inicia la nave en el centro de la pantalla aproximadamente
        x = screenX / 2;
        y = screenY - 20;

        // Inicializa el bitmap
        bitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.playership);

        // ajusta el bitmap a un tamaño proporcionado a la resolución de la pantalla
        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (length),
                (int) (height),
                false);

        // Qué tan rápido va la nave espacial en pixeles por segundo
        shipSpeed = 350;
    }

    public RectF getRect(){
        return rect;
    }

    // Este es un método de "get" para hacer el rectángulo que
    // define nuestra nave espacial disponible en la clase de SpaceInvadersView
    public Bitmap getBitmap(){
        return bitmap;
    }

    public float getX(){
        return x;
    }

    public float getLength(){
        return length;
    }

    // Este método será usado para cambiar/establecer si la nave
    // espacial va a la izquierda, la derecha o no se mueve
    public void setMovementState(int state){
        shipMoving = state;
    }

    // Este método de update será llamado desde el update en SpaceInvadersView
    // Determina si la nave espacial del jugador necesita moverse y cambiar las coordenadas
    // que están en x si es necesario
    public void update(long fps){
        if(shipMoving == LEFT){
            x = x - shipSpeed / fps;
        }

        if(shipMoving == RIGHT){
            x = x + shipSpeed / fps;
        }

        // Actualiza rect el cual es usado para detectar impactos
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;

    }
}
