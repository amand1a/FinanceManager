package com.example.financemanager

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.financemanager.data.services.PlannedExpenditureWorkManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotifyChannel()
    }

    private fun createNotifyChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                PlannedExpenditureWorkManager.Planned_Expenditure_Notification,
                "Planned Expenditure",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "used for the increment counter notifications"
            val notificationManager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}