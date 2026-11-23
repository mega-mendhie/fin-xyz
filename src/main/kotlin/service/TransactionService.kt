package service

import domain.Transaction

class TransactionService {

    //in-app memory
    private val transactions = mutableListOf<Transaction>()

    fun createTransaction(transaction: Transaction): Transaction {
        require(transaction.id.isNotBlank()) { "Transaction ID must not be empty" }
        require(transaction.accountId.isNotBlank()) { "Account ID must not be empty" }
        require(transaction.amount > 0L) { "Amount must be greater than zero" }
        require(transaction.description.isNotBlank()) { "Description must not be blank" }
        transactions.add(transaction)
        return transaction
    }

    fun getAll(): List<Transaction> {
        return transactions.toList()
    }
}