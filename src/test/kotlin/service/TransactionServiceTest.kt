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
        assertEquals(transaction, transactionService.createTransaction(transaction))
    }

    @Test
    fun `service gets all transactions`(){
        val transactionService = TransactionService()

        transactionService.createTransaction(transaction)
        transactionService.createTransaction(transaction)
        assertEquals(2, transactionService.getAll().size)
    }

    @Test
    fun `service returns exact transactions`(){
        val transactionService = TransactionService()
        transactionService.createTransaction(transaction)
        assertEquals(transaction, transactionService.getAll().first())
    }

    @Test
    fun `returns empty list of transactions`(){
        val transactionService = TransactionService()
        assertEquals(emptyList<Transaction>(), transactionService.getAll())
    }
}