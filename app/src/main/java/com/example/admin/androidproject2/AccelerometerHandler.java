package com.example.admin.androidproject2;

/**
 * Created by admin on 9/13/16.
 */
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Observable;

public class AccelerometerHandler extends Observable implements SensorEventListener{

    // copied from class

    SensorManager sensorManager = null;
    Sensor accelerometer = null;

    public AccelerometerHandler(Activity act) {
        sensorManager = (SensorManager) act.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_UI);
    }

    //this method is only needed if you have more then one sensor
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        //check which sensor changed
        if(sensorEvent.sensor.getType()!= Sensor.TYPE_ACCELEROMETER)
            return;

        // notify activity of new acceleration value and set changed
        setChanged();
        notifyObservers(sensorEvent.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}