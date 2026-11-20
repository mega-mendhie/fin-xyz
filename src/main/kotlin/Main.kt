import server.FinanceHttpServer

fun main() {
    val server = FinanceHttpServer(8080)
    server.start()
}