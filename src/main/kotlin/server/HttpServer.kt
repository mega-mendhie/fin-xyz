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