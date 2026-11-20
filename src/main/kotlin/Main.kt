import server.FinanceHttpServer

fun main() {
    /*val transactionService = TransactionService()
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
    )*/

    val server = FinanceHttpServer(8080)
    server.start()
}