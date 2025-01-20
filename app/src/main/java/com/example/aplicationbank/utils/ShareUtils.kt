package com.example.aplicationbank.utils

import android.content.Context
import android.content.Intent

fun shareProductInfo(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Compartir información de la cuenta"))
}