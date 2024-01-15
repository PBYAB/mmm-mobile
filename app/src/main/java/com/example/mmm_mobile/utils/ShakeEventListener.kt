package com.example.mmm_mobile.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

typealias ShakeEventListener = () -> Unit

class ShakeDetector(private val shakeEventListener: ShakeEventListener) : SensorEventListener {

    private var lastUpdate: Long = 0
    private var last_x: Float = 0.0f
    private var last_y: Float = 0.0f
    private var last_z: Float = 0.0f

    override fun onSensorChanged(event: SensorEvent) {
        val curTime = System.currentTimeMillis()
        if ((curTime - lastUpdate) > 100) {
            val diffTime = (curTime - lastUpdate)
            lastUpdate = curTime

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000

            if (speed > SHAKE_THRESHOLD) {
                shakeEventListener()
            }
            last_x = x
            last_y = y
            last_z = z
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Not used
    }

    companion object {
        private const val SHAKE_THRESHOLD = 800
    }
}