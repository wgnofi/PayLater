package com.example.paylater.ui.screens

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_TEXT
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.paylater.R
import com.example.paylater.data.Payment
import com.example.paylater.data.getMode
import com.example.paylater.data.getModeDrawable
import com.example.paylater.ui.DetailViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    pid: Int,
    detailViewModel: DetailViewModel = hiltViewModel(),
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        detailViewModel.setPaymentById(pid)
    }
    val payment by detailViewModel.payment.collectAsStateWithLifecycle()
    var openDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    var job: Job? = null
    val bounceScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = {
                job?.cancel()
                job = bounceScope.launch {
                    delay(300)
                    navController.popBackStack()
                }
            }) {
                Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft, contentDescription = null)
            }
            Row(modifier = Modifier.wrapContentWidth(align = Alignment.End)) {
                IconButton(onClick = {
                    openDialog = true
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
                }
                IconButton(onClick = {
                    shareText(context, payment.formattedStringToShare())
                }) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "share")
                }
            }
        }
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

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
                        Icon(painter = painterResource(R.drawable.date), contentDescription = null)
                        Text(payment?.date ?: "Loading..")
                    }
                }
            }
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                        Text(payment?.name ?: "Loading..")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(painter = painterResource(R.drawable.call), contentDescription = null)
                        Text(payment?.number ?: "Loading..")
                    }
                }
            }
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
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                painter = painterResource(payment?.mode?.getModeDrawable() ?: R.drawable.ic_launcher_foreground),
                                contentDescription = null
                            )
                            Text(payment?.mode?.getMode() ?: "Loading..")
                        }
                        Text("₹ ${payment?.amount}")
                    }
                }
            }
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
                        Text("Status:")
                        if (payment != null) {
                            if (payment?.status == 0) {
                                Text("Pending")
                            } else {
                                Text("Finished")
                            }
                        } else {
                            Text("Loading..")
                        }
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(15) {
                    Spacer(modifier = Modifier.padding(2.dp))
                    HorizontalDivider(modifier = Modifier.weight(1f), thickness = 2.dp)
                    Spacer(modifier = Modifier.padding(2.dp))
                }
            }
            Text(
                "Payed ? (The Status will change with Immediate Effect with Notifications Cancelled.)",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ElevatedButton(onClick = {
                    try {
                        detailViewModel.updatePayStatus(payment?.copy(status = 0)!!)
                        detailViewModel.scheduleAgain(pid)
                        Toast.makeText(context, "Payment set to Pending!", Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.localizedMessage}!", Toast.LENGTH_LONG).show()
                    }
                }, modifier = Modifier.weight(1f)) {
                    Text("No")
                }
                Spacer(modifier = Modifier.padding(16.dp))
                ElevatedButton(onClick = {
                    try {
                        detailViewModel.updatePayStatus(payment?.copy(status = 1)!!)
                        detailViewModel.cancelNotificationTiedToId(pid)
                        Toast.makeText(context, "Payment set to Finished!", Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.localizedMessage}!", Toast.LENGTH_LONG).show()
                    }
                }, modifier = Modifier.weight(1f)) {
                    Text("Yes")
                }

            }






            if (openDialog) {
                BasicAlertDialog(onDismissRequest = { openDialog = false }) {
                    Surface(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        shape = MaterialTheme.shapes.large,
                        tonalElevation = AlertDialogDefaults.TonalElevation
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text =
                                    "Do you wanna delete this Payment?",
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TextButton(
                                    onClick = { openDialog = false },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Cancel")
                                }
                                Spacer(modifier = Modifier.padding(8.dp))
                                TextButton(
                                    onClick = {
                                        scope.launch {
                                            openDialog = false
                                            delay(200)
                                            detailViewModel.deletePayment(payment!!)
                                            navController.popBackStack()
                                        }
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Confirm")
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}

fun Payment?.formattedStringToShare(): String {
    return if (this@formattedStringToShare != null) {
        "Hey $name\nKind Remainder about the payment amount ₹ $amount\nDated: $date"
    }
    else {
        "Error Please Try Again!"
    }
}

fun shareText(p0: Context,text: String) {
    val intent = Intent().apply {
        action = ACTION_SEND
        putExtra(EXTRA_TEXT, text)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(intent, null)
    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    p0.startActivity(shareIntent)
}
