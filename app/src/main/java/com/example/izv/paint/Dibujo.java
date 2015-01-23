package com.example.izv.paint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collections;


public class Dibujo extends Activity  implements ColorPickerDialog.OnColorChangedListener{

    private Vista v;
    private int alto, ancho, co;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_dibujo);
        v=new Vista(this);
        setContentView(v);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dibujo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nuevo) {
            v.nuevo();
            return true;
        }if (id == R.id.guardar) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(R.string.guardar);
            LayoutInflater inflater = LayoutInflater.from(this);
            final View vista = inflater.inflate(R.layout.dialogo, null);
            alert.setView(vista);
            final EditText et=(EditText)vista.findViewById(R.id.nombre);
            alert.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (et.getText().toString().compareTo("")==0){
                                tostada("DEBES ESCRIBIR UN NOMBRE");
                            }else{
                                String s=et.getText().toString();
                                guardar(s);
                            }
                        }
                    });
            alert.setNegativeButton(android.R.string.no,null);
            alert.show();

            return true;
        }
        if (id == R.id.cargar) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
            return true;
        }
        if (id == R.id.color) {
            Paint mPaint = new Paint();
            new ColorPickerDialog(this, this, mPaint.getColor()).show();
            return true;
        }
        if (id == R.id.formas) {
            Intent i= new Intent(this, HerramientasDibujo.class);
            startActivityForResult(i, 1);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data){
        if (resultCode == Activity.RESULT_OK &&
                requestCode == 1) {
            int forma=data.getIntExtra("forma",1);
            float tamaño=data.getFloatExtra("tamaño",1);
            if (forma==11){
                co=-1;
                v.forma(1);
                v.color(co);
                v.tamañoPincel(5);
            }else{
                if (co==-1){
                    v.color(Color.BLACK);
                }
                v.forma(forma);
                v.tamañoPincel(tamaño);
            }
        }else{
            if (resultCode == Activity.RESULT_OK &&
                    requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(
                        selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String s = cursor.getString(columnIndex);
                cursor.close();
                v.cargar(s);
            }
        }
    }

    @Override
    public void colorChanged(int color) {
        co=color;
        v.color(color);
    }

    public void tostada (String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void guardar(String s){
        Bitmap mapaDeBits= v.guardar();
        File carpeta = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES).getPath());
        File archivo = new File(carpeta, s+".PNG");
        try {
            FileOutputStream fos = new FileOutputStream(archivo);
            mapaDeBits.compress(
                    Bitmap.CompressFormat.PNG, 90, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        };
        Intent intent = new Intent
                (Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(archivo);
        intent.setData(uri);
        this.sendBroadcast(intent);
        tostada("IMAGEN GUARDADA");
    }
}
