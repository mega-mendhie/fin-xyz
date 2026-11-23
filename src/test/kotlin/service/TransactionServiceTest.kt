package service

import domain.Transaction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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

    @Test
    fun `rejects transaction with non-positive amount`() {
        val transactionService = TransactionService()
        val invalidTransaction = transaction.copy(amount = 0)

        assertThrows<IllegalArgumentException> {
            transactionService.createTransaction(invalidTransaction)
        }

        assertEquals(emptyList<Transaction>(), transactionService.getAll())
    }

    @Test
    fun `rejects transaction with blank Account ID`() {
        val transactionService = TransactionService()
        val invalidTransaction = transaction.copy(accountId = " ")

        assertThrows<IllegalArgumentException> {
            transactionService.createTransaction(invalidTransaction)
        }

        assertEquals(emptyList<Transaction>(), transactionService.getAll())
    }

    @Test
    fun `rejects transaction with blank Transaction ID`() {
        val transactionService = TransactionService()
        val invalidTransaction = transaction.copy(id = " ")

        assertThrows<IllegalArgumentException> {
            transactionService.createTransaction(invalidTransaction)
        }

        assertEquals(emptyList<Transaction>(), transactionService.getAll())
    }

    @Test
    fun `rejects transaction with blank description`() {
        val transactionService = TransactionService()
        val invalidTransaction = transaction.copy(description = " ")

        assertThrows<IllegalArgumentException> {
            transactionService.createTransaction(invalidTransaction)
        }

        assertEquals(emptyList<Transaction>(), transactionService.getAll())
    }
}