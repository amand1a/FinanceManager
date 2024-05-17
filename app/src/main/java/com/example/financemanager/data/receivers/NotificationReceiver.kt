package com.example.financemanager.data.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.example.financemanager.data.repository.PlannedExpenseRepositoryImpl
import com.example.financemanager.data.services.PlannedExpenditureWorkManager
import com.example.financemanager.domain.repository.PlannedExpenseRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {
    @Inject
    lateinit var plannedRepository: PlannedExpenseRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == PlannedExpenditureWorkManager.NOTIFICATION_ACCEPTED) {
            val id = intent.getLongExtra(PlannedExpenseRepositoryImpl.KEY_ID, 0L)
            CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                plannedRepository.addPlannedExpenseInExpense(id)
                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.cancel(id.toInt())
            }
        }
    }
}
