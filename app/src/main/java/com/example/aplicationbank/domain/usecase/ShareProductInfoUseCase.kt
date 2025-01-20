package com.example.aplicationbank.domain.usecase

import com.example.aplicationbank.domain.model.Product

class ShareProductInfoUseCase {
    fun execute(product: Product): String {
        return """
            Cuenta: ${product.name}
            Número: ${product.accountNumber}
            CCI: ${product.cci}
            Moneda: ${product.currency}
        """.trimIndent()
    }
}