package com.example.paylater.remainder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.concurrent.TimeUnit

object PaymentRemainderScheduler {
    private const val TAG = "PaymentRemainderScheduler"

    private fun getRemainderPendingIntent(context: Context, paymentId: Int): PendingIntent {
        val intent = Intent(context, PaymentRemainderReceiver::class.java).apply {
            putExtra("pid", paymentId)
        }

        val requestCode = AppConstants.REMINDER_PENDING_INTENT_REQUEST_CODE_BASE + paymentId

        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun scheduleRemainder(context: Context, paymentId: Int) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = getRemainderPendingIntent(context, paymentId)

        val intervalMillis = TimeUnit.DAYS.toMillis(5)

        val firstTriggerAtMillis = System.currentTimeMillis() + intervalMillis

        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            firstTriggerAtMillis,
            intervalMillis,
            pendingIntent
        )

        Log.d(TAG, "Scheduled reminder for Payment ID: $paymentId. First alarm in 5 days, then every 5 days.")
    }
    // never mind idk the difference between remainder and reminder 😭
    fun cancelReminder(context: Context, paymentId: Int) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val pendingIntent = getRemainderPendingIntent(context, paymentId)

        alarmManager.cancel(pendingIntent)

        Log.d(TAG, "Canceled reminder for Payment ID: $paymentId.")
    }
}