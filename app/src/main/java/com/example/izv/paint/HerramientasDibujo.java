package com.example.izv.paint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class HerramientasDibujo extends Activity {

    private Vista v;
    private int forma=1;
    private float tamaño=1;
    private ImageButton b1,b2,b3, b4, b5, b6, b7, b8, b9, b10;
    private Button d1, d2, d3, t1,t3,t5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herramientas_dibujo);
        iniciarComponentes();
        d1.setEnabled(false);
        t1.setEnabled(false);
    }

    public void iniciarComponentes(){
        b1=(ImageButton)findViewById(R.id.b1);
        b2=(ImageButton)findViewById(R.id.b2);
        b3=(ImageButton)findViewById(R.id.b3);
        b4=(ImageButton)findViewById(R.id.b4);
        b5=(ImageButton)findViewById(R.id.b5);
        b6=(ImageButton)findViewById(R.id.b6);
        b7=(ImageButton)findViewById(R.id.b7);
        b8=(ImageButton)findViewById(R.id.b8);
        b9=(ImageButton)findViewById(R.id.b9);
        b10=(ImageButton)findViewById(R.id.b10);
        t1=(Button)findViewById(R.id.t1);
        t3=(Button)findViewById(R.id.t3);
        t5=(Button)findViewById(R.id.t5);
        d1=(Button)findViewById(R.id.d1);
        d2=(Button)findViewById(R.id.d2);
        d3=(Button)findViewById(R.id.d3);
    }

    public void activarbotones(){
        d1.setEnabled(true);
        d2.setEnabled(true);
        d3.setEnabled(true);
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);
        b5.setEnabled(true);
        b6.setEnabled(true);
        b7.setEnabled(true);
        b8.setEnabled(true);
        b9.setEnabled(true);
        b10.setEnabled(true);
    }

    public void aceptar(View v){
        Intent i = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("forma", forma);
        bundle.putFloat("tamaño", tamaño);
        i.putExtras(bundle);
        setResult(Activity.RESULT_OK, i);
        this.finish();
    }
    public void dedo1(View v){
        activarbotones();
        d1.setEnabled(false);
        forma=1;
    }
    public void recta(View v){
        activarbotones();
        b1.setEnabled(false);
        forma=2;
    }
    public void lineasdesdepunto(View v){
        activarbotones();
        b2.setEnabled(false);
        forma=3;
    }
    public void caminohormigas(View view){
        activarbotones();
        b3.setEnabled(false);
        forma=4;
    }
    public void cuadradolinea(View view){
        activarbotones();
        b4.setEnabled(false);
        forma=5;
    }
    public void cuadradorelleno(View view){
        activarbotones();
        b5.setEnabled(false);
        forma=6;
    }
    public void circulolinea(View view){
        activarbotones();
        b6.setEnabled(false);
        forma=7;
    }
    public void circulorelleno(View view){
        activarbotones();
        b7.setEnabled(false);
        forma=8;
    }
    public void ovallinea(View view){
        activarbotones();
        b8.setEnabled(false);
        forma=9;
    }
    public void ovalrelleno(View view){
        activarbotones();
        b9.setEnabled(false);
        forma=10;
    }
    public void borrar(View view){
        activarbotones();
        b10.setEnabled(false);
        forma=11;
    }
    public void dedo2(View view){
        activarbotones();
        d2.setEnabled(false);
        forma=12;
    }
    public void dedo3(View view){
        activarbotones();
        d3.setEnabled(false);
        forma=13;
    }
    public void tamaño1(View v){
        t1.setEnabled(false);
        t3.setEnabled(true);
        t5.setEnabled(true);
        tamaño=1;
    }
    public void tamaño3(View v){
        t3.setEnabled(false);
        t1.setEnabled(true);
        t5.setEnabled(true);
        tamaño=3;
    }
    public void tamaño5(View v){
        t5.setEnabled(false);
        t1.setEnabled(true);
        t3.setEnabled(true);
        tamaño=5;
    }



}
