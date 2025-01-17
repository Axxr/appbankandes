package com.example.aplicationbank.presentation.components


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MobileFriendly
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import com.example.aplicationbank.R



@Composable
fun BottomNavigationBar(onNavigationItemClick: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = false, // Manejar selección desde el estado
            onClick = { onNavigationItemClick("Inicio") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio"
                )
            },
            label = { Text("Inicio") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onNavigationItemClick("Operaciones") },
            icon = {
                Icon(
                    imageVector = Icons.Default.SwapHoriz,
                    contentDescription = "Operaciones"
                )
            },
            label = { Text("Operaciones") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onNavigationItemClick("Servicios") },
            icon = {
                Icon(
                    imageVector = Icons.Default.MobileFriendly,
                    contentDescription = "Servicios"
                )
            },
            label = { Text("Servicios") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onNavigationItemClick("Soporte") },
            icon = {
                Icon(
                    imageVector = Icons.Default.HeadsetMic,
                    contentDescription = "Soporte"
                )
            },
            label = { Text("Soporte") }
        )
    }
}