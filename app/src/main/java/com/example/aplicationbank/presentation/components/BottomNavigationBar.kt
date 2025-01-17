package com.example.aplicationbank.presentation.components


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MobileFriendly
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.aplicationbank.R



@Composable
fun BottomNavigationBar(onNavigationItemClick: (String) -> Unit) {
    var selectedItem by remember { mutableStateOf("Inicio") }

    NavigationBar(
        containerColor = Color.White,
    ) {
        NavigationBarItem(
            selected = selectedItem == "Inicio",
            onClick = {
                selectedItem = "Inicio"
                onNavigationItemClick("Inicio")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio",
                    tint = if (selectedItem == "Inicio") Color(0xFF00C8FF) else Color.Gray.copy(alpha = 0.6f)
                )
            },
            label = {
                Text(
                    text = "Inicio",
                    color = if (selectedItem == "Inicio") Color(0xFF00C8FF) else Color.Gray.copy(alpha = 0.6f) // Colores según selección
                )
            }
        )
        NavigationBarItem(
            selected = selectedItem == "Operaciones",
            onClick = {
                selectedItem = "Operaciones"
                onNavigationItemClick("Operaciones")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.SwapHoriz,
                    contentDescription = "Operaciones",
                    tint = if (selectedItem == "Operaciones") Color(0xFF00C8FF) else Color.Gray.copy(alpha = 0.6f)
                )
            },
            label = {
                Text(
                    text = "Operaciones",
                    color = if (selectedItem == "Operaciones") Color(0xFF00C8FF) else Color.Gray.copy(alpha = 0.6f)
                )
            }
        )
        NavigationBarItem(
            selected = selectedItem == "Servicios",
            onClick = {
                selectedItem = "Servicios"
                onNavigationItemClick("Servicios")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.MobileFriendly,
                    contentDescription = "Servicios",
                    tint = if (selectedItem == "Servicios") Color(0xFF00C8FF) else Color.Gray.copy(alpha = 0.6f)
                )
            },
            label = {
                Text(
                    text = "Servicios",
                    color = if (selectedItem == "Servicios") Color(0xFF00C8FF) else Color.Gray.copy(alpha = 0.6f)
                )
            }
        )
        NavigationBarItem(
            selected = selectedItem == "Menu",
            onClick = {
                selectedItem = "Menu"
                onNavigationItemClick("Menu")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Notes,
                    contentDescription = "Menu",
                    tint = if (selectedItem == "Menu") Color(0xFF00C8FF) else Color.Gray.copy(alpha = 0.6f)
                )
            },
            label = {
                Text(
                    text = "Menu",
                    color = if (selectedItem == "Menu") Color(0xFF00C8FF) else Color.Gray.copy(alpha = 0.6f)
                )
            }
        )
    }
}
