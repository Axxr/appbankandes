package com.example.aplicationbank.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import com.example.aplicationbank.domain.model.Product
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aplicationbank.domain.model.Currency
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.aplicationbank.presentation.components.BottomNavigationBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.draw.clip


import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import com.example.aplicationbank.R


@Composable
fun HomeScreen(
    onProductSelected: (String) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
    onNavigationItemClick: (String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val navigateToLogin by viewModel.navigateToLogin.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)
    var isBalanceVisible by remember { mutableStateOf(true) }

    LaunchedEffect(navigateToLogin) {
        if (navigateToLogin) {
            onNavigateToLogin() // Ejecuta la navegación
        }
    }

    Scaffold(

        topBar = {
            AppBar(
                isBalanceVisible = isBalanceVisible,
                onToggleBalanceVisibility = { isBalanceVisible = !isBalanceVisible }
            )
        },
        bottomBar = {
            BottomNavigationBar(onNavigationItemClick)
        }
    ) { padding ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = viewModel::refreshProducts,
            modifier = Modifier.padding(padding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Productos",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp).padding(vertical = 12.dp)) {

                    val displayedProducts = if (state.isRefreshed) state.products else state.products.take(2)

                    LazyColumn(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .border(1.dp, Color(0x9CD8DADC), RoundedCornerShape(15.dp))
                            .background(Color.White)
                    ) {
                        items(displayedProducts) { product ->
                            ProductItem(
                                product = product,
                                isBalanceVisible = isBalanceVisible,
                                onClick = { onProductSelected(product.id) }
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 28.dp),
                                thickness = 1.dp,
                                color = Color.Gray.copy(alpha = 0.15f)
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun AppBar(
    isBalanceVisible: Boolean,
    onToggleBalanceVisibility: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFF0086B9), Color(0xFF00C8FF))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .padding(all = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.horizontal_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(82.dp)
            )
            // Icono de notificaciones
//            Icon(
//                imageVector = Icons.Default.Notifications,
//                contentDescription = "Notificaciones",
//                tint = Color.White,
//                modifier = Modifier.clickable { /* Acción de notificaciones */ }
//            )
            IconButton(onClick = onToggleBalanceVisibility) {
                Icon(
                    imageVector = if (isBalanceVisible) Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff,
                    contentDescription = if (isBalanceVisible) "Ocultar saldos" else "Mostrar saldos",
                    tint = Color.White
                )
            }
        }
    }
}


@Composable
fun ProductItem(
    product: Product,
    isBalanceVisible: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = when(product.currency){
                Currency.PEN -> R.drawable.savings_sol
                Currency.USD -> R.drawable.savings_dollar
            }),
            contentDescription = product.name,
            modifier = Modifier
                .size(80.dp)
                //.background(color = Color(0xFFE6FAFF))
                .padding(all = 1.dp)
                .clip(RoundedCornerShape(100.dp)),
                //.border(1.dp, Color(0xFFD8DADC), RoundedCornerShape(100.dp)),
            tint = Color.Unspecified
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xE6003462)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isBalanceVisible) {
                    when (product.currency) {
                        Currency.PEN -> "S/ ${product.balance}"
                        Currency.USD -> "US$ ${product.balance}"
                    }
                } else {
                    "••••••••"
                },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF003462)
            )
            Text(
                text = "Saldo disponible",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xCC003462)
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight, // Reemplaza con el ícono correcto
            contentDescription = "Acceder",
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF00C8FF)
        )
        Box(
            modifier = Modifier.padding(end = 12.dp)
        )
        {  }
    }
}