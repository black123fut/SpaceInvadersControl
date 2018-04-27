package com.isaacbenavides.spaceinvaderscontrol;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    SensorManager sensorManager;
    Sensor sensor;
    ImageView leftImage;
    ImageView rightImage;
    TextView currentLevel;
    TextView currentRow;
    TextView nextRow;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        leftImage = findViewById(R.id.leftImage);
        rightImage = findViewById(R.id.rightImage);
        currentLevel = findViewById(R.id.Level);
        currentRow = findViewById(R.id.CurrentRow);
        nextRow = findViewById(R.id.NextRow);
        score = findViewById(R.id.Score);
    }

    /**
     * Permite usar el acelerometro.
     * @param event event del movimiento del celular.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        float y = event.values[1];

        Client message = new Client();

        String direction = "  ";

        if (y > 0){
            rightImage.setImageResource(R.drawable.right2);
            leftImage.setImageResource(R.drawable.left);
            direction = "Right";
        }
        if (y < 0){
            leftImage.setImageResource(R.drawable.left2);
            rightImage.setImageResource(R.drawable.right);
            direction = "Left";
        }
        if (y > -2 && y < 2){
            rightImage.setImageResource(R.drawable.right);
            leftImage.setImageResource(R.drawable.left);
            direction = "  ";
        }
        message.execute(direction);

        String texto = message.getReceived();
        if (texto != null && !texto.equals(""))
            setTextViews(texto);
    }

    /**
     * Actualiza los textos de la app.
     * @param data Informacion a actualizar.
     */
    public void setTextViews(String data){
        Scanner str = new Scanner(data).useDelimiter("\\s*, \\s*");

        currentLevel.setText(str.next());
        currentRow.setText(str.next());
        nextRow.setText(str.next());
        score.setText(str.next());
        str.close();
    }

    /**
     * Hace que la nave dispare.
     * @param view Argumento.
     */
    public void onFireButton(View view){
        Client message = new Client();
        message.execute("Fire");
    }

    protected  void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
