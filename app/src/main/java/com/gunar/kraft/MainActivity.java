package com.gunar.kraft;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.shake);
        mediaPlayerWinner = MediaPlayer.create(getApplicationContext(), R.raw.winner);
        mediaPlayerPerder = MediaPlayer.create(getApplicationContext(), R.raw.perder);

        if (sensor == null) {
            Toast.makeText(getApplicationContext(), "No cuenta con el sensor", Toast.LENGTH_LONG).show();
            //finish();
        }

        final Button bo2 = (Button) findViewById(R.id.button);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                System.out.println("Valor giro " + x);
                //Izquierda

                if (x < 1 && x > -1) contador2 = 1;
                if (x > 4 && contador2==1) {
                    if(aux && aux2){
                        new Handler().postDelayed(new Runnable() {

                            public void run() {

                                aux = true;

                            }
                        }, 5);
                        lanzar(bo2);

                        aux=false;

                    }
                    contador2 = 0;
                } else if (x < -4 && contador2==1) {
                    if(aux && aux2){
                        new Handler().postDelayed(new Runnable() {

                            public void run() {

                                aux = true;

                            }
                        }, 5);
                        lanzar(bo2);

                        aux=false;

                    }
                    contador2 = 0;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        start();
    }

    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayerWinner;
    private MediaPlayer mediaPlayerPerder;
    private int contador = 0, objetivo = 0, contador2 = 0;
    private boolean aux = true, aux2 = true;

    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    int indice = 0;

    public void lanzar(View view) {

        mediaPlayer.start();


        EditText pd1 = (EditText) findViewById(R.id.editText);
        EditText pd2 = (EditText) findViewById(R.id.editText2);
        TextView tx2 = (TextView) findViewById(R.id.textView2);
        TextView tx3 = (TextView) findViewById(R.id.textView3);

        ImageView img2 = (ImageView) findViewById(R.id.imageView2);
        ImageView img3 = (ImageView) findViewById(R.id.imageView3);
        Button bo1 = (Button) findViewById(R.id.button2);

        int d1 = (int) (Math.random() * 6) + 1;

        switch (d1){
            case 1:
                img2.setImageResource(R.drawable.and_uno);
                break;
            case 2:
                img2.setImageResource(R.drawable.and_dos);
                break;
            case 3:
                img2.setImageResource(R.drawable.and_tres);
                break;
            case 4:
                img2.setImageResource(R.drawable.and_cuatro);
                break;
            case 5:
                img2.setImageResource(R.drawable.and_cinco);
                break;
            case 6:
                img2.setImageResource(R.drawable.and_seis);
                break;
        }

        int d2 = (int) (Math.random() * 6) + 1;

        switch (d2){
            case 1:
                img3.setImageResource(R.drawable.and_uno);
                break;
            case 2:
                img3.setImageResource(R.drawable.and_dos);
                break;
            case 3:
                img3.setImageResource(R.drawable.and_tres);
                break;
            case 4:
                img3.setImageResource(R.drawable.and_cuatro);
                break;
            case 5:
                img3.setImageResource(R.drawable.and_cinco);
                break;
            case 6:
                img3.setImageResource(R.drawable.and_seis);
                break;
        }


        if (contador == 0) {
            if (d1 + d2 == 11 || d1 + d2 == 7) {
                tx2.setText("Gano!!");
                mediaPlayerWinner.start();
                view.setEnabled(false);
                bo1.setEnabled(true);
                aux2=false;
            } else {
                if (d1 + d2 == 2) {
                    tx2.setText("Perdio :(");
                    mediaPlayerPerder.start();
                    view.setEnabled(false);
                    bo1.setEnabled(true);
                    aux2=false;
                } else {
                    objetivo = d1 + d2;
                    tx2.setText("Tire otra vez");
                    tx3.setText("El objetivo es " + objetivo);
                }
            }
            contador++;
        } else {
            if (d1 + d2 == 7) {
                //perdio
                tx2.setText("Perdio :(");
                mediaPlayerPerder.start();
                view.setEnabled(false);
                bo1.setEnabled(true);
                aux2=false;
            } else {

                if (d1 + d2 == objetivo) {
                    tx2.setText("Gano!!");
                    mediaPlayerWinner.start();
                    view.setEnabled(false);
                    bo1.setEnabled(true);
                    aux2=false;
                }
                tx3.setText("El objetivo es " + objetivo);
            }
        }


        pd1.setText(d1 + "");
        pd2.setText(d2 + "");



    }

    private void start() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stop() {
        sensorManager.unregisterListener(sensorEventListener);
    }

    public void reiniciar(View view) {
        EditText pd1 = (EditText) findViewById(R.id.editText);
        EditText pd2 = (EditText) findViewById(R.id.editText2);
        TextView tx2 = (TextView) findViewById(R.id.textView2);
        TextView tx3 = (TextView) findViewById(R.id.textView3);
        pd1.setText("");
        pd2.setText("");
        tx2.setText("");
        tx3.setText("");
        contador = 0;
        objetivo = 0;

        ImageView img2 = (ImageView) findViewById(R.id.imageView2);
        ImageView img3 = (ImageView) findViewById(R.id.imageView3);
        img2.setImageResource(R.drawable.juegodados);
        img3.setImageResource(R.drawable.juegodados);

        Button bo = (Button) findViewById(R.id.button);
        bo.setEnabled(true);

        Button bo2 = (Button) findViewById(R.id.button2);
        bo2.setEnabled(false);

        aux2=true;
    }
}
