package com.example.paylater.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paylater.data.PayRepository
import com.example.paylater.data.Payment
import com.example.paylater.remainder.PaymentRemainderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val payRepository: PayRepository,
    @ApplicationContext private val applicationContext: Context
): ViewModel() {

    private var _payment = MutableStateFlow<Payment?>(null)
    val payment = _payment.asStateFlow()


    fun updatePayStatus(p: Payment) {
        viewModelScope.launch {
            payRepository.updatePayment(p)
        }
        _payment.value = p
    }

    fun setPaymentById(id: Int) {
        viewModelScope.launch {
            payRepository.getPaymentById(id).collectLatest { pay ->
                _payment.value = pay
            }
        }
    }

    fun deletePayment(p: Payment) {
        viewModelScope.launch {
            payRepository.deletePayment(p)
        }
        _payment.value = null
    }


    fun cancelNotificationTiedToId(pid: Int) {
        PaymentRemainderScheduler.cancelReminder(applicationContext, pid)
    }

    fun scheduleAgain(pid: Int) {
        PaymentRemainderScheduler.scheduleRemainder(applicationContext, pid)
    }


}