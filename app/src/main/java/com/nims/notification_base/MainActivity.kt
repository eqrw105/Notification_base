package com.nims.notification_base

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val NOTIFICATION_ID = 1001;
        createNotificationChannel(this, NotificationManagerCompat.IMPORTANCE_HIGH, false,
            getString(R.string.app_name), "App notification channel")   // 1

        val channelId = "$packageName-${getString(R.string.app_name)}"
        val title = "Android Developer"
        val content = "Notifications in Android P"

        val intent = Intent(baseContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val fullScreenPendingIntent = PendingIntent.getActivity(baseContext, 0,
            intent, PendingIntent.FLAG_UPDATE_CURRENT)    // 2

        val builder = NotificationCompat.Builder(this, channelId)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setContentTitle(title)
        builder.setContentText(content)
        builder.priority = NotificationCompat.PRIORITY_HIGH   // 3
        builder.setAutoCancel(true)
        builder.setFullScreenIntent(fullScreenPendingIntent, true)   // 4

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(NOTIFICATION_ID, builder.build())

    }

    private fun createNotificationChannel(context: Context, importance: Int, showBadge: Boolean,
                                          name: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "${context.packageName}-$name"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}