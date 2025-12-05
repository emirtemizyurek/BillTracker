package org.example.billtracker.utils

import android.content.Intent
import android.net.Uri
import org.example.billtracker.MainActivity



object AndroidContextHolder {
    var activity: MainActivity? = null
}

actual object UrlOpener {
    actual fun openUrl(url: String) {
        AndroidContextHolder.activity?.let { activity ->
            try {
                val finalUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    "https://$url"
                } else {
                    url
                }
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl))
                activity.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }
}
