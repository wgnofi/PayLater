package com.example.paylater.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    var job: Job? = null
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            IconButton(onClick = {
                job?.cancel()
                job = scope.launch {
                    delay(300)
                    navController.popBackStack()
                }
            }) {
                Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft, contentDescription = null)
            }
        }
        ElevatedCard(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Every Pending Payment will be reminded periodically in an interval of 5 days",
                    textAlign = TextAlign.Center)
            }
        }
        Text("PayLater v 1.0", fontSize = 12.sp, fontWeight = FontWeight.Normal, modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally))
    }
}