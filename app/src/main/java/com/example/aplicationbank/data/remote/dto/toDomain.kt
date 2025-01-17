package com.example.aplicationbank.data.remote.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.aplicationbank.domain.model.Currency
import com.example.aplicationbank.domain.model.Product
import com.example.aplicationbank.domain.model.ProductType
import com.example.aplicationbank.domain.model.Transaction
import com.example.aplicationbank.domain.model.TransactionType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun ProductDto.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        type = ProductType.valueOf(this.type),
        balance = this.balance,
        currency = Currency.valueOf(this.currency),
        accountNumber = this.accountNumber.orEmpty(),
        cci = this.cci
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun TransactionDto.toDomain(): Transaction {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    return Transaction(
        id = this.id,
        type = TransactionType.valueOf(this.type),
        amount = this.amount,
        date = LocalDateTime.parse(this.date, formatter),
        description = this.description
    )
}
