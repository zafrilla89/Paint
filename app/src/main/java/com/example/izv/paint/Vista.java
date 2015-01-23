package com.example.izv.paint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.security.Principal;

/**
 * Created by ZAFRA on 21/01/2015.
 */
public class Vista extends View implements Serializable {

    private Paint pincel;
    private int alto, ancho;
    private Bitmap mapaDeBits;
    private Canvas lienzoFondo;
    int forma = 1;
    float tamaño;
    private Path[] rectaPoligonal = new Path[3];
    private Recta[] coordenadas = new Recta[3];

    class Recta {
        public float x0, y0, xi, yi;

        Recta(float x0, float xi, float y0, float yi) {
            this.x0 = x0;
            this.xi = xi;
            this.y0 = y0;
            this.yi = yi;
        }
    }

    public Vista(Context context) {
        super(context);
        pincel = new Paint();
        pincel.setColor(Color.BLACK);
        pincel.setAntiAlias(true);
        tamaño = 1;
        pincel.setStrokeWidth(tamaño);
        for (int i = 0; i < 3; i++) {
            rectaPoligonal[i] = new Path();
            coordenadas[i] = new Recta(-1, -1, -1, -1);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mapaDeBits, 0, 0, null);
        lienzoFondo.drawLine(coordenadas[0].x0,coordenadas[0].y0,coordenadas[0].xi,coordenadas[0].yi,pincel);
        lienzoFondo.drawLine(coordenadas[1].x0,coordenadas[1].y0,coordenadas[1].xi,coordenadas[1].yi,pincel);
        lienzoFondo.drawLine(coordenadas[2].x0,coordenadas[2].y0,coordenadas[2].xi,coordenadas[2].yi,pincel);



        if (forma == 1 || forma == 2 || forma == 3) {
            canvas.drawLine(x0, y0, xi, yi, pincel);
        } else {
            if (forma == 5 || forma == 6) {
                float xorigen = Math.min(x0, xi);
                float xdestino = Math.max(x0, xi);
                float yorigen = Math.min(y0, yi);
                float ydestino = Math.max(y0, yi);
                canvas.drawRect(xorigen, yorigen, xdestino, ydestino, pincel);
            } else {
                if (forma == 7 || forma == 8) {
                    canvas.drawCircle(x0, y0, (float) radio, pincel);
                } else {
                    if (forma == 9 || forma == 10) {
                        float xx = Math.min(x0, xi);
                        float xxx = Math.max(x0, xi);
                        float yy = Math.min(y0, yi);
                        float yyy = Math.max(y0, yi);
                        RectF rf = new RectF(xx, yy, xxx, yyy);
                        canvas.drawOval(rf, pincel);
                    }
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mapaDeBits = Bitmap.createBitmap(w, h,
                Bitmap.Config.ARGB_8888);
        lienzoFondo = new Canvas(mapaDeBits);
        lienzoFondo.drawRGB(255, 255, 255);
        alto = h;
        ancho = w;
    }

    private float x0 = -1, y0 = -1, xi = -1, yi = -1;
    private double radio = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            int dedo=0;
            if (forma==12){
                dedo=1;
            }else {
                if (forma == 13) {
                    dedo = 2;
                }
            }
            int numPuntos = event.getPointerCount();
            for (int i = 0; i < numPuntos; i++) {
                int numPuntero = event.getPointerId(i);
            float x = event.getX(i);
            float y = event.getY(i);
                if (numPuntero > dedo)
                    continue;
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_POINTER_DOWN:;
                    coordenadas[numPuntero].x0 = coordenadas[numPuntero].xi = x;
                    coordenadas[numPuntero].y0 = coordenadas[numPuntero].yi = y;
                    invalidate();
                    break;
                case MotionEvent.ACTION_DOWN:
                    x0 = x;
                    y0 = y;
                    if (forma == 5 || forma == 7 || forma == 9) {
                        pincel.setStyle(Paint.Style.STROKE);
                    } else {
                        if (forma == 6 || forma == 8 || forma == 10) {
                            pincel.setStyle(Paint.Style.FILL);
                        }else{
                            if (forma==12 || forma==13){
                                coordenadas[numPuntero].x0 = coordenadas[numPuntero].xi = x;
                                coordenadas[numPuntero].y0 = coordenadas[numPuntero].yi = y;
                                invalidate();
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    xi = x;
                    yi = y;
                    if (forma == 1) {
                        lienzoFondo.drawLine(x0, y0, xi, yi, pincel);
                        x0 = x;
                        y0 = y;
                    } else {
                        if (forma == 3) {
                            lienzoFondo.drawLine(x0, y0, xi, yi, pincel);
                        } else {
                            if (forma == 4) {
                                lienzoFondo.drawCircle(xi, yi, (float) 2, pincel);
                            } else {
                                if (forma == 7 || forma == 8) {
                                    radio = Math.sqrt(Math.pow(xi - x0, 2) + Math.pow(yi - y0, 2));
                                } else{
                                    if (forma==12 || forma==13){
                                        coordenadas[numPuntero].xi = x;
                                        coordenadas[numPuntero].yi = y;
                                        lienzoFondo.drawLine(coordenadas[numPuntero].x0, coordenadas[numPuntero].y0, coordenadas[numPuntero].xi, coordenadas[numPuntero].yi, pincel);
                                        coordenadas[numPuntero].x0 = x;
                                        coordenadas[numPuntero].y0 = y;
                                    }
                                }
                            }
                        }
                    }
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    if (forma == 2) {
                        xi = x;
                        yi = y;
                        lienzoFondo.drawLine(x0, y0, xi, yi, pincel);
                    } else {
                        if (forma == 5 || forma == 6) {
                            lienzoFondo.drawRect(x0, y0, xi, yi, pincel);
                        } else {
                            if (forma == 7 || forma == 8) {
                                lienzoFondo.drawCircle(x0, y0, (float) radio, pincel);
                                radio = 0;
                                x0 = y0 = xi = yi = -1;
                            } else {
                                if (forma == 9 || forma == 10) {
                                    float xx = Math.min(x0, xi);
                                    float xxx = Math.max(x0, xi);
                                    float yy = Math.min(y0, yi);
                                    float yyy = Math.max(y0, yi);
                                    RectF rf = new RectF(xx, yy, xxx, yyy);
                                    lienzoFondo.drawOval(rf, pincel);
                                } else {
                                    if (forma==12 || forma==13){
                                        Recta r = coordenadas[numPuntero];
                                        lienzoFondo.drawLine(r.x0, r.y0, r.xi, r.yi, pincel);
                                    }
                                }
                            }
                        }
                    }
                    invalidate();
                    x0 = y0 = xi = yi = -1;
                    break;
            }
        }
        return true;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public void nuevo() {
        mapaDeBits = Bitmap.createBitmap(ancho, alto,
                Bitmap.Config.ARGB_8888);
        lienzoFondo = new Canvas(mapaDeBits);
        lienzoFondo.drawRGB(255, 255, 255);
        invalidate();
    }

    public void cargar(String s) {
        File carpeta = new
                File(Environment.getExternalStorageDirectory().getPath());
        File archivo = new File(s);
        mapaDeBits = Bitmap.createBitmap(ancho, alto,
                Bitmap.Config.ARGB_8888);
        Log.v("AAAAAAA",archivo.exists()+"");
        if (archivo.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            mapaDeBits = BitmapFactory.decodeFile(
                    archivo.getAbsolutePath(), options);
        }
        lienzoFondo = new Canvas(mapaDeBits);
        invalidate();
    }

    public Bitmap guardar() {
        return mapaDeBits;
    }

    public void tamañoPincel(float tamaño) {
        pincel.setStrokeWidth(tamaño);
    }

    public void forma(int fo) {
        forma = fo;
    }

    public void color(int color) {
        pincel.setColor(color);
    }
}
