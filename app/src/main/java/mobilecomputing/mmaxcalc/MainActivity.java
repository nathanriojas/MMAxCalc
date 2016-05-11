package mobilecomputing.mmaxcalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.lang.Math;
import com.javelindevices.javelinsdk.JavelinSensorManager;
import com.javelindevices.javelinsdk.model.ISensor;
import com.javelindevices.javelinsdk.model.ISensorManager;
import com.javelindevices.javelinsdk.model.JavelinSensorEvent;


public class MainActivity extends AppCompatActivity implements ISensorManager.JavelinEventListener {

    private final String TAG = "Javelin Tutorial 1.0";
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static String mDeviceAddress;
    JavelinSensorManager mSensorManager;
    public static boolean isPressed = true;

    private ToggleButton punch;
    private TextView  accelerationView, magnitudeView, editpsi,maxpsi;
    public float totalAcceleration;
    public double gravity = 9.81;
    public String username;
    public String password;
    public String weight;
    public String area;
    public String psi;
    public String id;
    public float trueAccel;
    LoginDataBaseAdapter loginDataBaseAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        /*** Sensor Management**/
        mSensorManager = new JavelinSensorManager(this, mDeviceAddress);
        mSensorManager.setListener(this);
        mSensorManager.enable();
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("userName");
        password = extras.getString("password");
        weight = extras.getString("weight");
        area = extras.getString("area");
        psi = extras.getString("psi");
        id = loginDataBaseAdapter.getID(username);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        maxpsi = (TextView)findViewById(R.id.maxpsiEdit);
        editpsi = (TextView)findViewById(R.id.psiEdit);
        accelerationView = (TextView)findViewById(R.id.acceleration);
        maxpsi.setText(psi);
    }


    /***
     * JavelinEventListener Methods
     ***/
    @Override
    public void onSensorManagerConnected() {
        Log.v(TAG, "Successfully connected to Javelin: " + mDeviceAddress);
        mSensorManager.setAcceleromGyroRate(1);
        mSensorManager.setLedBlinkNumber(5);
        mSensorManager.setLedBlinkRate(75);
        mSensorManager.setLedIntensity(75);
        mSensorManager.ledEnable(true);
    }

    @Override
    public void onSensorManagerDisconnected() {
        Log.d(TAG, "Javelin device disconnected!");
    }

    @Override
    public void onReadRemoteRssi(int rssi) {
    }

    @Override
    public void onSensorChanged(JavelinSensorEvent event) {
        float[] values = event.values;
        //Log.v(TAG, "Sensor changed.\nSensor: " + event.sensor + "\nValues: " + values.length + "\nValue: " + values[0] +" "+ values[3]+ " "+ values[6]);

        switch (event.sensor) {

            case ISensor.TYPE_ACCELEROMETER: //1
                //handle accelerometer data here

                float x = (float)((event.values[6]/2)*9.81);
                float y = (float)((event.values[7]/2)*9.81);
                float z = (float)((event.values[8] / 2) * 9.81);
                float accelerationMagnitude = ((x*x)+(y*y)+(z*z));
                float accelNoGrav =  (float) Math.sqrt(Math.abs(accelerationMagnitude - (9.8*9.8)));
                if (accelNoGrav < 8.00){
                    accelNoGrav = 0;
                }
                trueAccel = accelNoGrav;
                if (trueAccel > 15){
                    totalAcceleration = trueAccel;
                }
                accelerationView.setText("" + totalAcceleration);
                float mass = (float)(Float.parseFloat(weight)/(4.448*9.81));
                float force = mass * trueAccel;
                float fistArea = (float)(Float.parseFloat(area)*(.000645*.125));
                float PSI = (float)((force/fistArea)*.000145);
                editpsi.setText("" + PSI);
                if (PSI > Float.parseFloat(psi)) {
                    loginDataBaseAdapter.updateData(id, username, password, weight, area, Float.toString(PSI));
                    maxpsi.setText(Float.toString(PSI));
                }

                break;

        }
    }


    @Override
    public void onResume(){
        mSensorManager.ledEnable(true);
        super.onResume();
        //** Register Listener for each Javelin Service **//
        mSensorManager.registerListener(this, ISensor.TYPE_ACCELEROMETER);

    }

    @Override
    public void onPause(){
        //** Register Listener for each Javelin Service **//

        mSensorManager.unregisterListener(this, ISensor.TYPE_ACCELEROMETER);

        super.onPause();
    }

    @Override
    public void onStop(){
        mSensorManager.ledEnable(false);
        mSensorManager.disable();
        super.onStop();

    }

    public void onBackPressed(){
        mSensorManager.disable();
        finish();
        Intent i = new Intent(MainActivity.this, ScanActivity.class);
        i.putExtra("userName", username);
        i.putExtra("password", password);
        i.putExtra("weight", weight);
        i.putExtra("area", area);
        i.putExtra("psi", psi);
        startActivity(i);
    }



}