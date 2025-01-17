import com.example.aplicationbank.domain.model.Transaction
import com.example.aplicationbank.domain.repository.ProductRepository

class GetTransactionsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: String): List<Transaction> =
        repository.getTransactions(productId)
}