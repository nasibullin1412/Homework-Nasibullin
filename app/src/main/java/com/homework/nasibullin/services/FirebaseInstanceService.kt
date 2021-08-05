package com.homework.nasibullin.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.homework.nasibullin.R


class FirebaseInstanceService: FirebaseMessagingService() {

    companion object{
        const val NOTIFICATION_CHANNEL_ID = "homework.nasibullin.services"
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        if (p0.data.isEmpty()){
            mapOf(p0.notification?.title to p0.notification?.body)
        }
        else{
            showNotification(p0.data)
        }
    }

    private fun showNotification(data: Map<String, String>){
        val title: String = data["title"].toString()
        val body: String = data["body"].toString()
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Notification",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = "Code sphere"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = getColor(R.color.custom_background)
            notificationChannel.enableLights(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(
            this,
            NOTIFICATION_CHANNEL_ID
        )
        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.black_star)
            .setContentTitle(title)
            .setContentText(body)
            .setContentInfo("Info")

    }
}