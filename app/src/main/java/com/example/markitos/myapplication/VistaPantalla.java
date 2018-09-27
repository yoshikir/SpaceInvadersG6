package com.example.markitos.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class VistaPantalla  extends SurfaceView {

    Context context;

    //bloquear superficie de los graficos
    private SurfaceHolder pantalla;

    //canvas y pintar
    private Canvas canvas;
    private Paint paint;

    //velocidad
    private int fps;

    //tiempo de frame
    private int tiempo;

    //tamaño de pixels
    private int horizontal;
    private int vertical;

    //pintar invaders
    Marcianos[]mars=new Marcianos[10];
    int cantidadMarcianos=0;

    public VistaPantalla(Context context,int coordenadaX,int coordenadaY){
        super(context);
        this.context=context;

        pantalla=getHolder();
        paint=new Paint();

        horizontal=coordenadaX;
        vertical=coordenadaY;
    }

    private void dibujar(){
        if(pantalla.getSurface().isValid()){
            canvas=pantalla.lockCanvas();
            //color de fondo
            canvas.drawColor(Color.BLUE);
            paint.setColor(Color.WHITE);
        }
    }
}
