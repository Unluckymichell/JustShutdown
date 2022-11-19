package notification

import http.ShutdownHttpHandler
import Server
import java.io.File
import java.awt.*
import java.awt.TrayIcon.MessageType
import kotlin.system.exitProcess


class PopupThingy {

    companion object {
        lateinit var instance: PopupThingy
    }

    init {
        instance = this
    }

    val tray = SystemTray.getSystemTray()
    private val popup: PopupMenu = PopupMenu()
    private val trayIcon: TrayIcon
    lateinit var countDownItem: MenuItem

    init {
        val image: Image = Toolkit.getDefaultToolkit().createImage(Server::class.java.getResource("icon.png"))
        trayIcon = TrayIcon(image, "Just Shutdown")
        trayIcon.isImageAutoSize = true
        trayIcon.toolTip = "What is this?"
        trayIcon.popupMenu = popup
        tray.add(trayIcon)
        buildPopupMenu()
    }

    private fun buildPopupMenu() {
        countDownItem = MenuItem("Waiting for Command")
        popup.add(countDownItem)
        popup.addSeparator()

        val shutdownNowItem = MenuItem("Shutdown now")
        shutdownNowItem.addActionListener {
            Runtime.getRuntime().exec("shutdown -s -t 0", null, File("."))
        }
        popup.add(shutdownNowItem)

        val cancelItem = MenuItem("Cancel")
        cancelItem.addActionListener {
            cancelShutdown()
        }
        popup.add(cancelItem)

        val exitItem = MenuItem("Exit")
        exitItem.addActionListener {
            println("Exit")
            tray.remove(trayIcon)
            exitProcess(0)
        }
        popup.addSeparator()
        popup.add(exitItem)
    }

    @Throws(AWTException::class)
    fun displayShutdownMessage(delay: Int) {
        trayIcon.displayMessage("Shutting down", "Shutting down in ${delay}s", MessageType.ERROR)
    }

    @Throws(AWTException::class)
    fun updateShutdownCountdown(delay: Int) {
        if(delay == -1) countDownItem.label = "Waiting for Command"
        else countDownItem.label = "Countdown: $delay"
    }

    fun cancelShutdown() {
        println("Cancel Shutdown")
        updateShutdownCountdown(-1)
        ShutdownHttpHandler.instance.cancelShutdown()
    }
}