package com.example.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.splash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @RequiresApi(31)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyLifecycleObserver(lifecycle, this).also { lifecycle.addObserver(it) }

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}