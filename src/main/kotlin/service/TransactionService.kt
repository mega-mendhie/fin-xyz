package service

import domain.Transaction

class TransactionService {

    //in-app memory
    private val transactions = mutableListOf<Transaction>()

    fun createTransaction(transaction: Transaction): Transaction {
        transactions.add(transaction)
        return transaction
    }

    fun getAll(): List<Transaction> {
        return transactions.toList()
    }
}