package http

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import notification.PopupThingy
import java.io.IOException

class CancelHttpHandler : HttpHandler {
    companion object {
        lateinit var instance: CancelHttpHandler
    }

    init {
        instance = this
    }

    @Throws(IOException::class)
    override fun handle(httpExchange: HttpExchange) {
        PopupThingy.instance.cancelShutdown()

        HttpUtil.sendResponse(httpExchange, 200, "{msg: \"Shutdown canceled\"}")
    }

}