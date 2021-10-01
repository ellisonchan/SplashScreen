package com.example.splash.activity

import android.graphics.*
import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.os.BuildCompat
import com.example.splash.databinding.ActivityMainBinding
import com.example.splash.databinding.ActivityMainSimpleBinding
import com.example.splash.observer.MyLifecycleObserver
import com.example.splash.viewmodel.MyViewModel

class OldMainActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainSimpleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Test for hard work to keep splash screen longer.
        // doSomeHardWork()
    }

    private fun doSomeHardWork() {
        Log.d("Splash", "SplashActivity#doSomeHardWork()")
        val content: View = findViewById(android.R.id.content)

        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    Log.d("Splash", "SplashActivity#onPreDraw() currentTime:${SystemClock.uptimeMillis()}")

                    return if (viewModel.isDataReady()) {
                        Log.d("Splash", "SplashActivity#onPreDraw() proceed")
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        Log.d("Splash", "SplashActivity#onPreDraw() suspend")
                        false
                    }
                }
            }
        )
    }
}