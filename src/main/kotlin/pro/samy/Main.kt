package pro.samy

import pro.samy.notification.PopupThingy
import java.awt.SystemTray

class Main {

    companion object {

        lateinit var popup: PopupThingy
        lateinit var server: Server

        @JvmStatic
        fun main(args: Array<String>) {
            println("Hello World!")

            if (!SystemTray.isSupported()) {
                System.err.println("System tray not supported!")
            }

            popup = PopupThingy()
            var server = Server()
        }



    }

}
