import com.sun.beans.introspect.PropertyInfo
import domain.Transaction
import service.TransactionService
import java.util.UUID

fun main() {
    val transactionService = TransactionService()

    transactionService.createTransaction(
        Transaction(
            id = UUID.randomUUID().toString(),
            amount = 60000,
            description = "For bread",
            accountId = "UU3hgjfj"
        )
    )

    println(
        transactionService.getAll()
    )
}