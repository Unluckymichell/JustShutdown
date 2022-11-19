
import notification.PopupThingy
import java.awt.SystemTray

lateinit var popup: PopupThingy
lateinit var server: Server

fun main() {
    println("Hello World!")

    if (!SystemTray.isSupported()) {
        System.err.println("System tray not supported!")
    }

    popup = PopupThingy()
    server = Server()
}