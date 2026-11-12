package com.example.meditationtimer

import android.app.Activity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.WindowManager
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import java.util.Locale

/**
 * Shows the meditation timer (assets/index.html) full screen.
 * The page calls window.AndroidTTS.speak(text, lang) and stop(), so guidance uses the
 * phone's native text-to-speech engine, which the Android WebView does not provide.
 * lang is "en" or "ta" and selects the matching voice.
 */
class MainActivity : Activity(), TextToSpeech.OnInitListener {

    private lateinit var webView: WebView
    private var tts: TextToSpeech? = null
    private var initDone = false
    private var pendingText: String? = null
    private var pendingLang: String = "en"

    inner class TtsBridge {
        @JavascriptInterface
        fun speak(text: String, lang: String) {
            runOnUiThread { speakNow(text, lang) }
        }

        @JavascriptInterface
        fun stop() {
            runOnUiThread { tts?.stop() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keep the screen on while the app is open so the session is not interrupted.
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        tts = TextToSpeech(this, this)

        webView = WebView(this)
        setContentView(webView)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.addJavascriptInterface(TtsBridge(), "AndroidTTS")

        webView.loadUrl("file:///android_asset/index.html")
    }

    override fun onInit(status: Int) {
        initDone = true
        pendingText?.let { speakNow(it, pendingLang) }
        pendingText = null
    }

    private fun speakNow(text: String, lang: String) {
        if (!initDone) {
            pendingText = text
            pendingLang = lang
            return
        }
        val tag = when (lang) {
            "ta" -> "ta-IN"
            "hi" -> "hi-IN"
            "es" -> "es-ES"
            "fr" -> "fr-FR"
            "zh" -> "zh-CN"
            else -> "en-US"
        }
        val locale = Locale.forLanguageTag(tag)
        val result = tts?.setLanguage(locale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) return
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "meditation")
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}
