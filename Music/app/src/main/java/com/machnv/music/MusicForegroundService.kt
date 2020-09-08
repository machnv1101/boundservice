package com.machnv.music

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MusicForegroundService : Service() {

    private var musicBinder: IBinder = MusicBinder()

    override fun onBind(p0: Intent?): IBinder? {
        return musicBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val input = intent?.getStringExtra(MainActivity.KEY_NOTIFICATION)
        input?.let { createNotification(it) }
            ?: createNotification(MainActivity.DEFAULT_NOTIFICATION)
        return START_NOT_STICKY
    }

    private fun createNotification(title: String) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val serviceChanel =
                NotificationChannel(
                    CHANNEL_ID,
                    "Foreground",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChanel)

            val notificationIntent = Intent(this, MainActivity::class.java)

            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(MainActivity.VALUE_NOTIFICATION)
                .setContentText(title)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build()

            startForeground(MainActivity.NOTIFICATION_ID, notification)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }

    inner class MusicBinder : Binder() {
        fun getMusicForegroundService() = this@MusicForegroundService
    }

    companion object {
        const val CHANNEL_ID = "ForegroundChannelID"
    }
}