package com.example.splash.activity

import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.os.BuildCompat
import com.example.splash.databinding.ActivityMainBinding
import com.example.splash.databinding.ActivitySplashCusBinding
import com.example.splash.observer.MyLifecycleObserver
import com.example.splash.viewmodel.MyViewModel

class CustomizeSplashActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySplashCusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goToMainScreenDelayed()
    }

    private fun goToMainScreenDelayed() {
        handler.postDelayed({ goToMainScreen() }, 1000)
    }

    private fun goToMainScreen() {
        Intent(
            this,
            OldMainActivity::class.java
        ).also {
            startActivity(it)
            finish()
        }
    }
}