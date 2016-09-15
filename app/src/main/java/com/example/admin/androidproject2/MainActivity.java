package com.example.admin.androidproject2;

/**
 * Created by admin on 9/13/16.
 */
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    //Variables
    private TextView z_axis = null;
    private TextView x_axis = null;
    private TextView y_axis = null;
    private TextView latitude = null;
    private TextView longitude = null;
    private AccelerometerHandler ah = null;
    private MyLocation ml = null;
    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        z_axis = (TextView)findViewById(R.id.Z_);
        x_axis = (TextView)findViewById(R.id.X_);
        y_axis = (TextView)findViewById(R.id.Y_);
        latitude = (TextView)findViewById(R.id.Lat_);
        longitude = (TextView)findViewById(R.id.Lon_);
        ah = new AccelerometerHandler(this);
        ah.addObserver(this);
        String permission = getString(R.string.permission_resource);

        // check permissions

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            ml = new MyLocation(this);
            ml.addObserver(this);
            ml.getLastKnownLocation();
        } else {
            // request permission from the user
            ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_REQUEST_FINE_LOCATION);
        }
    }
    // request permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST_FINE_LOCATION:
                // Need to check that permission was granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ml = new MyLocation(this);
                    ml.addObserver(this);
                    ml.getLastKnownLocation();
                }
                ml = null;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //setup so that data will not be lost on a screen rotation
        ah = new AccelerometerHandler(this);
        ah.addObserver(this);
        ml = new MyLocation(this);
        ml.addObserver(this);
    }

    public void update(Observable observable, Object o) {
        if(observable == ah) {
            // Update the values in the accelerometer if the
            // observable is an accelerometer handler
            float[] xyz = (float[]) o;
            x_axis.setText(Float.toString(xyz[0]));
            y_axis.setText(Float.toString(xyz[1]));
            z_axis.setText(Float.toString(xyz[2]));
        } else if(observable == ml){
            // Update the values in the location if the observable
            // is a location handler
            latitude.setText(Double.toString(((Location) o).getLatitude()));
            longitude.setText(Double.toString(((Location) o).getLongitude()));
        }
    }

}
