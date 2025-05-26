package com.example.paylater.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

data class GroupedAmountByNumber(
    val number: String,
    val totalAmount: Double
)

@Dao
interface PayDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPayment(payment: Payment): Long

    @Query("SELECT * FROM payments")
    fun getPayments(): Flow<List<Payment>>

    @Update
    suspend fun updatePayment(payment: Payment)

    @Delete
    suspend fun deletePayment(payment: Payment)

    @Query("SELECT * FROM payments WHERE id = :paymentId")
    fun getPaymentById(paymentId: Int): Flow<Payment?>

    @Query("SELECT * FROM payments WHERE status = 0")
    fun getPaymentsWithStatusZero(): Flow<List<Payment>>

    @Query("SELECT * FROM payments WHERE status = 1")
    fun getPaymentsWithStatusOne(): Flow<List<Payment>>

    @Query("SELECT SUM(amount) FROM payments")
    fun getTotalAmountSum(): Flow<Double?>

    @Query("SELECT SUM(amount) FROM payments WHERE status = 0")
    fun getAmountSumForStatusZero(): Flow<Double?>

    @Query("SELECT SUM(amount) FROM payments WHERE status = 1")
    fun getAmountSumForStatusOne(): Flow<Double?>

    @Query("SELECT number, SUM(amount) as totalAmount FROM payments WHERE status = 0 GROUP BY number")
    fun getSumOfAmountsGroupedByNumber(): Flow<List<GroupedAmountByNumber>>

    @Query("SELECT * FROM payments WHERE id = :paymentId")
    suspend fun getPaymentByIdSync(paymentId: Int): Payment? // for notifications gang!
}