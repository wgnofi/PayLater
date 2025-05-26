package com.example.paylater.remainder

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.paylater.R
import com.example.paylater.data.PayRepository
import com.example.paylater.data.Payment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

const val NOTIFICATION_ERROR = "ERROR_NOTIFY"

@AndroidEntryPoint
class PaymentRemainderReceiver: BroadcastReceiver() {

    @Inject
    lateinit var payRepository: PayRepository

    private val coroutineScope = CoroutineScope(SupervisorJob())
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p0 == null || p1 == null) return

        createNotificationChannel(p0)

        val paymentId = p1.getIntExtra("pid", -1)
        if (paymentId == -1) {
            Log.e(NOTIFICATION_ERROR, "Payment id is -1")
            return
        }

        coroutineScope.launch {
            val payment = payRepository.getPaymentByIdSync(paymentId)

            payment?.let {
                if (ActivityCompat.checkSelfPermission(
                        p0,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return@launch
                }

                showNotification(p0, it)
            } ?: run {
                PaymentRemainderScheduler.cancelReminder(p0, paymentId)
            }
        }
    }

    private fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            AppConstants.PAYMENT_REMINDER_CHANNEL_ID,
            AppConstants.PAYMENT_REMINDER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = AppConstants.PAYMENT_REMINDER_CHANNEL_DESCRIPTION
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification(context: Context, payment: Payment) {
        val notificationId = payment.id

        val builder = NotificationCompat.Builder(context, AppConstants.PAYMENT_REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.rounded_credit_card_clock_24)
            .setContentTitle("Payment Reminder: ${payment.name} ${payment.number}")
            .setContentText("Payment of ${payment.amount} from ${payment.number} dated ${payment.date} is still pending!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)

        // val intent = Intent(context, MainActivity::class.java).apply {
        //     flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        //     putExtra("payment_id", payment.id) // Pass data if needed
        // }
        // val pendingIntent: PendingIntent = PendingIntent.getActivity(
        //     context,
        //     payment.id, // Unique request code for this notification's action
        //     intent,
        //     PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        // )
        // .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(notificationId, builder.build())
        }
    }
}