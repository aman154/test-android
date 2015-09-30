package com.example.aman.myapp1.activity;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.graph.GraphData;
import com.example.aman.myapp1.graph.LineGraphView;

public class GraphExample extends Activity implements SensorEventListener {

    static final int ACCEL_DATA_COUNT  = 512;
    static final int UPDATE_TIME_MILLIS = 200;
    static final String TAG = "GraphExample";

    /* widgets */
    private LineGraphView mAccelGraphView = null;
    private LineGraphView mAccelMagGraphView  = null;

    /* data for the accelerometer graph */
    private GraphData mXAccelData  = null;
    private GraphData mYAccelData = null;
    private GraphData mZAccelData= null;

    /* data for the accelerometer magnitude graph */
    private GraphData mAccelMagData = null;

    /* sensor widgets */
    private Sensor mAccelerometer = null;
    private SensorManager mSensorManager = null;

    private long mLastGraphDrawTimeMillis;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_example);

        mLastGraphDrawTimeMillis = System.currentTimeMillis();

        /* connect to the sensor manager and the accelerometer */
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        listenToAccel();

      /*  *//* initialize the accelerometer graph and its data *//*
        mXAccelData = new GraphData(null, Color.RED, ACCEL_DATA_COUNT);
        mYAccelData = new GraphData(null, Color.GREEN, ACCEL_DATA_COUNT);
        mZAccelData = new GraphData(null, Color.BLUE, ACCEL_DATA_COUNT);

        mAccelGraphView = (LineGraphView) findViewById(R.id.WidgetAccelGraph);
        mAccelGraphView.addDataSet(mXAccelData);
        mAccelGraphView.addDataSet(mYAccelData);
        mAccelGraphView.addDataSet(mZAccelData);*/

        /* initialize the accelerometer magnitude graph and its data */
        mAccelMagData = new GraphData(null, Color.WHITE, ACCEL_DATA_COUNT);

        mAccelMagGraphView = (LineGraphView) findViewById(R.id.WidgetAccelMagGraph);
        mAccelMagGraphView.addDataSet(mAccelMagData);

    }
    public void onDestroy() {
        stopListeningToAccel();
        super.onDestroy();
    }
    private void listenToAccel() {

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (mSensorManager == null
                || mAccelerometer == null
                || !mSensorManager.registerListener(GraphExample.this, mAccelerometer,
                SensorManager.SENSOR_DELAY_FASTEST)) {

            Log.e(TAG, "No accelerometer found.");
        }

    }
    private void stopListeningToAccel() {
        try {
            mSensorManager.unregisterListener(this);
        }
        catch (Throwable t) {
            Log.d(TAG, "Sensor manager unregister failed.");
        }

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
    }

    public void onSensorChanged(SensorEvent event) {

        long    currentTimeMillis = System.currentTimeMillis();
        double  accelMagnitude;

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mXAccelData.appendValue(event.values[0]);
            mYAccelData.appendValue(event.values[1]);
            mZAccelData.appendValue(event.values[2]);

            accelMagnitude =
                    (event.values[0] * event.values[0]) +
                            (event.values[1] * event.values[1]) +
                            (event.values[2] * event.values[2]);
            accelMagnitude = Math.sqrt(accelMagnitude);

            mAccelMagData.appendValue((float)accelMagnitude);
        }

        if ((currentTimeMillis - mLastGraphDrawTimeMillis) >= UPDATE_TIME_MILLIS) {
            mAccelGraphView.invalidate();
            mAccelMagGraphView.invalidate();

            mLastGraphDrawTimeMillis = currentTimeMillis;
        }

    }

}
