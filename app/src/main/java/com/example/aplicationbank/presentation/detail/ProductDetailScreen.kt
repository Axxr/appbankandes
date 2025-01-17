package com.example.aplicationbank.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aplicationbank.domain.model.Currency
import com.example.aplicationbank.domain.model.Product
import com.example.aplicationbank.domain.model.Transaction
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.aplicationbank.R


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductDetailScreen(
    productId: String,
    viewModel: ProductDetailViewModel = koinViewModel { parametersOf(productId) }
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        state.product?.let { product ->
            Text(
                text = "Consultas",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
            ProductHeader(
                product = product,
                onShareClick = {
                    val shareMessage = viewModel.buildShareMessage(product)
                    val clearMessage = viewModel.clearShareMessage()
                    shareProductInfo(context, shareMessage, clearMessage)
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            TransactionsList(state.transactions)
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        state.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun ProductHeader(
    product: Product, onShareClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0x9CD8DADC),
                shape = RoundedCornerShape(15.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
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
                    Spacer(modifier = Modifier.widthIn(16.dp))
                    Column (
                        horizontalAlignment = Alignment.Start
                    ){
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xCC003462),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "Cuenta: ${product.accountNumber}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "CCI: ${product.cci}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                    }
                    IconButton(
                        onClick = { onShareClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartir",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Saldo disponible",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = when (product.currency) {
                    Currency.PEN -> "S/ ${product.balance}"
                    Currency.USD -> "US$ ${product.balance}"
                },
                style = MaterialTheme.typography.headlineMedium,
                //fontWeight = FontWeight.Bold,
                color = Color(0xFF003462)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionsList(transactions: List<Transaction>) {
    Column {
        Text(
            text = "Últimos movimientos",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        LazyColumn {
            items(
                items = transactions,
                key = { transaction -> transaction.id }
            ) { transaction ->
                TransactionItem(transaction = transaction)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.CheckCircleOutline,
                    contentDescription = "Transacciones",
                    tint = Color(0xE6003462),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = transaction.description,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold

                    )
                    Text(
                        text = transaction.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = "${if (transaction.amount > 0) "+" else ""}${transaction.amount}",
                style = MaterialTheme.typography.titleMedium,
                color = if (transaction.amount > 0)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error
            )
        }
    }
}

private fun shareProductInfo(context: Context, shareMessage: String, claearmesage: Unit) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareMessage)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Compartir información de la cuenta"))
}