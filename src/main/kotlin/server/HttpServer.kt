package server

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

class FinanceHttpServer(portNumber: Int = 8080){
    var server: HttpServer = HttpServer.create(InetSocketAddress(portNumber), 0)

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
                exchange.sendResponseHeaders(200, response.length.toLong())
                val responseBody = exchange.responseBody
                responseBody.write(responseBytes)
                responseBody.close()
            }
            else{
                exchange.sendResponseHeaders(405, -1)
            }
        }
        server.executor = null
    }

    fun start(){
        server.start()
        println("Server running on http://localhost:8080/health")
    }
}