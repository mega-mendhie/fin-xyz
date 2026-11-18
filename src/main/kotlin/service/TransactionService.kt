package service

import domain.Transaction

class TransactionService {

    //in-app memory
    private val transactions = mutableListOf<Transaction>()

    fun createTransaction(transaction: Transaction): Transaction?{
        return if(transactions.add(transaction)) transaction else null
    }

    fun getAll(): List<Transaction> {
        return transactions.toList()
    }
}