package org.example.billtracker.utils

import java.awt.Desktop
import java.net.URI

actual object UrlOpener {
    actual fun openUrl(url: String) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(URI(url))
        }
    }
}
