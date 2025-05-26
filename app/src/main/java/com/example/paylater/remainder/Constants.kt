package com.example.paylater.remainder

object AppConstants {
    const val PAYMENT_REMINDER_CHANNEL_ID = "payment_reminder_channel"
    const val PAYMENT_REMINDER_CHANNEL_NAME = "Payment Reminders"
    const val PAYMENT_REMINDER_CHANNEL_DESCRIPTION = "Reminders for pending payments"

    const val REMINDER_PENDING_INTENT_REQUEST_CODE_BASE = 1000 // Append with pid, this need to be unique for each payment fam!
}