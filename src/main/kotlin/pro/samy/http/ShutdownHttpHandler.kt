package pro.samy.http

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import pro.samy.Main.Companion.popup
import pro.samy.http.HttpUtil.sendResponse
import java.io.File
import java.io.IOException
import kotlin.system.exitProcess

internal class ShutdownHttpHandler : HttpHandler {

    companion object {
        lateinit var instance: ShutdownHttpHandler
    }

    init {
        instance = this
    }

    var shutdownTriggered = false
    private var timer = java.util.Timer()

    fun cancelShutdown() {
        timer.cancel()
        shutdownTriggered = false
    }

    @Throws(IOException::class)
    override fun handle(httpExchange: HttpExchange) {

        if (httpExchange.requestMethod != "POST" && httpExchange.requestMethod != "GET") {
            sendResponse(httpExchange, 405, "{err: \"Method Not Allowed\", code: 405}")
            return
        }


        val path = httpExchange.requestURI.path
        val pathParts = path.split("/")
        val delayString = pathParts[pathParts.size - 1]
        val delay = delayString.toIntOrNull()
            ?: run {
                sendResponse(httpExchange, 400, "{err: \"Bad Request\", code: 400}")
                return
            }

        if (shutdownTriggered) {
            sendResponse(httpExchange, 400, "{err: \"Already shutting down\", code: 400}")
            return
        }

        shutdownTriggered = true
        sendResponse(httpExchange, 200, "{msg: \"Shutting down in ${delay}s\", delay: $delay, code: 200}")

        println(File(".").canonicalPath)

        println("Shutting down in ${delay}s")
        popup.displayShutdownMessage(delay)

        // repeat task for every second
        timer = java.util.Timer()
        timer.scheduleAtFixedRate(object : java.util.TimerTask() {
            var counter = delay
            override fun run() {
                if (counter == 0) {
                    Runtime.getRuntime().exec("shutdown -s -t 0", null, File("."))
                    timer.cancel()
                    exitProcess(0)
                } else {
                    println("Shutting down in ${counter}s")
                    popup.updateShutdownCountdown(counter)
                    counter--
                }
            }
        }, 0, 1000)
    }

}