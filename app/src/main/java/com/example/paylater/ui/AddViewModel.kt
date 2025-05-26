package com.example.paylater.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paylater.data.PayRepository
import com.example.paylater.data.Payment
import com.example.paylater.remainder.PaymentRemainderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

const val ADD_EXCEPTION = "ADD_EXC"

@HiltViewModel
class AddViewModel @Inject constructor(
    private val payRepository: PayRepository,
    @ApplicationContext private val applicationContext: Context
): ViewModel() {
    fun addPayment(p: Payment) {
        viewModelScope.launch {
            try {
                val pid = payRepository.insertPayment(p)
                PaymentRemainderScheduler.scheduleRemainder(applicationContext, pid.toInt())
            } catch (e: Exception) {
                Log.e(ADD_EXCEPTION, "Problem with pid and notification")
            }
        }
    }
}