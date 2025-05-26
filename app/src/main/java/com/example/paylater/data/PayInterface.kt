package com.example.paylater.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PayRepository {
    fun getPayments(): Flow<List<Payment>>
    suspend fun insertPayment(payment: Payment): Long
    suspend fun updatePayment(payment: Payment)
    suspend fun deletePayment(payment: Payment)
    fun getPaymentsWithStatusZero(): Flow<List<Payment>>
    fun getPaymentsWithStatusOne(): Flow<List<Payment>>
    fun getTotalAmountSum(): Flow<Double?>
    fun getPaymentById(paymentId: Int): Flow<Payment?>
    fun getAmountSumForStatusZero(): Flow<Double?>
    fun getAmountSumForStatusOne(): Flow<Double?>
    fun getSumOfAmountsGroupedByNumber(): Flow<List<GroupedAmountByNumber>>
    suspend fun getPaymentByIdSync(paymentId: Int): Payment?
}

class OfflinePayRepository
    @Inject constructor(private val payDao: PayDao): PayRepository {
    override fun getPayments(): Flow<List<Payment>> = payDao.getPayments()
    override suspend fun insertPayment(payment: Payment): Long = payDao.insertPayment(payment)
    override suspend fun updatePayment(payment: Payment) = payDao.updatePayment(payment)
    override suspend fun deletePayment(payment: Payment) = payDao.deletePayment(payment)
    override fun getPaymentsWithStatusZero(): Flow<List<Payment>> = payDao.getPaymentsWithStatusZero()
    override fun getPaymentsWithStatusOne(): Flow<List<Payment>> = payDao.getPaymentsWithStatusOne()
    override fun getTotalAmountSum(): Flow<Double?> = payDao.getTotalAmountSum()
    override fun getAmountSumForStatusZero(): Flow<Double?> = payDao.getAmountSumForStatusZero()
    override fun getAmountSumForStatusOne(): Flow<Double?> = payDao.getAmountSumForStatusOne()
    override fun getSumOfAmountsGroupedByNumber(): Flow<List<GroupedAmountByNumber>> = payDao.getSumOfAmountsGroupedByNumber()
    override fun getPaymentById(paymentId: Int): Flow<Payment?> = payDao.getPaymentById(paymentId)
    override suspend fun getPaymentByIdSync(paymentId: Int): Payment? = payDao.getPaymentByIdSync(paymentId)
}