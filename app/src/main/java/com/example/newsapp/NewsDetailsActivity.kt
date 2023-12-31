package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_news_details.*

class NewsDetailsActivity : AppCompatActivity() {

    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        dialog = SpotsDialog.Builder().setContext(this).setCancelable(false).build()
        dialog.show()


        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                dialog.dismiss()
            }

        }

        if (intent != null) {
            if (!intent.getStringExtra("webURL").toString().isEmpty()) {
                webView.loadUrl(intent.getStringExtra("webURL").toString())
            }
        }

    }
}