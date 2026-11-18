package domain

data class Transaction(
    val id: String,
    val amount: Long,
    val description: String,
    val accountId: String,
)