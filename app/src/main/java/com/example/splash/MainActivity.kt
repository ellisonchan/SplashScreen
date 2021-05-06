package com.example.splash

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity: AppCompatActivity() {
    private var preDrawCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Splash", "MainActivity#onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyLifecycleObserver(lifecycle, this).also { lifecycle.addObserver(it) }

        val contentView = findViewById<View>(android.R.id.content)
        contentView.viewTreeObserver.addOnGlobalLayoutListener {
            Log.d("Splash", "onGlobalLayout()")
        }
        contentView.viewTreeObserver.addOnPreDrawListener {
            Log.d("Splash", "onPreDraw() count:$preDrawCount")
            if (++preDrawCount > 0) {
                Log.d("Splash", "onPreDraw() proceed")
                return@addOnPreDrawListener true
            } else {
                Log.d("Splash", "onPreDraw() cancel")
                return@addOnPreDrawListener false
            }
        }
        contentView.viewTreeObserver.addOnDrawListener {
            Log.d("Splash", "onDraw()")
        }
    }
}