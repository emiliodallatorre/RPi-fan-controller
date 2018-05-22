package gq.emiliodallatorre.rpi_fan;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class fanController extends Activity {
    private static final String GPIO_NAME = "BCM18";
    private static final String TAG = "fanController";
    private Gpio mGpio;
    int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PeripheralManager pioService = PeripheralManager.getInstance();
        try {
            mGpio = pioService.openGpio(GPIO_NAME);
            mGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            Log.d(TAG, "mGpio is set to TRUE");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (; ; ) {
            temp = tempGetter();
            if (temp > 40000) {
                try {
                    Log.d(TAG, "Temperature is above 40°C, starting fan.");
                    mGpio.setValue(true);
                    for (; ; ) {
                        temp = tempGetter();
                        if (temp < 38000) {
                            Log.d(TAG, "Temperature is under 38°C, stopping fan.");
                            break;
                        }
                        Thread.sleep(10000);
                    }
                    mGpio.setValue(false);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(10000);
                Log.d(TAG, "Waiting 10s to refresh. Temperature is " + temp + ".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    int tempGetter() {
        BufferedReader br;
        FileReader fr;
        try {
            fr = new FileReader("/sys/class/hwmon/hwmon0/temp1_input");
            br = new BufferedReader(fr);
            String line;
            try {
                if ((line = br.readLine()) != null) {
                    final int temp = Integer.parseInt(line);
                    return temp;
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
