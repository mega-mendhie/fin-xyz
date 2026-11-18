package service

import domain.Transaction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TransactionServiceTest {
    val transaction = Transaction(
        "tnx123",
        20000,
        "for test",
        "UU456"
    )

    @Test
    fun `service creates a new transaction`(){
        val transactionService = TransactionService()
        transactionService.createTransaction(transaction)

        assertEquals(1, transactionService.transactions.size)
    }

    @Test
    fun `service gets all transactions`(){
        val transactionService = TransactionService()

        transactionService.createTransaction(transaction)
        transactionService.createTransaction(transaction)
        assertEquals(2, transactionService.getAll().size)
    }

    fun `service returns exact transactions`(){
        val transactionService = TransactionService()
        transactionService.createTransaction(transaction)
        val result = transactionService.getAll()
        assertEquals(transaction, result.first())
    }
}