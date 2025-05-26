package com.example.paylater.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.paylater.R


@Entity(tableName = "payments")
data class Payment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val number: String,
    val mode: Int,
    val date: String,
    val amount: Double,
    var status: Int
)


fun Int.getMode(): String {
    return when(this) {
        0 -> "Cash"
        1 -> "UPI"
        2 -> "Credit"
        else -> {"Mode Error"}
    }
}
fun Int.getModeDrawable(): Int {
    return when(this) {
        0 -> R.drawable.cash
        1 -> R.drawable.upi
        2 -> R.drawable.card
        else -> {-1}
    }
}
