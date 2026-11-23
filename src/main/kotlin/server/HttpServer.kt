package server

import com.sun.net.httpserver.HttpServer
import domain.Transaction
import domain.TransactionResponse
import service.TransactionService
import tools.jackson.core.JacksonException
import tools.jackson.module.kotlin.jacksonObjectMapper
import java.net.InetSocketAddress

class FinanceHttpServer(portNumber: Int = 8080){
    private val server: HttpServer = HttpServer.create(
        InetSocketAddress("127.0.0.1", portNumber),
        0
    )
    private val txService = TransactionService()
    private val mapper = jacksonObjectMapper()

    init{
        server.createContext("/health"){ exchange ->
            exchange.use { ex ->
                if(ex.requestURI.path != "/health"){
                    ex.sendResponseHeaders(404, -1)
                    return@createContext
                }
                if(ex.requestMethod == "GET"){

                    // response body
                    val response = """{"status": "UP"}"""
                    val responseBytes = response.toByteArray(Charsets.UTF_8)

                    // response headers
                    ex.responseHeaders.add("Content-Type", "application/json")
                    ex.sendResponseHeaders(200, responseBytes.size.toLong())
                    val responseBody = ex.responseBody
                    responseBody.use { it.write(responseBytes) }
                } else{
                    ex.responseHeaders.add("Allow", "GET")
                    ex.sendResponseHeaders(405, -1)
                }
            }
        }

        server.createContext("/transactions"){ exchange ->
            exchange.use { ex ->
                if(ex.requestURI.path != "/transactions"){
                    ex.sendResponseHeaders(404, -1)
                    return@createContext
                }

                when(ex.requestMethod){
                    "GET" -> {
                        val response = txService.getAll()
                        val mappedResponse = mapper.writeValueAsBytes(response)
                        ex.responseHeaders.add("Content-Type", "application/json")
                        ex.sendResponseHeaders(200, mappedResponse.size.toLong())
                        ex.responseBody.use{it.write(mappedResponse)}
                    }
                    "POST" -> {
                        val contentType = ex.requestHeaders.getFirst("Content-Type") ?: ""
                        if(!contentType.startsWith("application/json", ignoreCase = true)){
                            val response = """{"error": "Unsupported Media Type"}"""
                            val responseStream = response.toByteArray(Charsets.UTF_8)
                            ex.responseHeaders.add("Content-Type", "application/json")
                            ex.sendResponseHeaders(415, responseStream.size.toLong())
                            ex.responseBody.use { it.write(responseStream) }
                            return@createContext
                        }
                        val body = ex.requestBody
                        try {
                            val transaction = mapper.readValue(body, Transaction::class.java)
                            val response = txService.createTransaction(transaction)
                            val mappedResponse = mapper.writeValueAsBytes(
                                TransactionResponse(
                                    message = "Transaction created",
                                    transaction = response
                                )
                            )
                            ex.responseHeaders.add("Content-Type", "application/json")
                            ex.sendResponseHeaders(201, mappedResponse.size.toLong())
                            ex.responseBody.use{it.write(mappedResponse)}
                        }
                        catch (e: IllegalArgumentException){
                            val response = """{"error": "Invalid transaction data: ${e.message}"}"""
                            val responseStream = response.toByteArray(Charsets.UTF_8)
                            ex.responseHeaders.add("Content-Type", "application/json")
                            ex.sendResponseHeaders(400, responseStream.size.toLong())
                            ex.responseBody.use { it.write(responseStream) }
                        }
                        catch (e: JacksonException){
                            val response = """{"error": "Invalid JSON request body"}"""
                            val responseStream = response.toByteArray(Charsets.UTF_8)
                            ex.responseHeaders.add("Content-Type", "application/json")
                            ex.sendResponseHeaders(400, responseStream.size.toLong())
                            ex.responseBody.use { it.write(responseStream) }
                        }
                        catch (e: Exception){
                            val response = """{"error": "Internal Server Error"}"""
                            val responseStream = response.toByteArray(Charsets.UTF_8)
                            ex.responseHeaders.add("Content-Type", "application/json")
                            ex.sendResponseHeaders(500, responseStream.size.toLong())
                            ex.responseBody.use { it.write(responseStream) }
                        }
                    }
                    else -> {
                        ex.responseHeaders.add("Allow", "GET, POST")
                        ex.sendResponseHeaders(405, -1)
                    }
                }
            }
        }
        server.executor = null
    }

    fun start(){
        server.start()
    }

    fun stop(delaySeconds: Int = 0){
        server.stop(delaySeconds)
    }
}