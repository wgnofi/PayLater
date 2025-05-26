package com.example.paylater.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paylater.data.PayRepository
import com.example.paylater.data.Payment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


sealed interface PayListState {
    data class Success(val payments: List<Payment>): PayListState
    data object Empty: PayListState
}

@HiltViewModel
class MainViewModel @Inject constructor(
    payRepository: PayRepository,
): ViewModel() {

    val payments = payRepository.getPayments().mapNotNull { res ->
        when {
            res.isNotEmpty() -> PayListState.Success(res)
            else -> PayListState.Empty
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = PayListState.Empty
    )

    val totalSum = payRepository.getTotalAmountSum().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = 0.0
        )

    val totalPendingSum = payRepository.getAmountSumForStatusZero().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = 0.0
    )

    val totalFinishedSum = payRepository.getAmountSumForStatusOne().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = 0.0
    )
}