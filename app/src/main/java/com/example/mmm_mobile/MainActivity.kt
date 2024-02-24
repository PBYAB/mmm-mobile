package com.example.mmm_mobile


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mmm_mobile.ui.theme.MmmmobileTheme
import com.example.mmm_mobile.utils.NotificationReceiver
import com.example.mmm_mobile.utils.NotificationScheduler
import dagger.hilt.android.AndroidEntryPoint


private const val NOTIFICATION_MINUTES_INTERVAL = 1L
private const val MINUTE_TO_MILLIS = 60 * 1000L

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationScheduler.setReminder(this, NotificationReceiver::class.java, NOTIFICATION_MINUTES_INTERVAL * MINUTE_TO_MILLIS)

        setContent {
            MmmmobileTheme {
                MmmMobileApp()
            }
        }
    }
}
