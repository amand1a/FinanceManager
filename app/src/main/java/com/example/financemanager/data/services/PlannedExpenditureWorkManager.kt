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
import com.example.financemanager.data.repository.PlannedExpenseRepositoryImpl

class PlannedExpenditureWorkManager(
    private val ctx: Context,
    private val params: WorkerParameters
) :
    CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        with(inputData) {
            val category = getString(PlannedExpenseRepositoryImpl.TITLE_KEY) ?: ""
            val description = getString(PlannedExpenseRepositoryImpl.DESCRIPTION_KEY) ?: ""
            val cost = getString(PlannedExpenseRepositoryImpl.COST_KEY) ?: ""
            val bodyString = "$category : $cost\n$description"
            val id = getLong(PlannedExpenseRepositoryImpl.KEY_ID, 0)
            notifyPlannedExpenditure(ctx, bodyString, id)
        }

        return Result.success()
    }

    companion object {
        const val Planned_Expenditure_Notification = "planned expenditure"
        const val NOTIFICATION_ACCEPTED = "notification_accepted"
        private const val ACCEPT = "accept"
        private const val NOTIFY_TITLE = "Planned Expenditure"
        fun notifyPlannedExpenditure(context: Context, message: String, id: Long) {
            val acceptIntent = Intent(context, NotificationReceiver::class.java)
            acceptIntent.action = NOTIFICATION_ACCEPTED
            acceptIntent.putExtra(PlannedExpenseRepositoryImpl.KEY_ID, id)
            val acceptPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                acceptIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val builder = NotificationCompat.Builder(context, Planned_Expenditure_Notification)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(NOTIFY_TITLE)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(LongArray(0))
                .addAction(R.drawable.baseline_done_24, ACCEPT, acceptPendingIntent)
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