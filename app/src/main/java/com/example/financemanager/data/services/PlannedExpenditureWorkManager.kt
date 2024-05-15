package com.example.financemanager.data.services

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financemanager.R
import com.example.financemanager.data.receivers.NotificationReceiver

class PlannedExpenditureWorkManager(
    private val ctx: Context,
    private val params: WorkerParameters
) :
    CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val category = inputData.getString("title") ?: ""
        val description = inputData.getString("description") ?: ""
        val cost = inputData.getString("cost") ?: ""
        val bodyString = "$category : $cost\n$description"
        val id = inputData.getLong("id", 0)
        notifyPlannedExpenditure(ctx, bodyString, id)
        return Result.success()
    }

    companion object {
        const val Planned_Expenditure_Notification = "planned expenditure"

        fun notifyPlannedExpenditure(context: Context, message: String, id: Long) {
            val acceptIntent = Intent(context, NotificationReceiver::class.java)
            acceptIntent.action = "notification_accepted"
            acceptIntent.putExtra("id", id)
            val acceptPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                acceptIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val builder = NotificationCompat.Builder(context, Planned_Expenditure_Notification)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Planned Expenditure")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(LongArray(0))
                .addAction(R.drawable.baseline_done_24, "Accept", acceptPendingIntent)
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
            NotificationManagerCompat.from(context).notify(id.toInt(), builder.build())
        }
    }
}