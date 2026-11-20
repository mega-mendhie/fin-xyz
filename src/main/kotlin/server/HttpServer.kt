package server

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

class FinanceHttpServer(val portNumber: Int = 8080){
    private val server: HttpServer = HttpServer.create(
        InetSocketAddress("127.0.0.1", portNumber),
        0
    )

    init{
        server.createContext("/health"){ exchange ->
            if(exchange.requestURI.path != "/health"){
                exchange.sendResponseHeaders(404, -1)
                return@createContext
            }
            if(exchange.requestMethod == "GET"){

                // response body
                val response = """{"status": "UP"}"""
                val responseBytes = response.toByteArray(Charsets.UTF_8)

                // response headers
                exchange.responseHeaders.add("Content-Type", "application/json")
                exchange.sendResponseHeaders(200, responseBytes.size.toLong())
                val responseBody = exchange.responseBody
                responseBody.use { it.write(responseBytes) }
            }
            else{
                exchange.responseHeaders.add("Allow", "GET")
                exchange.sendResponseHeaders(405, -1)
            }
        }
        server.executor = null
    }

    fun start(){
        server.start()
        println("Server running on http://localhost:${portNumber}/health")
    }

    fun stop(delaySeconds: Int = 0){
        server.stop(delaySeconds)
    }
}