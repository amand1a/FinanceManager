package com.example.financemanager.data.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Message
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financemanager.R

class PlannedExpenditureWorkManager(val ctx: Context, val params: WorkerParameters) :
    CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return Result.success()
    }




    companion object {
        const val Planned_Expenditure_Notification = "planned expenditure"

        fun notifyPlannedExpenditure(context: Context,message: String){
            /*val deleteIntent = Intent(context, YourBroadcastReceiver::class.java)
            deleteIntent.action = "notification_swiped"
            val deletePendingIntent = PendingIntent.getBroadcast(context, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val builder = NotificationCompat.Builder(context, PlannedExpenditureWorkManager.Planned_Expenditure_Notification)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Planned Expenditure")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(LongArray(0))
                .addAction(R.drawable.ic_delete, "Delete", deletePendingIntent)

            */
            // Show the notification
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            //NotificationManagerCompat.from(context).notify(1, builder.build())
        }
    }



}