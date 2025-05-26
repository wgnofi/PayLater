package com.example.paylater.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.paylater.R
import com.example.paylater.data.Payment
import com.example.paylater.data.getModeDrawable
import com.example.paylater.ui.MainViewModel
import com.example.paylater.ui.PayListState


@Composable
fun HomeScreen(
    onAdd: () -> Unit,
    onGroup: () -> Unit,
    onProfile: () -> Unit,
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val payments by viewModel.payments.collectAsStateWithLifecycle()
    val total by viewModel.totalSum.collectAsStateWithLifecycle()
    val pending by viewModel.totalPendingSum.collectAsStateWithLifecycle()
    val finished by viewModel.totalFinishedSum.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth().wrapContentWidth(align = Alignment.End).padding(16.dp)) {
            Surface(
                shadowElevation = 3.dp,
                tonalElevation = 3.dp,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.clickable(
                    onClick = onGroup
                )
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Icon(painter = painterResource(R.drawable.groups), contentDescription = null)
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Surface(
                shadowElevation = 3.dp,
                tonalElevation = 3.dp,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.clickable(
                    onClick = onProfile
                )
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                }
            }
        }
        StatCard(
            total = total ?: 0.0,
            pending = pending ?: 0.0,
            finished = finished ?: 0.0
        )
        PaymentHeader(onClick = onAdd)
        LazyColumn {
            when(payments) {
                is PayListState.Success -> {
                    val pays = (payments as PayListState.Success).payments.map { it.number to it }
                    val p = pays.groupBy({it.first}, {it.second})
                    p.forEach { (number, payList) ->
                        item {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(number, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold)
                                Text("Total: ₹ ${payList.sumOf { it.amount }.toInt()}", fontWeight = FontWeight.SemiBold)
                                Text("Pending: ₹ ${payList.filter { it.status == 0 }.sumOf { it.amount }.toInt()}", fontWeight = FontWeight.SemiBold)
                            }
                        }
                        items(payList) { pay ->
                            PayCard(pay, onClick = {
                                navController.navigate(MainNavigation.DETAIL + "/${pay.id}")
                            })
                        }
                    }
                }
                is PayListState.Empty -> {
                    item {
                        Text("Empty List!", fontWeight = FontWeight.Light, modifier = Modifier.fillMaxWidth().wrapContentWidth(align = Alignment.CenterHorizontally).padding(top = 200.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    total: Double,
    pending: Double,
    finished: Double
) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                with(20.sp) {
                    Text("Total:", fontSize = this, fontWeight = FontWeight.SemiBold)
                    Text("₹ $total", fontSize = this, fontWeight = FontWeight.SemiBold)
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                with(15.sp) {
                    Text("Amount pending:", fontSize = this)
                    Text("₹ $pending", fontSize = this)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                with(15.sp) {
                    Text("Amount Completed:", fontSize = this)
                    Text("₹ $finished", fontSize = this)
                }
            }
        }
    }
}

@Composable
private fun PayCard(
    p: Payment,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    color = if (!isSystemInDarkTheme()) Color.White else Color.Black,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(p.mode.getModeDrawable()),
                            contentDescription = null
                        )
                    }
                }
                Surface(
                    color = if (!isSystemInDarkTheme()) Color.White else Color.Black,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.call),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(p.number, fontSize = 12.sp)
                    }
                }
                Column(modifier = Modifier.wrapContentWidth(align = Alignment.End),
                    horizontalAlignment = Alignment.End) {
                    Text(
                        "₹ ${p.amount}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.End)
                    )
                    Text(p.date, fontSize = 10.sp)
                }


            }
        }
    }
}

@Composable
private fun PaymentHeader(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Payments", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        OutlinedButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text("Add")
        }
    }
}

