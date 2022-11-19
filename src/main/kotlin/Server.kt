import com.sun.net.httpserver.HttpServer
import http.CancelHttpHandler
import http.ShutdownHttpHandler
import java.net.InetSocketAddress
import java.util.concurrent.Executors

import java.util.concurrent.ThreadPoolExecutor





class Server {
    private val SERVER_PORT = 26262

    init {

        val server = HttpServer.create(InetSocketAddress("localhost", SERVER_PORT), 0)
        server.createContext("/shutdown", ShutdownHttpHandler())
        server.createContext("/cancel", CancelHttpHandler())

        val threadPoolExecutor = Executors.newFixedThreadPool(2) as ThreadPoolExecutor
        server.executor = threadPoolExecutor
        server.start()

    }

}