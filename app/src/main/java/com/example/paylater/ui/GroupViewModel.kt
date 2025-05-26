package com.example.paylater.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paylater.data.GroupedAmountByNumber
import com.example.paylater.data.PayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class GroupViewModel @Inject constructor(
    val payRepository: PayRepository
): ViewModel() {
    val listOfAggregates = payRepository.getSumOfAmountsGroupedByNumber().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList<GroupedAmountByNumber>()
    )
}