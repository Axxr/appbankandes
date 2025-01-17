package com.example.aplicationbank.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.aplicationbank.presentation.components.BottomNavigationBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff


import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest

import com.example.aplicationbank.R


@Composable
fun HomeScreen(
    onProductSelected: (String) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
    onNavigationItemClick: (String) -> Unit // Para manejar navegación desde el NavBar
) {
    val state by viewModel.state.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)
    var isBalanceVisible by remember { mutableStateOf(true) }

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
            Box(modifier = Modifier.fillMaxSize()) {
                if (state.products.isEmpty() && state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                LazyColumn {
                    items(state.products) { product ->
                        ProductItem(
                            product = product,
                            isBalanceVisible = isBalanceVisible,
                            onClick = { onProductSelected(product.id) }
                        )
                    }
                }

                state.error?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
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
            .height(80.dp)
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
                .padding(horizontal = 16.dp),
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
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono de producto
//        Icon(
//            painter = painterResource(id = when(product.currency){
//                Currency.PEN -> R.drawable.savings_sol
//                Currency.USD -> R.drawable.savings_dollar
//            }), // Reemplaza con el ícono correcto
//            contentDescription = product.name,
//            modifier = Modifier
//                .size(80.dp)
//                .padding(end = 16.dp)
//        )
        Icon(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        when (product.currency) {
                            Currency.PEN -> "android.resource://${LocalContext.current.packageName}/raw/savings_sol"
                            Currency.USD -> "android.resource://${LocalContext.current.packageName}/raw/savings_dollar"
                        }
                    )
                    .decoderFactory(SvgDecoder.Factory())
                    .build()
            ),
            contentDescription = product.name,
            modifier = Modifier
                .size(80.dp)
                .padding(end = 16.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
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
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Saldo disponible",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight, // Reemplaza con el ícono correcto
            contentDescription = "Acceder",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}