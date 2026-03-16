package com.amira.metro_masr.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amira.metro_masr.R
import kotlinx.coroutines.delay

@Composable
fun MetroSplashScreen(navController: NavController, modifier: Modifier = Modifier) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("home_screen") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.cairo_metro_logo),
            contentDescription = "Metro Logo",
            modifier = Modifier.size(280.dp)
        )
    }
}