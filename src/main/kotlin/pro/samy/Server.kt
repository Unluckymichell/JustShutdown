package pro.samy

import com.sun.net.httpserver.HttpServer
import pro.samy.http.CancelHttpHandler
import pro.samy.http.ShutdownHttpHandler
import java.net.InetSocketAddress
import java.util.concurrent.Executors

import java.util.concurrent.ThreadPoolExecutor

class Server {
    private val SERVER_PORT = 26262

    init {

        val server = HttpServer.create(InetSocketAddress("0.0.0.0", SERVER_PORT), 0)
        server.createContext("/shutdown", ShutdownHttpHandler())
        server.createContext("/cancel", CancelHttpHandler())

        val threadPoolExecutor = Executors.newFixedThreadPool(2) as ThreadPoolExecutor
        server.executor = threadPoolExecutor
        server.start()

    }

}
