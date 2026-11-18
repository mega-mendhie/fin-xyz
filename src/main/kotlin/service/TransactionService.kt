package service

import domain.Transaction

class TransactionService {

    //in-app memory
    val transactions = mutableListOf<Transaction>()

    fun createTransaction(transaction: Transaction){
        transactions.add(transaction)
    }

    fun getAll(): List<Transaction> {
        return transactions.toList()
    }
}