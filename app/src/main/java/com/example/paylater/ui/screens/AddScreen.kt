package com.example.paylater.ui.screens

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.paylater.R
import com.example.paylater.data.Payment
import com.example.paylater.ui.AddViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

data class Mode(
    @DrawableRes val icon: Int,
    val modeName: String,
    val mode: Int
)

private val modes = listOf(
    Mode(R.drawable.cash, "Cash", 0),
    Mode(R.drawable.upi, "UPI",1),
    Mode(R.drawable.card, "Credit", 2)
)

@Composable
fun AddScreen(
    navController: NavHostController,
    addViewModel: AddViewModel = hiltViewModel()
) {
    var navJob: Job? = null
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var number by rememberSaveable {
        mutableStateOf("")
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var modeInInt by rememberSaveable {
        mutableIntStateOf(-1)
    }
    var amount by rememberSaveable {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = {
                navJob?.cancel()
                navJob = scope.launch {
                    delay(500)
                    navController.popBackStack()
                }
            }) {
                Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft, contentDescription = null)
            }
            TextButton(onClick = {
                if (listOf(number, name, amount).any { it.isEmpty() } || modeInInt == -1) {
                    Toast.makeText(context, "One or more fields is/are Empty!", Toast.LENGTH_LONG).show()
                } else {
                    val pam =  Payment(
                        name = name,
                        number = number,
                        mode = modeInInt,
                        date = "${Calendar.getInstance().time.date}/${Calendar.getInstance().time.month + 1}/${Calendar.getInstance().time.year + 1900}",
                        amount = amount.toDouble(),
                        status = 0
                    )
                    addViewModel.addPayment(pam)
                    navController.popBackStack()
                    Toast.makeText(context, "Successfully Added!", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Add customer")
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), value = number,
                onValueChange = { number = it },
                label = {
                    Text("Enter Mobile Number")
                }, leadingIcon = {
                    Text("+91")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            FieldHeader("Name")
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), value = name,
                onValueChange = { name = it },
                label = {
                    Text("Enter name of recipient")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                })
            FieldHeader("Mode of payment")
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Spacer(modifier = Modifier.padding(4.dp))
                modes.forEach { mode ->
                    PayMode(
                        mode,
                        selected = mode.mode == modeInInt, onSelect = { m ->
                            modeInInt = m
                        }
                    )
                }
                Spacer(modifier = Modifier.padding(4.dp))
            }
            FieldHeader("Amount")
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), value = amount,
                onValueChange = { amount = it},
                label = {
                    Text("Enter Amount In Rupees")
                },
                leadingIcon = {
                    Text("₹")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

        }
    }
}

@Composable
private fun PayMode(
    mode: Mode,
    selected: Boolean,
    onSelect: (Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .width(100.dp)
            .border(
                width = 2.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else if (isSystemInDarkTheme()) Color.Gray else Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(
                onClick = {
                    onSelect(mode.mode)
                }
            ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(painter = painterResource(mode.icon), contentDescription = null)
            Text(mode.modeName)
        }
    }
}

@Composable
private fun FieldHeader(
    s: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f))
        Text(s)
        HorizontalDivider(modifier = Modifier.weight(1f))
    }
}
