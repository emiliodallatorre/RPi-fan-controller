package gq.emiliodallatorre.rpi_fan

import android.app.Activity
import android.os.Bundle
import android.util.Log

import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManager


import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class FanController : Activity() {
    private var mGpio: Gpio? = null
    private var temp: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pioService = PeripheralManager.getInstance()
        try {
            mGpio = pioService.openGpio(GPIO_NAME)
            mGpio!!.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
            Log.d(TAG, "mGpio is set to TRUE")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        while (true) {
            temp = tempGetter()
            if (temp > 40000) {
                try {
                    Log.d(TAG, "Temperature is above 40°C, starting fan.")
                    mGpio!!.value = true
                    while (true) {
                        temp = tempGetter()
                        if (temp < 38000) {
                            Log.d(TAG, "Temperature is under 38°C, stopping fan.")
                            break
                        }
                        Thread.sleep(10000)
                    }
                    mGpio!!.value = false
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
            try {
                Thread.sleep(10000)
                Log.d(TAG, "Waiting 10s to refresh. Temperature is $temp.")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
    }

    private fun tempGetter(): Int {
        val br: BufferedReader
        val fr: FileReader
        try {
            fr = FileReader("/sys/class/hwmon/hwmon0/temp1_input")
            br = BufferedReader(fr)
            val line: String
            try {
                line = br.readLine()
                if (line != null) {
                    return Integer.parseInt(line)
                }
            } catch (e: java.io.IOException) {
                e.printStackTrace()
            }

        } catch (e: java.io.FileNotFoundException) {
            e.printStackTrace()
        }

        return 0
    }

    companion object {
        private const val GPIO_NAME = "BCM18"
        private const val TAG = "fanController"
    }
}
