package http

import com.sun.net.httpserver.HttpExchange

object HttpUtil {

    fun sendResponse(httpExchange: HttpExchange, code: Int, text: String) {
        httpExchange.sendResponseHeaders(code, text.length.toLong())
        httpExchange.responseBody.write(text.toByteArray())
        httpExchange.responseBody.flush()
        httpExchange.responseBody.close()
    }

}