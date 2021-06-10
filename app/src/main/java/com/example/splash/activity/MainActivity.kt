package com.example.splash.activity

import android.graphics.*
import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.os.BuildCompat
import com.example.splash.databinding.ActivityMainBinding
import com.example.splash.observer.MyLifecycleObserver

class MainActivity : AppCompatActivity() {
    @RequiresApi(31)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyLifecycleObserver(lifecycle, this).also { lifecycle.addObserver(it) }

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playKotlinAnimation(binding)

        // Todo show blur effect.
        // showViewRenderEffect(binding.iv)
        // listenBlurStatus()
        // showDialog()
    }

    private fun playKotlinAnimation(binding: ActivityMainBinding) {
        val animatable: Animatable = binding.iv.drawable as Animatable
        animatable.start()
    }

    @RequiresApi(31)
    private fun showViewRenderEffect(view: View) {
        // Ensure working on S device or above .
        if (!BuildCompat.isAtLeastS()) {
            return
        }

        val radiusX = 13f
        val radiusY = 14f

        // view.setRenderEffect(RenderEffect.createBlurEffect(radiusX, radiusY, Shader.TileMode.CLAMP))
        // view.setRenderEffect(RenderEffect.createBlurEffect(radiusX, radiusY, Shader.TileMode.DECAL))
        // view.setRenderEffect(RenderEffect.createBlurEffect(radiusX, radiusY, Shader.TileMode.MIRROR))
        // view.setRenderEffect(RenderEffect.createBlurEffect(radiusX, radiusY, Shader.TileMode.REPEAT))
        // view.setRenderEffect(RenderEffect.createColorFilterEffect(PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.ADD)))
        view.setRenderEffect(RenderEffect.createOffsetEffect(113f, 112f))
    }

    @RequiresApi(31)
    private fun listenBlurStatus() {
        // Ensure working on S device or above .
        if (!BuildCompat.isAtLeastS()) {
            return
        }

        val windowManager = getSystemService(WindowManager::class.java)
        windowManager.addCrossWindowBlurEnabledListener { result ->
            Log.d(
                "Blur",
                "addCrossWindowBlurEnabledListener result:$result"
            )
        }
        Log.d(
            "Blur",
            "is blur enabled:${windowManager.isCrossWindowBlurEnabled}"
        )
    }

    @RequiresApi(31)
    private fun showDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Warning")
            .setMessage("Check out latest commit please.")
            .create()
        showWindowRenderEffect(dialog.window)
        dialog.show()
    }

    @RequiresApi(31)
    private fun showWindowRenderEffect(window: Window?) {
        // Ensure working on S device or above .
        if (!BuildCompat.isAtLeastS()) {
            return
        }

        Log.d("Blur", "showWindowRenderEffect window:$window")
        // Todo
        window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        window?.setBackgroundBlurRadius(30)
    }
}